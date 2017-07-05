package multidriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by aourf on 7/4/2017.
 */
public class MultiDriverGUI extends JPanel{
    private JPanel panel_url;
    private JTextField text_url;
    private JButton button_begin;

    private JPanel panel_users;
    JPanel panel_left;
    JPanel panel_right;
    private ArrayList<JTextField> list_users;
    private ArrayList<JPasswordField> list_passwords;
    private JButton button_add;

    public MultiDriverGUI() {
        super(true);

        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        initializeComponents();
        setupUrlPanel();
        setupUsersPanel(3);
    }

    private void initializeComponents() {
        panel_url = new JPanel();
        text_url = new JTextField();
        button_begin = new JButton();

        panel_users = new JPanel();
        panel_left = new JPanel();
        list_users = new ArrayList();
        panel_right = new JPanel();
        list_passwords = new ArrayList();
        button_add = new JButton();
    }

    private void setupUrlPanel() {
        panel_url.setSize(500, 100);
        panel_url.setLocation(10, 10);
        panel_url.setLayout(null);
//        panel_url.setBackground(Color.DARK_GRAY);

        text_url.setSize(325, 50);
        text_url.setLocation(25, 25);
        text_url.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        text_url.setText("https://google.com");
        panel_url.add(text_url);

        button_begin.setText("Begin!");
        button_begin.setSize(100, 50);
        button_begin.setLocation(350, 25);
        button_begin.addActionListener(new Begin());
        panel_url.add(button_begin);

        this.add(panel_url);
    }

    private void setupUsersPanel(int n) {
//        panel_users.setBackground(Color.DARK_GRAY);
        panel_users.setLayout(new BorderLayout());
        panel_users.setSize(500, 500);
        panel_users.setLocation(10, 120);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setOpaque(false);
        panel_left.setOpaque(false);
        panel_right.setOpaque(false);

        splitPane.setLeftComponent(panel_left);
        splitPane.setRightComponent(panel_right);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(2);
//        splitPane.setEnabled(false);

        for(int i = 0; i < n; i++) {
            createUsernameAndPassword();
        }

        button_add.setText("Add Username/Password");
        button_add.addActionListener(new AddUser());



        panel_users.add(splitPane);
        panel_users.add(button_add, BorderLayout.SOUTH);
        this.add(panel_users);
    }

    private void createUsernameAndPassword() {
        int width, height, fontSize;
        width = 200;
        height = 50;
        fontSize = 25;

        JTextField tf;
        tf = new JTextField();
        tf.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
        tf.setPreferredSize(new Dimension(width, height));
        list_users.add(tf);
        panel_left.add(tf);

        JPasswordField pw;
        pw = new JPasswordField();
        pw.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        pw.setPreferredSize(new Dimension(width, height));
        list_passwords.add(pw);
        panel_right.add(pw);
    }

    private class AddUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createUsernameAndPassword();
            panel_users.updateUI();
        }
    }
    private class Begin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    public static MultiDriverGUI buildGUI() {
        JFrame frame = new JFrame("Multiple Web Drivers");
        frame.setSize(530, 690);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MultiDriverGUI md = new MultiDriverGUI();

        frame.setContentPane(md);
        frame.setVisible(true);

        return md;
    }
}
