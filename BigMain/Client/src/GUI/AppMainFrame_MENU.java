package GUI;

import javax.swing.*;

public class AppMainFrame_MENU extends JMenuBar{
    public JMenu menu; // Menu
    public JMenuItem createAFile;
    public JMenuItem downloadAFile;
    public JMenuItem deleteAFile;
    public JMenuItem rewriteAFile; // rewrite the file and add the new contents
    public JMenuItem appendAFile; // don't rewrite in the file, just add the new content
    public JMenuItem exit;
    public AppMainFrame_MENU()
    {
        menu = new JMenu("Functions");
        createAFile = new JMenuItem("Create a file");
        downloadAFile = new JMenuItem("Download a file");
        deleteAFile = new JMenuItem("Delete a file");
        rewriteAFile = new JMenuItem("Rewrite a file");
        appendAFile = new JMenuItem("Add Content in a file");
        exit = new JMenuItem("Exit the app");

        menu.add(createAFile);
        menu.add(downloadAFile);
        menu.add(deleteAFile);
        menu.add(rewriteAFile);
        menu.add(appendAFile);
        menu.add(exit);

        this.add(menu);
    }
}
