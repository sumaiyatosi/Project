package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class Book extends JFrame {
    JTable table;
    DefaultTableModel model;
    JTextField searchField;
    JButton searchBtn, readBtn;
    String role, userEmail;

    public Book(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Books");
        setSize(1200, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Alice", Font.BOLD, 18);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        JLabel heading = new JLabel("BOOKS");
        heading.setFont(new Font("Alice", Font.PLAIN, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(550, 8, 200, 40);
        add(heading);

        JButton homeBtn = new JButton("⬅Home");
        homeBtn.setBounds(30, 10, 110, 35);
        homeBtn.setFont(labelFont);
        homeBtn.setBackground(Color.GRAY);
        homeBtn.setForeground(Color.WHITE);
        add(homeBtn);

        searchField = new JTextField();
        searchField.setBounds(860, 60, 150, 35);
        searchField.setFont(fieldFont);
        add(searchField);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(1010, 60, 100, 35);
        searchBtn.setFont(labelFont);
        searchBtn.setBackground(Color.ORANGE);
        searchBtn.setForeground(Color.WHITE);
        add(searchBtn);

        readBtn = new JButton("READ NOW");
        readBtn.setBounds(66, 60, 140, 35);
        readBtn.setFont(labelFont);
        readBtn.setBackground(Color.ORANGE);
        add(readBtn);

        model = new DefaultTableModel(new String[]{
            "Book ID", "Name", "Author", "Department", "File"
        }, 0);

        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Alice", Font.BOLD, 16));
        header.setBackground(Color.ORANGE);
        header.setForeground(Color.BLACK);
        table.setFont(new Font("Alice", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.setBackground(new Color(255, 235, 205));
        table.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(66, 135, 1050, 400);
        add(scrollPane);

        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("image/bg_img3.jpg"));
        Image scaledImg = bgImg.getImage().getScaledInstance(1200, 640, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setBounds(0, 0, 1200, 640);
        add(background);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        loadBooks("");

        homeBtn.addActionListener(e -> {
            dispose();
            new Home(role, userEmail).setVisible(true);
        });

        searchBtn.addActionListener(e -> loadBooks(searchField.getText()));
        readBtn.addActionListener(e -> openSelectedBook());
    }

    void loadBooks(String keyword) {
        model.setRowCount(0);
        String query;
        boolean searchByID = false;
        int bookID = -1;

        try {
            bookID = Integer.parseInt(keyword);
            searchByID = true;
        } catch (NumberFormatException ex) {
            searchByID = false;
        }

        try (Connection con = ConnectionProvider.getConnection()) {
            if (searchByID) {
                query = "SELECT * FROM Book WHERE Book_ID = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setInt(1, bookID);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("Book_ID"),
                                rs.getString("Name"),
                                rs.getString("Author"),
                                rs.getString("Department"),
                                rs.getString("File")
                        });
                    }
                }
            } else {
                query = "SELECT * FROM Book WHERE Name LIKE ? OR Author LIKE ? OR Department LIKE ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    String like = "%" + keyword + "%";
                    ps.setString(1, like);
                    ps.setString(2, like);
                    ps.setString(3, like);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("Book_ID"),
                                rs.getString("Name"),
                                rs.getString("Author"),
                                rs.getString("Department"),
                                rs.getString("File")
                        });
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }

        if (searchByID && table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
            table.scrollRectToVisible(table.getCellRect(0, 0, true));
        }
    }

    void openSelectedBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Take One");
            return;
        }
        String path = table.getValueAt(selectedRow, 4).toString();
        try {
            if (path != null && !path.isEmpty()) {
                Desktop.getDesktop().open(new File(path));
            } else {
                JOptionPane.showMessageDialog(this, "No File");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cann't Open" + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Book("Student", "student@gmail.com").setVisible(true);
    }
}
