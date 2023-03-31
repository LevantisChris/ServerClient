package GUI.FUNCTIONS;

import ClientFuncs.AddContentToAFile_Func;
import ClientFuncs.AddNewContentToAFile_Func;
import Protocol.header;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AddContentToAFile extends JPanel implements ActionListener {

    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
    private int WIDTH = 900;
    private int HEIGHT = 850;

    private JPanel UP_JPANEL;
    private JLabel enter_name_JLabel;
    private JTextField name_TextField;
    private JButton sumbit;

    private JPanel BOTTOM_JPANEL;
    private JTextArea contents_TextField; // Here will be all the contents of the file that the user add ...

    /// For the CLIENT-SERVER connection
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    private header hd = null;

    public AddContentToAFile(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream)
    {
        hd = new header(); // For the protocol

        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.socket = socket;

        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);

        //// START UP_JPANEL
        UP_JPANEL = new JPanel();
        UP_JPANEL.setBounds(0,0,WIDTH,40);
        UP_JPANEL.setLayout(new GridLayout(1,3));

        enter_name_JLabel = new JLabel("Enter name:");
        enter_name_JLabel.setFont(new Font("Serif", Font.BOLD, 30));
        enter_name_JLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enter_name_JLabel.setBorder(border);
        name_TextField = new JTextField();
        name_TextField.setHorizontalAlignment(SwingConstants.CENTER);
        name_TextField.setFont(new Font("Serif", Font.BOLD, 25));
        name_TextField.setBorder(border);
        sumbit = new JButton("Submit");
        sumbit.setFocusable(false);
        sumbit.setFont(new Font("Serif", Font.BOLD, 30));
        sumbit.setBorder(border);
        sumbit.addActionListener(this);

        UP_JPANEL.add(enter_name_JLabel);
        UP_JPANEL.add(name_TextField);
        UP_JPANEL.add(sumbit);
        //// END UP_JPANEL

        //// START BOTTOM_JPANEL
        BOTTOM_JPANEL = new JPanel();
        BOTTOM_JPANEL.setBorder(border);
        BOTTOM_JPANEL.setBounds(0,40,WIDTH,810);
        BOTTOM_JPANEL.setLayout(new GridLayout(1,1));

        contents_TextField = new JTextArea();
        contents_TextField.setBorder(border);
        contents_TextField.setFont(new Font("Serif", Font.BOLD, 25));
        contents_TextField.setForeground(Color.BLACK);
        contents_TextField.setBackground(Color.WHITE);

        BOTTOM_JPANEL.add(contents_TextField);
        //// END BOTTOM_JPANEL
        this.add(UP_JPANEL);
        this.add(BOTTOM_JPANEL);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sumbit)
        {
            System.out.println("CLIENT STATUS: SUBMIT BUTTON IN ADDCONTENTTOAFILE CLASS HAS BEEN PRESSED");
            if(contents_TextField.getText().trim().equals("")) { // if the user don't enter a text in the TEXTAREA the operation cannot complete ...
                JOptionPane.showMessageDialog(null, "You have to add something to the file to rewrite it", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                AddContentToAFile_Func addNewContentToAFile_func = new AddContentToAFile_Func(hd.ADD_CONTENT, socket, objectOutputStream, objectInputStream, contents_TextField.getText(), name_TextField.getText());
            }
        }
    }
}
