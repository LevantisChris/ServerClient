/* This class is about the start of the GUI
(It will show a frame with only a menu) in the top */
package GUI;

import GUI.FUNCTIONS.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class AppMainFrame extends JFrame {
    private static int stateOfOpenFrame = 0; // to see if a JPanel is open
    ArrayList <JPanel> JPanels_active = new ArrayList<>();
    private int WIDTH = 900;
    private int HEIGHT = 900;

    private AppMainFrame_MENU menu;
    private MenuHandler handler;

    /// Functions
    private CreateAFile createAFile;
    private DownloadAFile downloadAFile;
    private DeleteAFile deleteAFile;
    private AddNewContentToAFile addNewContentToAFile;
    private AddContentToAFile addContentToAFile;

    /// For the CLIENT-SERVER connection
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    public AppMainFrame(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.socket = socket;

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("File App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setUndecorated(false);

        /// Menu
        menu = new AppMainFrame_MENU();
        handler = new MenuHandler();
        addListenersInMenu(handler);
        this.setJMenuBar(menu);

        this.setVisible(true);
    }

    private void addAPanel(JPanel panel, String new_title) {
        this.setTitle(new_title);
        stateOfOpenFrame = 1;
        JPanels_active.add(panel);
        this.add(panel);
        pack();
    }
    private void closeJPanel() {
        stateOfOpenFrame = 0;
        for (JPanel jPanel : JPanels_active) {
            this.remove(jPanel);
        }
        JPanels_active.removeAll(JPanels_active);
        this.repaint();
    }

    private void addListenersInMenu(MenuHandler handler) {
        menu.createAFile.addActionListener(handler);
        menu.downloadAFile.addActionListener(handler);
        menu.deleteAFile.addActionListener(handler);
        menu.rewriteAFile.addActionListener(handler);
        menu.appendAFile.addActionListener(handler);
        menu.exit.addActionListener(handler);
    }
    class MenuHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            /// If a JPanel is open close it...to be accurate we close all...
            if(stateOfOpenFrame == 1) {
                closeJPanel();
            }
            if(e.getSource() == menu.createAFile)
            {
                System.out.println("\nCLIENT STATUS: CREATE A FILE HAS BEEN PRESSED");
                createAFile = new CreateAFile(socket, objectOutputStream, objectInputStream); // JPanel for creating a file...
                addAPanel(createAFile, "Create a file");
            }
            if(e.getSource() == menu.downloadAFile)
            {
                System.out.println("\nCLIENT STATUS: DOWNLOAD A FILE HAS BEEN PRESSED");
                downloadAFile = new DownloadAFile(socket, objectOutputStream, objectInputStream);
                addAPanel(downloadAFile, "Download a file");
            }
            if(e.getSource() == menu.deleteAFile)
            {
                System.out.println("\nCLIENT STATUS: DELETE A FILE HAS BEEN PRESSED");
                deleteAFile = new DeleteAFile(socket, objectOutputStream, objectInputStream);
                addAPanel(deleteAFile, "Delete a file");
            }
            if(e.getSource() == menu.rewriteAFile)
            {
                System.out.println("\nCLIENT STATUS: REWRITE A FILE HAS BEEN PRESSED");
                addNewContentToAFile = new AddNewContentToAFile(socket, objectOutputStream, objectInputStream);
                addAPanel(addNewContentToAFile, "Add new content to a file");
            }
            if(e.getSource() == menu.appendAFile)
            {
                System.out.println("\nCLIENT STATUS: APPEND IN A FILE HAS BEEN PRESSED");
                addContentToAFile = new AddContentToAFile(socket, objectOutputStream, objectInputStream);
                addAPanel(addContentToAFile, "Append new content to a file");
            }
            if(e.getSource() == menu.exit)
            {
                System.out.println("\n\nCLIENT STATUS: EXIT APP PRESSED");
                System.exit(99);
            }
        }
    }
}
