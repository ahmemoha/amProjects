import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * The PasswordChecker class represents a simple Swing application for checking the strength of passwords.
 * It includes a GUI with a text field for entering a password, a "Check" button, and a text area for displaying
 * the password strength evaluation.
 */
public class PasswordChecker extends JFrame {

    private JLabel passwordLabel;
    private JTextField passwordField;
    private JButton checkButton;
    private JTextArea strengthText;

    // List of common easy passwords to be checked against
    private static final List<String> COMMON_PASSWORDS = Arrays.asList(
            "password", "123456", "123456789", "2345678", "12345", "qwerty",
            "abc123", "football", "1234567", "monkey", "111111", "letmein",
            "1234", "1234567890", "dragon", "baseball", "sunshine", "iloveyou",
            "trustno1", "123123", "welcome", "login", "admin", "princess",
            "adobe123[a]", "1q2w3e4r", "master", "solo", "1qaz2wsx",
            "ashley", "mustang", "121212", "starwars", "bailey", "access",
            "flower", "555555", "passw0rd", "shadow", "lovely", "654321",
            "7777777", "!@#$%^&*", "jesus", "password1", "superman", "696969",
            "hottie", "freedom", "aa123456", "qazwsx", "ninja", "azerty",
            "solo", "loveme", "whatever", "donald", "michael", "batman",
            "zaq1zaq1", "Football", "000000", "qwerty123", "123qwe", "Keeper");

    /**
     * Constructs a new PasswordChecker instance.
     * Initializes the GUI components and sets up the event handling for the "Check" button.
     */
    public PasswordChecker() {
        setTitle("Password Strength Checker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(20, 20, 120, 30);
        add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setBounds(150, 20, 200, 30);
        add(passwordField);

        checkButton = new JButton("Check");
        checkButton.setBounds(150, 70, 100, 30);
        add(checkButton);

        strengthText = new JTextArea();
        strengthText.setBounds(20, 120, 350, 100);
        strengthText.setFont(new Font("Arial", Font.PLAIN, 13));

        Color lighterGray = new Color(238, 238, 238, 255);
        strengthText.setBackground(lighterGray);
        strengthText.setWrapStyleWord(true);
        strengthText.setLineWrap(true);
        strengthText.setEditable(false);
        add(strengthText);

        // Event handling for the "Check" button
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = passwordField.getText();
                String strength = checkPasswordStrength(password);
                strengthText.setText(strength);
            }
        });
    }

    /**
     * Checks the strength of a given password based on various criteria.
     *
     * @param password The password to be evaluated.
     * @return A String indicating the password strength and any requirements not met.
     */
    private String checkPasswordStrength(String password) {
        // Check if the password is in the list of common easy passwords
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            strengthText.setForeground(Color.RED);
            return "Password Strength: Not strong enough. Avoid using common passwords.";
        }

        // Check minimum length
        boolean hasMinimumLength = password.length() >= 8;

        // Check complexity requirements
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[~!@#$%^&*_\\-+=`|(){}\\[\\]:;\"'<>,.?/].*");

        // Build strength message based on criteria met
        StringBuilder strength = new StringBuilder("Password Strength: ");

        if (hasMinimumLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar) {
            strength.append("Strong!");
            strengthText.setForeground(Color.GREEN.darker());
        } else {
            strength.append("Not strong enough. Requirements:\n");
            if (!hasMinimumLength) {
                strength.append("- Minimum 8 characters\n");
            }
            if (!hasUpperCase) {
                strength.append("- At least one uppercase letter\n");
            }
            if (!hasLowerCase) {
                strength.append("- At least one lowercase letter\n");
            }
            if (!hasDigit) {
                strength.append("- At least one digit\n");
            }
            if (!hasSpecialChar) {
                strength.append("- At least one special character\n");
            }
            strengthText.setForeground(Color.RED);
        }
        return strength.toString();
    }

    /**
     * Main method to launch the PasswordChecker application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PasswordChecker passwordStrengthChecker = new PasswordChecker();
                passwordStrengthChecker.setVisible(true);
            }
        });
    }
}
