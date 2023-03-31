import ClientFuncs.MyFile;
import ClientFuncs.MyString;
import Protocol.header;
import ServerFuncs.*;
import java.io.*;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class Server {
    private header hd;
    protected ArrayList<MyFile> allTheFiles = null;
    /// Functions SERVER
    private CreateAFile_Func createAFile_func;
    private DownloadAFile_Func downloadAFile_func;
    private DeleteAFile_Func deleteAFile_func;
    private AddNewContentToAFile_Func addNewContentToAFile_func;
    private AddContentToAFile_Func addContentToAFile_func;

    // My ELEMENTS
    private MyFile recMyFile;
    private MyString recMyString;

    // LOG-FILE
    private File log_file;

    protected void start() {
        //
        deleteAllInTheFile(); /// At first empty the file with the logs we do that because we don't want in these exercise to keep the old logs, we are going to get a very big file ...

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("./Server/resources/LOG_FILE.txt", true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hd = new header(); // for the functions code ...
        allTheFiles = new ArrayList<>();

        Socket socket = null;

        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        ServerSocket serverSocket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;

            try {
                serverSocket = new ServerSocket(1234);
                System.out.println("SERVER STATUS: ACTIVE--PORT: " + serverSocket.getLocalPort());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        while (true) {
            try {
                socket = serverSocket.accept();

                DeleteAllInStorage();
                allTheFiles.removeAll(allTheFiles); // We do this beacuse we have only one client at a time we don't want the new client to have the files of the older client ...

                System.out.println("SERVER STATUS: CONNECTION ESTABLISHED\nIP-CLIENT --> " + socket.getRemoteSocketAddress().toString());

                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object recObj = (Object) objectInputStream.readObject();
                    if(recObj instanceof MyFile) {
                        recMyFile = (MyFile) recObj;
                        if (recMyFile.protocol_func == hd.CREATE) // 1
                        {
                            System.out.println("\nSERVER STATUS: RECEIVED OPERATION --> " + hd.CREATE);
                            System.out.println("SERVER STATUS: NAME RECEIVED FOR CREATE --> " + recMyFile.name);
                            createAFile_func = new CreateAFile_Func(); // Server Funcs
                            String msg = createAFile_func.saveTheFile(recMyFile, allTheFiles);
                            System.out.println("SERVER STATUS: "  + msg);
                            printAllFiles();
                            bufferedWriter.write(msg);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                            writeLogs(socket.getRemoteSocketAddress().toString(), hd.CREATE, recMyFile.name, writer);
                        }
                        if(recMyFile.protocol_func == hd.ADD_NEW_CONTENT) // 4
                        {
                            System.out.println("\nSERVER STATUS: RECEIVED OPERATION --> " + hd.ADD_NEW_CONTENT);
                            System.out.println("SERVER STATUS: NAME RECEIVED FOR ADD NEW CONTENT --> " + recMyFile.name);
                            addNewContentToAFile_func = new AddNewContentToAFile_Func();
                            String msg = addNewContentToAFile_func.alreadyExists(recMyFile, allTheFiles);
                            if(!msg.equals(hd.FILE_NOT_EXISTS))
                                writeLogs(socket.getRemoteSocketAddress().toString(), hd.ADD_NEW_CONTENT, recMyFile.name, writer);
                            bufferedWriter.write(msg);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                        if(recMyFile.protocol_func == hd.ADD_CONTENT)
                        {
                            System.out.println("\nSERVER STATUS: RECEIVED OPERATION --> " + hd.ADD_CONTENT);
                            System.out.println("SERVER STATUS: NAME RECEIVED FOR ADD CONTENT --> " + recMyFile.name);
                            addContentToAFile_func = new AddContentToAFile_Func();
                            String msg = addContentToAFile_func.searchAppendFile(recMyFile, allTheFiles);
                            if(!msg.equals(hd.FILE_NOT_EXISTS))
                                writeLogs(socket.getRemoteSocketAddress().toString(), hd.ADD_CONTENT, recMyFile.name, writer);
                            bufferedWriter.write(msg);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                    }
                    if(recObj instanceof MyString) {
                        recMyString = (MyString) recObj;
                        if (recMyString.protocol_func == hd.DOWNLOAD) // 2
                        {
                            System.out.println("\nSERVER STATUS: RECEIVED OPERATION --> " + hd.DOWNLOAD);
                            System.out.println("SERVER STATUS: NAME RECEIVED FOR DOWNLOAD --> " + recMyString.myString);
                            downloadAFile_func = new DownloadAFile_Func();
                            MyFile myFile_toSend = downloadAFile_func.searchForFile(recMyString, allTheFiles);
                            if(myFile_toSend != null) {
                                /// Now send the Object file int the client ...
                                objectOutputStream.reset();
                                objectOutputStream.writeObject(myFile_toSend);
                                writeLogs(socket.getRemoteSocketAddress().toString(), hd.DOWNLOAD, recMyString.myString, writer);
                            }
                            else {
                                /// Now send the MyString Object to say to the server that the File NOT FOUND
                                MyString msg = new MyString(hd.FILE_NOT_EXISTS);
                                objectOutputStream.reset();
                                objectOutputStream.writeObject(msg);
                            }
                        }
                        if(recMyString.protocol_func == hd.DELETE) // 3
                        {
                            System.out.println("\nSERVER STATUS: RECEIVED OPERATION --> " + hd.DELETE);
                            System.out.println("SERVER STATUS: NAME RECEIVED FOR DELETION --> " + recMyString.myString);
                            deleteAFile_func = new DeleteAFile_Func();
                            /// everything is fine
                            if(deleteAFile_func.searchForFile(recMyString, allTheFiles).equals(hd.FILE_DELETED)) {
                                MyString msg = new MyString(hd.FILE_DELETED);
                                printAllFiles();
                                objectOutputStream.reset();
                                objectOutputStream.writeObject(msg);
                                writeLogs(socket.getRemoteSocketAddress().toString(), hd.DELETE, recMyString.myString, writer);
                            } else { // not fine ...
                                MyString msg = new MyString(hd.FILE_NOT_EXISTS);
                                objectOutputStream.reset();
                                objectOutputStream.writeObject(msg);
                            }
                        }
                    }
                }
            } catch (SocketException s)
            {
                System.out.println("\n\nSERVER STATUS: CONNECTION CLOSED....\n\n");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                    outputStreamWriter.close();
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void deleteAllInTheFile() {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("./Server/resources/LOG_FILE.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.write("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void DeleteAllInStorage() {
        File directory = new File("./Server/storage");
        try {
            for(File file: directory.listFiles())
                file.delete();
            System.out.println("SERVER STATUS: THE STORAGE HAS BEEN EMPTIED FOR THE NEW USER");
        } catch (NullPointerException n) {
            System.out.println("----> THIS IS THE ERROR IS NULL --- SEE DeleteAllInStorage in Server Class ...");
        }
    }
    private void writeLogs(String CLIENT_IP, int FUNC_CODE, String FILE_NAME, BufferedWriter writer) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy.HH:mm:ss");
        String timeStamp = df.format(new Date());
        String log = "///IP-CLIENT === " + CLIENT_IP + "///FUNC_CODE --> " + FUNC_CODE + "///FILE_NAME === " + FILE_NAME + "///DATE-TIME ===" + timeStamp + "///";
        System.out.println("SERVER STATUS: LOG GENERATED --> "+ log);
        try {
            writer.write(log);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void printAllFiles() {
        System.out.println("--------------------------------------------------------------------------------------------");
        for(int i = 0;i < allTheFiles.size();i++)
        {
            System.out.println("-- FILE " + i + " with name --> " + allTheFiles.get(i).name);
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }
}
