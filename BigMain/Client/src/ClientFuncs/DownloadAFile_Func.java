package ClientFuncs;

import Protocol.header;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/* NOTE: We suppose in this project that the client don't store the file he gets from the server */

public class DownloadAFile_Func {
    private MyFile file_from_server;
    private MyString string_from_server;
    private header hd;

    /// The name_of_the_title in this class means the name of the file the Client wants to download ...
    public DownloadAFile_Func(int protocol_func, Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String name_of_the_file)
    {
        hd = new header();
        // 2
        MyString myString = new MyString(protocol_func, name_of_the_file, socket.getInetAddress());
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(myString);
            // Receive the file in Object, and then we analyze the Object received from the server:
            // if it is instance of MyFile --> everything is FINE the file is found
            // if it is instance of MyString --> we have an error the FILE_NOT_FOUND
            Object recObj = (Object) objectInputStream.readObject();
            if(recObj instanceof MyFile) // Everything is fine the client has take back the file
            {
                file_from_server = (MyFile) recObj;
                System.out.println("CLIENT STATUS: FILE NAME RECEIVED: " + file_from_server.name);
                openFileChooser(file_from_server);
                JOptionPane.showMessageDialog(null, "The file with the name: " + file_from_server.name + " found");
                showContentsOfTheFile(); // open the Frame to see the contents of the file the client receive ...
            } else if(recObj instanceof MyString) { // here means that the file not found ...
                System.out.println("CLIENT STATUS: FILE NOT RECEIVED");
                JOptionPane.showMessageDialog(null, "The file not found, Try Again");
                string_from_server = (MyString) recObj;
                System.out.println("CLIENT STATUS: MSG FROM SERVER --> " + string_from_server);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* --------------------------------------------------------------- */
    /* We ask the user to give us the path he wants to save the file,
       the default path is the storage in the Client folder.
    *  Then we save it in that path */
    private void openFileChooser(MyFile recMyFile) {
        JFileChooser jFileChooser = new JFileChooser("Client/storage");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // the user will select storage only ...
        jFileChooser.setDialogTitle("Default folder is ./Client/storage/");
        int returnVal= jFileChooser.showSaveDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION)
           return;
        System.out.println("CLIENT STATUS: THE PATH THE CLIENT CHOOSE TO SAVE LOCALLY THE FILE IS " + jFileChooser.getSelectedFile().getAbsolutePath());
        downloadTheFile(jFileChooser.getSelectedFile().getAbsolutePath(), recMyFile);
    }
    private void downloadTheFile(String path , MyFile recMyFile) {
        File f = new File(path + "/" + recMyFile.name + ".txt"); // this is the file to save in the specific storage ...
        try {
            if(f.createNewFile()) {
                System.out.println("CLIENT STATUS: FILE WITH NAME " + recMyFile.name + "AND LENGTH " + f.length() + " SAVED IN CLIENT STORAGE");
            } else {
                System.out.println("CLIENT STATUS: ERROR, FILE ALREADY EXISTS WITH NAME " + recMyFile.name + "..."); // we don't want that ...
            }
            storeContentsFile(f, recMyFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void storeContentsFile(File file, MyFile recMyFile) {
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
            String contents = new String(recMyFile.contents_in_bytes);
            fileWriter.write(contents);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /* --------------------------------------------------------------- */
    /* This is a simple Frame that pops up, so the client
       can see the contents of the file he gets from the Server */
    private void showContentsOfTheFile() {
        JFrame contentsFrame = new JFrame(file_from_server.name + ".txt");
        contentsFrame.setSize(new Dimension(500,500));
        contentsFrame.setLayout(new GridLayout(1,1));
        contentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentsFrame.setLocationRelativeTo(null);

        String contents = new String(file_from_server.contents_in_bytes);

        JTextArea contentsTextArea = new JTextArea();
        contentsTextArea.setFont(new Font("Serif", Font.BOLD, 20));
        contentsTextArea.append(contents); // append the contents of the file ...
        contentsTextArea.setEditable(false); // The client won't be able to edit the text ...

        contentsFrame.add(contentsTextArea);

        contentsFrame.setVisible(true);
    }
}
