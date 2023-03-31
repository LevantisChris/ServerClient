package ClientFuncs;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/* I add a Dependency to the project (Server) sto that I can access the class MyFile */
public class MyFile implements Serializable {
    private InetAddress IP_OWNER_FILE;
    public int protocol_func;
    public String name; // NOTE --> I have to make them public, so they can be accessed in the server side
    public byte[] contents_in_bytes; // NOTE --> I have to make them public, so they can be accessed in the server side
    /* This function saves the contents of the JTextArea in a byte array, better for storage */
    protected void saveContents(String contents) {
        contents_in_bytes = contents.getBytes(StandardCharsets.UTF_8);
    }
    protected void saveName(String name) {
        this.name = name;
    }
    protected void saveOpCode(int opCode) {
        this.protocol_func = opCode;
    }
    protected void saveIpOwnerFile(InetAddress ip) {
        this.IP_OWNER_FILE = ip;
    }
}
