package ServerFuncs;

import ClientFuncs.MyFile;
import ClientFuncs.MyString;
import Protocol.header;

import java.io.File;
import java.util.ArrayList;

public class DeleteAFile_Func {
    private final header hd;
    public DeleteAFile_Func() {
        hd = new header();
    }
    /* If it finds the file we will send back a message that says FILE_ALREADY_EXIST else the file not exist */
    public String searchForFile(MyString myString, ArrayList<MyFile> allTheFiles) {
        for(int i= 0;i < allTheFiles.size();i++) {
            if(allTheFiles.get(i).name.equals(myString.myString)) {
                System.out.println("SERVER STATUS: FILE WITH NAME --> " + allTheFiles.get(i).name + " FOUND");
                deleteFile(allTheFiles.get(i)); // delete the file found from the Server ...
                allTheFiles.remove(i);
                System.out.println("SERVER STATUS: FILES IN THE SERVER --> " + allTheFiles.size());
                return hd.FILE_DELETED;
            }
        }
        System.out.println("SERVER STATUS: FILE WITH NAME --> " + myString.myString + " NOT FOUND");
        return hd.FILE_NOT_EXISTS;
    }

    /* This function deletes a file from the storage of the Server */
    private void deleteFile(MyFile file) {
        try {
            File file_to_delete = new File("./Server/storage/" + file.name + ".txt");

            if (file_to_delete.delete()) {
                System.out.println("SERVER STATUS: THE FILE HAS BEEN DELETED FROM THE SERVER STORAGE");
            } else {
                System.out.println("SERVER STATUS: ERROR, FILE NOT FOUND WITH THE NAME " + file.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
