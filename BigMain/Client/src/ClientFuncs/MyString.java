package ClientFuncs;
import java.io.Serializable;
import java.net.InetAddress;
public class MyString implements Serializable {
    protected InetAddress IP_OWNER_STRING;
    public int protocol_func;
    public String myString; // Most time the name ...
    public MyString(int protocol_func, String myString, InetAddress ip) {
        this.myString = myString;
        this.protocol_func = protocol_func;
        this.IP_OWNER_STRING = ip;
    }
    public MyString(String myString) {
        this.myString = myString;
        //this.IP_OWNER_STRING = ip;
    }
}
