package ServerFuncs;
import ClientFuncs.MyFile;
import Protocol.header;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class CreateAFile_Func {
    private header hd;
    public CreateAFile_Func() {
        hd = new header();
    }
    public String saveTheFile(MyFile recMyFile, ArrayList <MyFile> allTheFiles)
    {
        String protocol_code = hd.FILE_CREATED;
        // if the array is empty that means there is no file send to the server ...
        if(allTheFiles.isEmpty()) {
            storeFile(recMyFile); // save the file in the server ...
            allTheFiles.add(recMyFile);
        } else {
            protocol_code = alreadyExists(recMyFile, allTheFiles);
            if (!protocol_code.equals(hd.CREATION_ERROR)) {
                storeFile(recMyFile); // save the file in the server ...
                allTheFiles.add(recMyFile);
            }
            return protocol_code;
        }
        return protocol_code;
    }
    private String alreadyExists(MyFile recMtFile, ArrayList <MyFile> allTheFiles) {
        for(int i = 0;i < allTheFiles.size();i++)
        {
            if(allTheFiles.get(i).name.equals(recMtFile.name))
            {
                System.out.println("STATUS SERVER: " + hd.FILE_ALREADY_EXISTS);
                return hd.CREATION_ERROR; // If there is a file with the same name the function cannot complete successfully ...
            }
        }
        System.out.println("STATUS SERVER: " + hd.FILE_NOT_EXISTS);
        return  hd.FILE_CREATED;
    }

    /* We want the server to store the files in the storage it has */
    private void storeFile(MyFile recMyFile) {
        File new_file = new File("./Server/storage/" + recMyFile.name + ".txt");
        try {
            if(new_file.createNewFile()) {
                System.out.println("SERVER STATUS: FILE WITH NAME " + recMyFile.name + "AND LENGTH " + new_file.length() + " SAVED IN SERVER STORAGE");
            } else {
                System.out.println("SERVER STATUS: ERROR, FILE ALREADY EXISTS WITH NAME " + recMyFile.name + "..."); // we don't want that ...
            }
            storeContentsFile(new_file, recMyFile);
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
}
