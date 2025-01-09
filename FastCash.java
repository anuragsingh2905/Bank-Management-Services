package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    private String pin; // Instance variable for PIN
    private JButton b1, b2, b3, b4, b5, b6, b7; // Declare buttons as instance variables

    public FastCash(String pin) {
        this.pin = pin;

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        // Label
        JLabel label = new JLabel("SELECT WITHDRAWAL AMOUNT");
        label.setBounds(440, 180, 700, 35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System", Font.BOLD, 25));
        l3.add(label);

        // Buttons
        b1 = new JButton("Rs. 100");
        b1.setBounds(410, 274, 150, 35);
        styleButton(b1, l3);

        b2 = new JButton("Rs. 500");
        b2.setBounds(700, 274, 150, 35);
        styleButton(b2, l3);

        b3 = new JButton("Rs. 1000");
        b3.setBounds(410, 318, 150, 35);
        styleButton(b3, l3);

        b4 = new JButton("Rs. 2000");
        b4.setBounds(700, 318, 150, 35);
        styleButton(b4, l3);

        b5 = new JButton("Rs. 5000");
        b5.setBounds(410, 362, 150, 35);
        styleButton(b5, l3);

        b6 = new JButton("Rs. 10,000");
        b6.setBounds(700, 362, 150, 35);
        styleButton(b6, l3);

        b7 = new JButton("BACK");
        b7.setBounds(700, 406, 150, 35);
        styleButton(b7, l3);

        // Frame settings
        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    private void styleButton(JButton button, JLabel l3) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(65, 125, 128));
        button.addActionListener(this);
        l3.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b7) {
            setVisible(false);
            new main_Class(pin);
        } else {
            String amount = ((JButton) e.getSource()).getText().substring(4);
            Con c = new Con();
            Date date = new Date();
            try {
                ResultSet resultSet = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
                int balance = 0;
                while (resultSet.next()) {
                    if (resultSet.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }

                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                c.statement.executeUpdate(
                        "INSERT INTO bank VALUES('" + pin + "', '" + date + "', 'Withdrawal', '" + amount + "')");
                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            setVisible(false);
            new main_Class(pin);
        }
    }

    public static void main(String[] args) {
        new FastCash("");
    }
}
