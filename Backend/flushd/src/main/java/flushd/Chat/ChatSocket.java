package flushd.Chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/chat/{bathroomid}/{username}")
public class ChatSocket {
	private static Map<Integer, Map<Session, String>> sessionUsernameMap = new Hashtable<>();
	private static Map<Integer, Map<String, Session>> usernameSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username, @PathParam("bathroomid") int bathroomId)
      throws IOException {

		logger.info("Entered into Open");

		if(!sessionUsernameMap.containsKey(bathroomId) || !usernameSessionMap.containsKey(bathroomId))
		{
			sessionUsernameMap.put(bathroomId, new Hashtable<>());
			usernameSessionMap.put(bathroomId, new Hashtable<>());
		}

		for(int id : usernameSessionMap.keySet())
		{
			Session s = usernameSessionMap.get(id).get(username);
			if(s != null)
			{
				onClose(s, id);
			}
		}

		sessionUsernameMap.get(bathroomId).put(session, username);
		usernameSessionMap.get(bathroomId).put(username, session);
		
		String message = username + " has Joined the Chat";
		broadcast(message, bathroomId);
	}


	@OnMessage
	public void onMessage(Session session, String message, @PathParam("bathroomid") int bathroomId) throws IOException {

		logger.info("Entered into Message: Got Message:" + message);
		String username = sessionUsernameMap.get(bathroomId).get(session);

		if (message.startsWith("@")) {
			String destUsername = message.split(" ")[0].substring(1); 

			sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message, bathroomId);
			sendMessageToPArticularUser(username, "[DM] " + username + ": " + message, bathroomId);

		} 
    else {
			broadcast(username + ": " + message, bathroomId);
		}
	}


	@OnClose
	public void onClose(Session session, @PathParam("bathroomid") int bathroomId) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(bathroomId).get(session);
		sessionUsernameMap.get(bathroomId).remove(session);
		if(username != null) {
			usernameSessionMap.get(bathroomId).remove(username);
			String message = username + " disconnected";
			broadcast(message, bathroomId);
		}
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}


	private void sendMessageToPArticularUser(String username, String message, @PathParam("bathroomid") int bathroomId) {
		try {
			usernameSessionMap.get(bathroomId).get(username).getBasicRemote().sendText(message);
		}
    catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}


	private void broadcast(String message, int bathroomId) {
		sessionUsernameMap.get(bathroomId).forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(message);
			} 
      catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}

		});

	}

}
