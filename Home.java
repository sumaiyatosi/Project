package student_management_system;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
    RoundedButton btnAddStudent, btnShowStudents, btnOperation, btnAttendancePerformance, btnProgram, btnLibrary;
    JButton btnLogout, btnMoreOptions, btnPro, btnClassRoutine, btnSemesterResult, btnbook;
    boolean isExpanded = false;

    String role;
    String userEmail;

    private static Home instance;

    public static Home getInstance(String role, String userEmail) {
        if (instance == null) {
            instance = new Home(role, userEmail);
        }
        return instance;
    }

    Home(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Home");
        setSize(1000, 600);
        setLocation(240, 80);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("image/background-img.jpg"));
        Image i22 = i11.getImage().getScaledInstance(1000, 600, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i22));
        setContentPane(image);
        image.setLayout(null);

        JLabel headerLabel = new JLabel("'NORTH EAST UNIVERSITY BANGLADESH'", SwingConstants.CENTER);
        headerLabel.setBounds(160, 10, 500, 30);
        headerLabel.setFont(new Font("Alice", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        image.add(headerLabel);

        btnLogout = new JButton("Sign Out");
        btnLogout.setBounds(820, 20, 120, 40);
        btnLogout.setFont(new Font("Alice", Font.BOLD, 16));
        btnLogout.setBackground(Color.black);
        btnLogout.setForeground(Color.WHITE);
        image.add(btnLogout);

        btnLogout.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(Home.this, "Logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new Login().setVisible(true);
                dispose();
                instance = null; 
            }
        });

        btnAddStudent = new RoundedButton("Admission");
        btnAddStudent.setBounds(300, 110, 200, 50);
        image.add(btnAddStudent);

        btnShowStudents = new RoundedButton("University Student");
        btnShowStudents.setBounds(300, 170, 200, 50);
        image.add(btnShowStudents);

        btnOperation = new RoundedButton("Controll Admission");
        btnOperation.setBounds(300, 230, 200, 50);
        image.add(btnOperation);

        btnAttendancePerformance = new RoundedButton("Attendance");
        btnAttendancePerformance.setBounds(300, 290, 200, 50);
        image.add(btnAttendancePerformance);

        btnProgram = new RoundedButton("Registration");
        btnProgram.setBounds(300, 350, 200, 50);
        image.add(btnProgram);

        btnLibrary = new RoundedButton("Library");
        btnLibrary.setBounds(300, 410, 200, 50);
        image.add(btnLibrary);

        btnMoreOptions = new JButton("Learn More▼");
        btnMoreOptions.setBounds(40, 20, 120, 40);
        btnMoreOptions.setFont(new Font("Arial", Font.BOLD, 12));
        btnMoreOptions.setBackground(new Color(255, 255, 255));
        btnMoreOptions.setForeground(Color.black);
        image.add(btnMoreOptions);

        btnPro = new JButton("Program");
        btnPro.setBounds(40, 60, 120, 30);
        btnPro.setFont(new Font("Arial", Font.BOLD, 12));
        btnPro.setBackground(new Color(212, 252, 53));
        btnPro.setVisible(false);
        image.add(btnPro);

        btnClassRoutine = new JButton("Routine");
        btnClassRoutine.setBounds(40, 90, 120, 30);
        btnClassRoutine.setFont(new Font("Arial", Font.BOLD, 12));
        btnClassRoutine.setBackground(new Color(212, 252, 53));
        btnClassRoutine.setVisible(false);
        image.add(btnClassRoutine);

        btnSemesterResult = new JButton("Result");
        btnSemesterResult.setBounds(40, 120, 120, 30);
        btnSemesterResult.setFont(new Font("Arial", Font.BOLD, 12));
        btnSemesterResult.setBackground(new Color(212, 252, 53));
        btnSemesterResult.setVisible(false);
        image.add(btnSemesterResult);

        btnbook = new JButton("Books");
        btnbook.setBounds(40, 150, 120, 30);
        btnbook.setFont(new Font("Arial", Font.BOLD, 12));
        btnbook.setBackground(new Color(212, 252, 53));
        btnbook.setVisible(false);
        image.add(btnbook);

        btnMoreOptions.addActionListener(e -> {
            isExpanded = !isExpanded;
            btnPro.setVisible(isExpanded);
            btnClassRoutine.setVisible(isExpanded);
            btnSemesterResult.setVisible(isExpanded);
            btnbook.setVisible(isExpanded); 
            btnMoreOptions.setText(isExpanded ? "Learn More▲" : "Learn More▼");
        });

        btnAddStudent.setVisible(false);
        btnShowStudents.setVisible(false);
        btnOperation.setVisible(false);
        btnAttendancePerformance.setVisible(false);
        btnProgram.setVisible(false);
        btnLibrary.setVisible(false);

        switch (role) {
            case "Admin":
                btnAddStudent.setVisible(true);
                btnShowStudents.setVisible(true);
                btnOperation.setVisible(true);
                btnAttendancePerformance.setVisible(true);
                btnProgram.setVisible(true);
                btnLibrary.setVisible(true);
                btnPro.setVisible(false);
                btnClassRoutine.setVisible(false);
                btnSemesterResult.setVisible(false);
                btnbook.setVisible(false);
                btnAddStudent.addActionListener(e -> new Admission(role, userEmail).setVisible(true));
                btnShowStudents.addActionListener(e -> new Student(role, userEmail).setVisible(true));
                btnOperation.addActionListener(e -> new ControllAdmission(role, userEmail).setVisible(true));
                btnAttendancePerformance.addActionListener(e -> new Attendance(role, userEmail).setVisible(true));
                btnProgram.addActionListener(e -> new CourseRegistration(role, userEmail).setVisible(true));
                btnLibrary.addActionListener(e -> new Library(role, userEmail).setVisible(true));
                btnPro.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseEntered(java.awt.event.MouseEvent evt) { new Program().setVisible(true); } });
                btnClassRoutine.addActionListener(e -> new Routine(role, userEmail).setVisible(true));
                btnSemesterResult.addActionListener(e -> new Result(role, userEmail).setVisible(true));
                btnbook.addActionListener(e -> new Book(role, userEmail).setVisible(true));
                break;
            case "Teacher":
                btnAttendancePerformance.setVisible(true);
                btnPro.setVisible(false);
                btnClassRoutine.setVisible(false);
                btnSemesterResult.setVisible(false);
                btnbook.setVisible(false);
                btnAttendancePerformance.addActionListener(e -> new Attendance(role, userEmail).setVisible(true));
                btnPro.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseEntered(java.awt.event.MouseEvent evt) { new Program().setVisible(true); } });
                btnClassRoutine.addActionListener(e -> new Routine(role, userEmail).setVisible(true));
                btnSemesterResult.addActionListener(e -> new Result(role, userEmail).setVisible(true));
                btnbook.addActionListener(e -> new Book(role, userEmail).setVisible(true));
                break;
            case "DeptHead":
                btnAddStudent.setVisible(true);
                btnShowStudents.setVisible(true);
                btnOperation.setVisible(true);
                btnPro.setVisible(false);
                btnAddStudent.addActionListener(e -> new Admission(role, userEmail).setVisible(true));
                btnShowStudents.addActionListener(e -> new Student(role, userEmail).setVisible(true));
                btnOperation.addActionListener(e -> new ControllAdmission(role, userEmail).setVisible(true));
                btnPro.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseEntered(java.awt.event.MouseEvent evt) { new Program().setVisible(true); } });
                btnClassRoutine.addActionListener(e -> new Routine(role, userEmail).setVisible(true));
                break;
            case "CourseAdvisor":
                btnProgram.setVisible(true);
                btnPro.setVisible(false);
                btnClassRoutine.setVisible(false);
                btnSemesterResult.setVisible(false);
                btnProgram.addActionListener(e -> new CourseRegistration(role, userEmail).setVisible(true));
                btnPro.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseEntered(java.awt.event.MouseEvent evt) { new Program().setVisible(true); } });
                btnClassRoutine.addActionListener(e -> new Routine(role, userEmail).setVisible(true));
                btnSemesterResult.addActionListener(e -> new Result(role, userEmail).setVisible(true));
                break;
            case "Student":
                btnProgram.setVisible(true);
                btnPro.setVisible(false);
                btnClassRoutine.setVisible(false);
                btnSemesterResult.setVisible(false);
                btnbook.setVisible(false);
                btnProgram.addActionListener(e -> new CourseRegistration(role, userEmail).setVisible(true));
                btnPro.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseEntered(java.awt.event.MouseEvent evt) { new Program().setVisible(true); } });
                btnClassRoutine.addActionListener(e -> new Routine(role, userEmail).setVisible(true));
                btnSemesterResult.addActionListener(e -> new Result(role, userEmail).setVisible(true));
                btnbook.addActionListener(e -> new Book(role, userEmail).setVisible(true));
                break;
            case "LibraryOfficer":
                btnLibrary.setVisible(true);
                btnLibrary.addActionListener(e -> new Library(role, userEmail).setVisible(true));
                break;
        }
        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());
        setVisible(true);
    }
    public static void main(String[] args) {
        Home.getInstance("Admin", "admin@gmail.com").setVisible(true);
    }
}

class RoundedButton extends JButton {
    public RoundedButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.black);
        setBackground(Color.WHITE);
        setFont(new Font("Alice", Font.BOLD, 13));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
        super.paintComponent(g);
        g2.dispose();
    }
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
    }
}
