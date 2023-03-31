package ClientFuncs;

import Protocol.header;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class DeleteAFile_Func {
    private header hd;
    public DeleteAFile_Func(int protocol_func, Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String name_of_the_file)
    {
        hd = new header();
        // 3
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader =  new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MyString myString = new MyString(protocol_func, name_of_the_file, socket.getInetAddress());
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(myString); // add the name to the server ...
            MyString msg_from_server = (MyString) objectInputStream.readObject();
            System.out.println("TEST MESSAGE FROM SERVER --> " + msg_from_server.myString);
            if(msg_from_server.myString.equals(hd.FILE_DELETED)) {
                JOptionPane.showMessageDialog(null, "The file has successfully deleted");
            } else if(msg_from_server.myString.equals(hd.FILE_NOT_EXISTS)){
                JOptionPane.showMessageDialog(null, "The file doesn't exists");
            } else {
                JOptionPane.showMessageDialog(null, "Error occurred with the deletion of the file");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
