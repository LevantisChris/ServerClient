package GUI.FUNCTIONS;

import ClientFuncs.DownloadAFile_Func;
import Protocol.header;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DownloadAFile extends JPanel implements ActionListener {
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
    private int WIDTH = 900;
    private int HEIGHT = 60;

    private JLabel give_the_name_JLabel;
    private JTextField name_JTextField;
    private JButton sumbit;

    /// For the CLIENT-SERVER connection
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    private header hd = null;
    public DownloadAFile(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        hd = new header(); // For the protocol

        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.socket = socket;

        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new GridLayout(1,3));

        give_the_name_JLabel = new JLabel("Give the name:");
        give_the_name_JLabel.setFont(new Font("Serif", Font.BOLD, 30));
        give_the_name_JLabel.setHorizontalAlignment(SwingConstants.CENTER);
        give_the_name_JLabel.setBorder(border);

        name_JTextField = new JTextField();
        name_JTextField.setHorizontalAlignment(SwingConstants.CENTER);
        name_JTextField.setFont(new Font("Serif", Font.BOLD, 25));
        name_JTextField.setBorder(border);

        sumbit = new JButton("Submit");
        sumbit.setFocusable(false);
        sumbit.setFont(new Font("Serif", Font.BOLD, 30));
        sumbit.setBorder(border);
        sumbit.addActionListener(this);

        this.add(give_the_name_JLabel);
        this.add(name_JTextField);
        this.add(sumbit);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sumbit)
        {
            System.out.println("CLIENT STATUS: SUBMIT BUTTON IN DOWNLOADAFILE CLASS HAS BEEN PRESSED");
            DownloadAFile_Func downloadAFile_func = new DownloadAFile_Func(hd.DOWNLOAD, socket, objectOutputStream, objectInputStream, name_JTextField.getText());
        }
    }
}
