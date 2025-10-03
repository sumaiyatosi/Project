//Program Page
package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Program extends JFrame {
    public Program() 
    {
        setTitle("Program");
        setSize(300, 220);
        setLocation(410, 170);
        getContentPane().setBackground(new Color(255, 255, 255));
        setUndecorated(true);

        JLabel title = new JLabel("Program", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        title.setBackground(new Color(212, 252, 53));
        title.setPreferredSize(new Dimension(300, 40));
        add(title, BorderLayout.NORTH);

        String[] programs = 
        {
            "   BSc in Computer Science & Engineering",
            "   BSc in Electrical & Electronic Engineering",
            "   Bachelor of Business Administration",
            "   BA in English",
            "   Bachelor of Law",
            "   MSc in Computer Science",
            "   Master of Business Administration",
            "   MA in English"
        };

        JList<String> programList = new JList<>(programs);
        programList.setFont(new Font("Arial", Font.PLAIN, 15));
        programList.setBackground(new Color(255, 255, 255));
        programList.setForeground(Color.BLACK);

        programList.setSelectionBackground(programList.getBackground());
        programList.setSelectionForeground(programList.getForeground());

        programList.setCellRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent
                (list, value, index, false, false);
                label.setBackground(list.getBackground());
                label.setForeground(Color.BLACK);
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(programList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseMoved(MouseEvent e) 
            {
                int x = e.getX();
                int y = e.getY();
                if (x < 0 || y < 0 || x > getWidth() || y > getHeight()) 
                {
                    dispose();
                }
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) 
    {
        new Program();
    }
}
