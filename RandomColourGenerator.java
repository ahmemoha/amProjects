import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The RandomColourGenerator class creates a simple graphical user interface (GUI)
 * application that generates random colors and displays them along with their hexadecimal values.
 *
 * The application includes a button to trigger the generation of a new random color,
 * a color panel to display the generated color, and a label showing the hexadecimal value of the color.
 */
public class RandomColourGenerator extends JFrame {
    private JPanel mainPanel;
    private JButton generateButton;
    private JPanel colorPanel;
    private JLabel colorLabel;

    /**
     * Constructs a new RandomColourGenerator instance.
     * Initializes the GUI components, sets up the layout, and makes the frame visible.
     */
    public RandomColourGenerator() {
        setTitle("Random Color Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 240);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(150, 150));
        mainPanel.add(colorPanel, BorderLayout.CENTER);

        colorLabel = new JLabel();
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(colorLabel, BorderLayout.SOUTH);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomColorRectangle();
            }
        });
        mainPanel.add(generateButton, BorderLayout.NORTH);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Generates a random color and updates the color panel and label accordingly.
     * Uses the RGB color model to create a random color and displays its hexadecimal value.
     */
    private void generateRandomColorRectangle() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        Color randomColor = new Color(red, green, blue);
        colorPanel.setBackground(randomColor);

        String hexColor = String.format("#%02x%02x%02x", red, green, blue);
        colorLabel.setFont(new Font("Ways", Font.PLAIN, 15));
        colorLabel.setText(hexColor);
    }

    /**
     * The main method to launch the RandomColourGenerator application.
     * Invoked on the Event Dispatch Thread to ensure thread safety in Swing applications.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandomColourGenerator();
            }
        });
    }
}

