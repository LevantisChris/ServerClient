package Protocol;

public class header {
    /// Operations
    public int CREATE = 1;
    public int DOWNLOAD = 2;
    public int DELETE = 3;
    public int ADD_NEW_CONTENT = 4; // Rewrite the file ...
    public int ADD_CONTENT = 5; // DON'T rewrite the file, just add the new content

    /// Errors
    public String FILE_ALREADY_EXISTS = "THE FILE ALREADY EXISTS";
    public String FILE_NOT_EXISTS = "THE FILE NOT EXISTS";
    public String CREATION_ERROR = "THE FILE IS NOT CREATED";

    /// All Done
    public String FILE_CREATED = "THE FILE CREATED";
    public String FILE_DELETED = "THE FILE DELETED";
    public String NEW_CONTENT_ADDED_TO_THE_FILE = "THE NEW CONTENTS HAS BEEN ADDED TO THE OLD FILE";
    public String CONTENT_ADDED = "THE CONTENT HAS BEEN APPENDED TO THE OLD ONE";
}
