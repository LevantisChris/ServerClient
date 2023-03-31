package ServerFuncs;

import ClientFuncs.MyFile;
import Protocol.header;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddNewContentToAFile_Func {
    private final header hd;
    public AddNewContentToAFile_Func() {
        hd = new header();
    }
    /* This func ckecks is the file exist */
    public String alreadyExists(MyFile recMyFile, ArrayList <MyFile> allTheFiles) {
        for(int i = 0;i < allTheFiles.size();i++)
        {
            if(allTheFiles.get(i).name.equals(recMyFile.name))
            {
                System.out.println("STATUS SERVER: " + hd.NEW_CONTENT_ADDED_TO_THE_FILE);
                rewriteTheFile(recMyFile, allTheFiles, i);
                changeFile(recMyFile);
                return hd.NEW_CONTENT_ADDED_TO_THE_FILE;
            }
        }
        System.out.println("STATUS SERVER: " + hd.FILE_NOT_EXISTS);
        return  hd.FILE_NOT_EXISTS;
    }
    private void rewriteTheFile(MyFile file_new, ArrayList <MyFile> allTheFiles, int index) {
        allTheFiles.set(index, file_new); // set the old file to the new ...
    }

    /* Here we are going to change the contents of the file inside the storage */
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
