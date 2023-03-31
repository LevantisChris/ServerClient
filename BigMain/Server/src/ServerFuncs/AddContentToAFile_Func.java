package ServerFuncs;
import ClientFuncs.MyFile;
import Protocol.header;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class AddContentToAFile_Func {
    private final header hd;
    public AddContentToAFile_Func(){
        hd = new header();
    }

    /* NOTE: The parameter MyFile recMyFile here contains the new content
    *  NOTE: Here to update the contents of the file in the storage we use the ArrayList MyFile, it is the same thing */
    public String searchAppendFile(MyFile recMyFile, ArrayList<MyFile> allTheFiles) {
        for(int i= 0;i < allTheFiles.size();i++) {
            if(allTheFiles.get(i).name.equals(recMyFile.name)) {
                System.out.println("SERVER STATUS: FILE WITH NAME --> " + allTheFiles.get(i).name + " FOUND");
                String old_content = new String(allTheFiles.get(i).contents_in_bytes);
                String new_content = new String(recMyFile.contents_in_bytes);
                old_content = old_content + " " + new_content; // we add the new content to the old on ...
                allTheFiles.get(i).contents_in_bytes = old_content.getBytes(); // make all back to bytes ...
                changeFile(allTheFiles.get(i)); // append the contents in the storage file ...
                return hd.CONTENT_ADDED;
            }
        }
        System.out.println("SERVER STATUS: FILE WITH NAME --> " + recMyFile.name + " NOT FOUND");
        return hd.FILE_NOT_EXISTS;
    }

    /* Update the contents of the file in the storage */
    private void changeFile(MyFile recMyFile) {
        File directory = new File("./Server/storage");
        FileWriter fileWriter = null;
        for(File file: directory.listFiles()) {
            /// Wea add the .txt to the name because the file in the storage is saved as text file, and the names will not be the same ...
            if (file.getName().equals(recMyFile.name + ".txt")) {
                try {
                    fileWriter = new FileWriter(file);
                    String new_contents = new String(recMyFile.contents_in_bytes);
                    fileWriter.write(new_contents);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
