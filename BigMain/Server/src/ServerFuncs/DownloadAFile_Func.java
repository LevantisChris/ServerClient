package ServerFuncs;
import ClientFuncs.MyFile;
import ClientFuncs.MyString;
import Protocol.header;
import java.util.ArrayList;
public class DownloadAFile_Func {
    public DownloadAFile_Func(){
        header hd = new header();
    }
    /* If the function find the file with the specific Name the client has give the file will be go back to the client */
    public MyFile searchForFile(MyString myString, ArrayList <MyFile> allTheFiles) {
        for (MyFile allTheFile : allTheFiles) {
            if (allTheFile.name.equals(myString.myString)) {
                String string = new String(allTheFile.contents_in_bytes);
                System.out.println("SERVER STATUS: FILE WITH NAME --> " + allTheFile.name + " FOUND--- cont --> " + string);
                return allTheFile;
            }
        }
        System.out.println("SERVER STATUS: FILE WITH NAME --> " + myString.myString + " NOT FOUND");
        return null; // if the for loop doesn't find something, it will return null ...
    }
}
