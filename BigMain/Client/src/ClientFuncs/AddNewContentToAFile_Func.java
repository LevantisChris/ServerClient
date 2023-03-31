package ClientFuncs;

import Protocol.header;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class AddNewContentToAFile_Func {
    private header hd;
    private MyFile myFile;
    public AddNewContentToAFile_Func(int protocol_func, Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String contents_of_the_file, String name_of_the_file)
    {
        hd = new header();
        // 4
        /// The file will be rewritten
        myFile = new MyFile();
        myFile.saveContents(contents_of_the_file); // NOTE: Here the contents are in bytes not in Strings...
        myFile.saveName(name_of_the_file); // NOTE: THE USER MUST PROVIDE THE CORRECT NAME OF THE FILE ...
        myFile.saveOpCode(protocol_func);
        myFile.saveIpOwnerFile(socket.getInetAddress()); // Save the address of the client ...

        /* This is so we can get a massage from the server */
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader =  new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /* Send the file to the server */
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(myFile); // Here we send the file (Object) to the server ...
            String msg = bufferedReader.readLine();
            System.out.println("CLIENT STATUS: MSG FROM SERVER --> " + msg);
            if(msg.equals(hd.NEW_CONTENT_ADDED_TO_THE_FILE)) {
                JOptionPane.showMessageDialog(null, "The new contents added to the file (the file rewritten)");
            }
            if(msg.equals(hd.FILE_NOT_EXISTS)) {
                JOptionPane.showMessageDialog(null, "The file doesn't exists", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
