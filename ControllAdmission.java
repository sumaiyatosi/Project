package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ControllAdmission extends JFrame {
    JTextField tfStudent_ID, tfStudent_Name, tfProgram, tfDepartment, tfSemester, tfSession, tfGender, tfFathers_Name, tfCGPA, tfWaiver, tfOffer, tfPhone, tfEmail;
    JTextArea taAddress;
    JButton btnSearch, btnUpdate, btnDelete, btnBack;
    String role, userEmail;

    public ControllAdmission(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Control Admission");
        setSize(1000, 600);
        setLocation(240, 80);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Alice", Font.BOLD, 17);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("image/bg_img1.jpg"));
        Image i22 = i11.getImage().getScaledInstance(1000, 600, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i22));
        image.setBounds(0, 0, 1000, 600);
        setContentPane(image);
        image.setLayout(null);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(700, 20, 130, 40);
        btnSearch.setFont(labelFont);
        btnSearch.setBackground(Color.GRAY);
        btnSearch.setForeground(Color.WHITE);
        image.add(btnSearch);

        JLabel lblID = new JLabel("Student ID");
        lblID.setBounds(50, 70, 120, 25);
        lblID.setForeground(Color.WHITE);
        lblID.setFont(labelFont);
        image.add(lblID);

        tfStudent_ID = new JTextField();
        tfStudent_ID.setBounds(200, 70, 200, 28);
        tfStudent_ID.setFont(fieldFont);
        image.add(tfStudent_ID);

        JLabel lblName = new JLabel("Student Name");
        lblName.setBounds(50, 110, 120, 25);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(labelFont);
        image.add(lblName);

        tfStudent_Name = new JTextField();
        tfStudent_Name.setBounds(200, 110, 200, 28);
        tfStudent_Name.setFont(fieldFont);
        image.add(tfStudent_Name);

        JLabel lblProgram = new JLabel("Program");
        lblProgram.setBounds(50, 150, 120, 25);
        lblProgram.setForeground(Color.WHITE);
        lblProgram.setFont(labelFont);
        image.add(lblProgram);

        tfProgram = new JTextField();
        tfProgram.setBounds(200, 150, 200, 28);
        tfProgram.setFont(fieldFont);
        image.add(tfProgram);

        JLabel lblDept = new JLabel("Department");
        lblDept.setBounds(50, 190, 120, 25);
        lblDept.setForeground(Color.WHITE);
        lblDept.setFont(labelFont);
        image.add(lblDept);

        tfDepartment = new JTextField();
        tfDepartment.setBounds(200, 190, 200, 28);
        tfDepartment.setFont(fieldFont);
        image.add(tfDepartment);

        JLabel lblSem = new JLabel("Semester");
        lblSem.setBounds(50, 230, 120, 25);
        lblSem.setForeground(Color.WHITE);
        lblSem.setFont(labelFont);
        image.add(lblSem);

        tfSemester = new JTextField();
        tfSemester.setBounds(200, 230, 200, 28);
        tfSemester.setFont(fieldFont);
        image.add(tfSemester);

        JLabel lblSession = new JLabel("Session");
        lblSession.setBounds(50, 270, 120, 25);
        lblSession.setForeground(Color.WHITE);
        lblSession.setFont(labelFont);
        image.add(lblSession);

        tfSession = new JTextField();
        tfSession.setBounds(200, 270, 200, 28);
        tfSession.setFont(fieldFont);
        image.add(tfSession);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(50, 310, 120, 25);
        lblGender.setForeground(Color.WHITE);
        lblGender.setFont(labelFont);
        image.add(lblGender);

        tfGender = new JTextField();
        tfGender.setBounds(200, 310, 200, 28);
        tfGender.setFont(fieldFont);
        image.add(tfGender);

        JLabel lblFather = new JLabel("Father's Name");
        lblFather.setBounds(50, 350, 120, 25);
        lblFather.setForeground(Color.WHITE);
        lblFather.setFont(labelFont);
        image.add(lblFather);

        tfFathers_Name = new JTextField();
        tfFathers_Name.setBounds(200, 350, 200, 28);
        tfFathers_Name.setFont(fieldFont);
        image.add(tfFathers_Name);

        JLabel lblGPA = new JLabel("GPA");
        lblGPA.setBounds(500, 70, 120, 25);
        lblGPA.setForeground(Color.WHITE);
        lblGPA.setFont(labelFont);
        image.add(lblGPA);

        tfCGPA = new JTextField();
        tfCGPA.setBounds(630, 70, 200, 28);
        tfCGPA.setFont(fieldFont);
        image.add(tfCGPA);

        JLabel lblWaiver = new JLabel("Waiver");
        lblWaiver.setBounds(500, 110, 120, 25);
        lblWaiver.setForeground(Color.WHITE);
        lblWaiver.setFont(labelFont);
        image.add(lblWaiver);

        tfWaiver = new JTextField();
        tfWaiver.setBounds(630, 110, 200, 28);
        tfWaiver.setFont(fieldFont);
        image.add(tfWaiver);

        JLabel lblOffer = new JLabel("Offer");
        lblOffer.setBounds(500, 150, 120, 25);
        lblOffer.setForeground(Color.WHITE);
        lblOffer.setFont(labelFont);
        image.add(lblOffer);

        tfOffer = new JTextField();
        tfOffer.setBounds(630, 150, 200, 28);
        tfOffer.setFont(fieldFont);
        image.add(tfOffer);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setBounds(500, 190, 120, 25);
        lblPhone.setForeground(Color.WHITE);
        lblPhone.setFont(labelFont);
        image.add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(630, 190, 200, 28);
        tfPhone.setFont(fieldFont);
        image.add(tfPhone);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(500, 230, 120, 25);
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(labelFont);
        image.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(630, 230, 200, 28);
        tfEmail.setFont(fieldFont);
        image.add(tfEmail);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(500, 270, 120, 25);
        lblAddress.setForeground(Color.WHITE);
        lblAddress.setFont(labelFont);
        image.add(lblAddress);

        taAddress = new JTextArea();
        taAddress.setBounds(630, 270, 200, 28);
        taAddress.setFont(fieldFont);
        image.add(taAddress);

        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(250, 430, 120, 40);
        btnUpdate.setFont(labelFont);
        btnUpdate.setBackground(Color.GRAY);
        btnUpdate.setForeground(Color.WHITE);
        image.add(btnUpdate);

        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(380, 430, 120, 40);
        btnDelete.setFont(labelFont);
        btnDelete.setBackground(Color.GRAY);
        btnDelete.setForeground(Color.WHITE);
        image.add(btnDelete);

        btnBack = new JButton("CANCEL");
        btnBack.setBounds(510, 430, 120, 40);
        btnBack.setFont(labelFont);
        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        image.add(btnBack);

        btnBack.addActionListener(e -> {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        btnSearch.addActionListener(e -> searchStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());
        setVisible(true);
    }

    private void searchStudent() {
        String id = tfStudent_ID.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID");
            return;
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "")) {
            String query = "SELECT * FROM Student WHERE Student_ID=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tfStudent_Name.setText(rs.getString("Student_Name"));
                tfProgram.setText(rs.getString("Program"));
                tfDepartment.setText(rs.getString("Department"));
                tfSemester.setText(rs.getString("Semester"));
                tfSession.setText(rs.getString("Session"));
                tfGender.setText(rs.getString("Gender"));
                tfFathers_Name.setText(rs.getString("Fathers_Name"));
                tfCGPA.setText(String.valueOf(rs.getDouble("GPA")));
                tfWaiver.setText(rs.getString("Waiver"));
                tfOffer.setText(rs.getString("Offer"));
                tfPhone.setText(String.valueOf(rs.getInt("Phone")));
                tfEmail.setText(rs.getString("Email"));
                taAddress.setText(rs.getString("Address"));
            } else {
                JOptionPane.showMessageDialog(this, "No Record " + id);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
        }
    }

    private void updateStudent() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "")) {
            String query = "UPDATE Student SET Student_Name=?, Program=?, Department=?, Semester=?, Session=?, Gender=?, Fathers_Name=?, GPA=?, Waiver=?, Offer=?, Phone=?, Email=?, Address=? WHERE Student_ID=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, tfStudent_Name.getText());
            pst.setString(2, tfProgram.getText());
            pst.setString(3, tfDepartment.getText());
            pst.setString(4, tfSemester.getText());
            pst.setString(5, tfSession.getText());
            pst.setString(6, tfGender.getText());
            pst.setString(7, tfFathers_Name.getText());
            pst.setDouble(8, Double.parseDouble(tfCGPA.getText()));
            pst.setString(9, tfWaiver.getText());
            pst.setString(10, tfOffer.getText());
            pst.setInt(11, Integer.parseInt(tfPhone.getText()));
            pst.setString(12, tfEmail.getText());
            pst.setString(13, taAddress.getText());
            pst.setInt(14, Integer.parseInt(tfStudent_ID.getText()));

            int row = pst.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Updated");
            } else {
                JOptionPane.showMessageDialog(this, "Failed");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        String id = tfStudent_ID.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID");
            return;
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "")) {
            String query = "DELETE FROM Student WHERE Student_ID=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));
            int row = pst.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Deleted");
            } else {
                JOptionPane.showMessageDialog(this, "Failed");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        new ControllAdmission("DeptHead", "admin@gmail.com");
    }
}
