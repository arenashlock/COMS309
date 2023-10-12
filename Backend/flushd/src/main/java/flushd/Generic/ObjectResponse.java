package flushd.Generic;

import java.util.HashMap;

public class ObjectResponse extends HashMap<String, Object> {
    private String ObjectName;
    private final String MessageName = "message";
    public ObjectResponse(String ObjectName) {
        this.ObjectName = ObjectName;
        this.put("message","");
        this.put(ObjectName, null);
    }

    public void setObject(Object obj) { this.put(ObjectName, obj); }

    public Object getObject() {return this.get(ObjectName); }
    public void setMessage(String message) {this.put(MessageName, message); }

    public String getMessage() {return (String)this.get(MessageName); }
}
