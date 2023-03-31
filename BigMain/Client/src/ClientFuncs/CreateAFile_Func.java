package ClientFuncs;

import Protocol.header;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class CreateAFile_Func {
    private header hd;
    private MyFile myFile;
    public CreateAFile_Func(int protocol_func, Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String contents_of_the_file, String name_of_the_file)
    {
        hd = new header();
        // 1

        myFile = new MyFile();
        myFile.saveContents(contents_of_the_file); // NOTE: Here the contents are in bytes not in Strings...
        myFile.saveName(name_of_the_file);
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
            if(msg.equals(hd.FILE_CREATED)) {
                JOptionPane.showMessageDialog(null, "THE NEW FILE HAS BEEN CREATED SUCCESSFULLY");
            } else if (msg.equals(hd.CREATION_ERROR)){
                JOptionPane.showMessageDialog(null, "A FILE WITH THE SAME NAME ALREADY EXISTS", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
