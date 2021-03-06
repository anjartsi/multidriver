package multidriver;

import javax.swing.*;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by aourf on 7/4/2017.
 */
public class MultiDriverGUI extends JPanel{
    private JFrame parentFrame;
    private JPanel panel_url;
    private JTextField text_url;
    private JButton button_begin;
    private JButton button_stop;

    private JPanel panel_users;
    private ArrayList<JTextField> list_users;
    private ArrayList<JPasswordField> list_passwords;

    private JMenuBar menuBar;

    private DriverFactory driverFactory;
    public MultiDriverGUI(JFrame f) {
        super(true);
        this.parentFrame = f;

        this.setLayout(null);
        initializeComponents();
        setupUrlPanel();
        setupUsersPanel(10);
        setupMenu();

        driverFactory = new DriverFactory();
    }

    private void initializeComponents() {
        panel_url = new JPanel();
        text_url = new JTextField();
        button_begin = new JButton();
        button_stop = new JButton();

        panel_users = new JPanel();
        list_users = new ArrayList();
        list_passwords = new ArrayList();

        menuBar = new JMenuBar();
    }

    private void setupUrlPanel() {
        panel_url.setBackground(Color.DARK_GRAY);
        panel_url.setSize(500, 100);
        panel_url.setLocation(10, 10);
        panel_url.setLayout(null);

        text_url.setSize(325, 50);
        text_url.setLocation(25, 25);
        text_url.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        text_url.setText("https://google.com");
        text_url.addFocusListener(new SelectAll());
        panel_url.add(text_url);

        button_begin.setText("Begin!");
        button_begin.setSize(100, 50);
        button_begin.setLocation(350, 25);
        button_begin.addActionListener(new Begin());
        panel_url.add(button_begin);

        button_stop.setText("Stop!");
        button_stop.setSize(100, 50);
        button_stop.setLocation(350, 25);
        button_stop.addActionListener(new Stop());
        this.add(panel_url);
    }

    private void setupUsersPanel(int n) {
        panel_users.setBackground(Color.DARK_GRAY);
        panel_users.setSize(500, 500);
        panel_users.setLayout(new GridLayout(n + 1, 2));
        panel_users.setLocation(10, 120);

        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
        JLabel usr = new JLabel("Usernames");
        usr.setFont(f);
        usr.setForeground(Color.white);
        JLabel pwd = new JLabel("Passwords");
        pwd.setFont(f);
        pwd.setForeground(Color.white);
        panel_users.add(usr);
        panel_users.add(pwd);

        for(int i = 0; i < n; i++) {
            createUsernameAndPassword();
        }

        this.add(panel_users);
    }

    private void setupMenu() {
        JMenu m_file = new JMenu("File");
        JMenuItem mi_save = new JMenuItem("Save");
        JMenuItem mi_saveAs = new JMenuItem("Save As ...");
        JMenuItem mi_load = new JMenuItem("Load");
        JMenuItem mi_exit = new JMenuItem("Exit");
        m_file.add(mi_save);
        m_file.add(mi_saveAs);
        m_file.add(mi_load);
        m_file.add(mi_exit);

        JMenu m_adv = new JMenu("Advanced");
        JMenuItem mi_browser = new JMenuItem("Change Browser...");
        JMenuItem mi_selectors = new JMenuItem("Change Webdriver Selectors...");
        m_adv.add(mi_browser);
        m_adv.add(mi_selectors);

        menuBar.add(m_file);
        menuBar.add(m_adv);

        parentFrame.setJMenuBar(menuBar);

    }

    private void createUsernameAndPassword() {
        int width, height, fontSize;
        width = 240;
        height = 40;
        fontSize = 20;

        JTextField tf;
        tf = new JTextField();
        tf.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
        tf.setPreferredSize(new Dimension(width, height));
        tf.addFocusListener(new SelectAll());
        list_users.add(tf);
        panel_users.add(tf);

        JPasswordField pw;
        pw = new JPasswordField();
        pw.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        pw.setPreferredSize(new Dimension(width, height));
        pw.addFocusListener(new SelectAll());
        list_passwords.add(pw);
        panel_users.add(pw);
    }

    private class SelectAll implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField tf = (JTextField) e.getSource();
            tf.select(0, tf.getText().length());
        }

        @Override
        public void focusLost(FocusEvent e) {}
    }

    private class Begin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel_url.remove(1);
            panel_url.add(button_stop);
            panel_url.updateUI();

            driverFactory.setUrl(text_url.getText());
            String usr, pwd;
            for(int i = 0; i < list_users.size(); i++) {
                // If both fields are filled out
                usr = list_users.get(i).getText();
                pwd = new String(list_passwords.get(i).getPassword());
                if(!usr.equals("") && !pwd.equals("")  ) {
                    try {driverFactory.login(usr, pwd);
                    } catch (Exception exc) { exc.printStackTrace();}
                }
            }
        }
    }

    private class Stop implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel_url.remove(1);
            panel_url.add(button_begin);
            panel_url.updateUI();

            driverFactory.closeAllDrivers();
        }
    }

    public void quit() {
        driverFactory.closeAllDrivers();
    }

    public static MultiDriverGUI buildGUI() {
        JFrame frame = new JFrame("Multiple Web Drivers");
        frame.setSize(530, 690);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MultiDriverGUI md = new MultiDriverGUI(frame);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                md.quit();
            }
        });
        frame.setContentPane(md);
        frame.setVisible(true);

        return md;
    }
}
