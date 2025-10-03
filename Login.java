package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.*;

public class Login extends JFrame {
    JTextField textFieldEmail;
    JPasswordField passwordField;
    JButton login, cancel;

    public Login() {
        setTitle("Login");
        setSize(800, 500);
        setLocation(300, 140);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("image/icon.png")).getImage());

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("image/login.jpg"));
        Image i22 = i11.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i22));
        setContentPane(image); 
        image.setLayout(null);

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setForeground(Color.white);
        labelEmail.setBounds(100, 145, 200, 60);
        labelEmail.setFont(new Font("Alice", Font.PLAIN, 18));
        image.add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(210, 160, 150, 35);
        textFieldEmail.setBorder(createRoundedBorder(15));
        image.add(textFieldEmail);

        JLabel labelPass = new JLabel("Password");
        labelPass.setForeground(Color.white);
        labelPass.setBounds(100, 195, 200, 60);
        labelPass.setFont(new Font("Alice", Font.PLAIN, 18));
        image.add(labelPass);

        passwordField = new JPasswordField();
        passwordField.setBounds(210, 210, 150, 35);
        passwordField.setBorder(createRoundedBorder(15));
        image.add(passwordField);

        login = new JButton("Sign In");
        login.setBounds(105, 290, 100, 40);
        login.setBackground(Color.gray);
        login.setForeground(Color.white);
        login.setBorder(createRoundedBorder(20));
        image.add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(235, 290, 100, 40);
        cancel.setBackground(Color.gray);
        cancel.setForeground(Color.white);
        cancel.setBorder(createRoundedBorder(20));
        image.add(cancel);

        JLabel forgotLink = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotLink.setForeground(Color.white);
        forgotLink.setBounds(170, 330, 150, 40);
        forgotLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        image.add(forgotLink);

        JLabel signupLink = new JLabel("Don't have an account?Sign Up");
        signupLink.setForeground(Color.white);
        signupLink.setBounds(135, 350, 250, 40);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        image.add(signupLink);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("image/loginIcon.png"));
        Image i2 = i1.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT);
        JLabel img = new JLabel(new ImageIcon(i2));
        img.setBounds(170, 10, 100, 130);
        image.add(img);

        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setOpaque(false);
        contactPanel.setBounds(500, 360, 400, 150);

        JLabel nameLbl = new JLabel("NORTH EAST UNIVERSITY BANGLADESH");
        JLabel emailLbl = new JLabel("Email : neub@gmail.com");
        JLabel phoneLbl = new JLabel("Phone : +880-1300567890");
        JLabel addressLbl = new JLabel("Address : Telihaor, Sheikhghat, Sylhet-3100");
        JLabel websiteLbl = new JLabel("Website : www.neub.edu.bd");

        JLabel[] labels = {nameLbl, emailLbl, phoneLbl, addressLbl, websiteLbl};
        for (JLabel lbl : labels) {
            lbl.setFont(new Font("Alice", Font.BOLD, 13));
            lbl.setForeground(Color.white);
            contactPanel.add(lbl);
        }
        image.add(contactPanel);

        login.addActionListener(e -> {
            String email = textFieldEmail.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(Login.this, "Fill in All", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Connection con = ConnectionProvider.getConnection();
                String query = "SELECT * FROM Login WHERE email = ? AND password = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    JOptionPane.showMessageDialog(Login.this, "" + role);
                    new Home(role, email).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid", "Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                stmt.close();
                con.close();
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Login.this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> dispose());

        forgotLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new ForgotPassword().setVisible(true);
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                forgotLink.setForeground(Color.yellow);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                forgotLink.setForeground(Color.WHITE);
            }
        });

        signupLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new SignUp().setVisible(true);
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                signupLink.setForeground(Color.yellow);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                signupLink.setForeground(Color.WHITE);
            }
        });
        setVisible(true);
    }
    private Border createRoundedBorder(int radius) {
        return new LineBorder(Color.GRAY, 2, true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
