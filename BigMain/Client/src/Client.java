import GUI.AppMainFrame; // This is the package for the GUI ...
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Client {

    protected void start() {
        Socket socket = null;

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            socket = new Socket("127.0.0.1", 1234);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            /// Start the GUI for the client...
            start_GUI(socket, objectOutputStream, objectInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void start_GUI(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream)
    {
        AppMainFrame appMainFrame = new AppMainFrame(socket, objectOutputStream, objectInputStream);
    }
}
