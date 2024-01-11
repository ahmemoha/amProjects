import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RandomColourGenerator extends JFrame {
    private JPanel mainPanel;
    private JButton generateButton;
    private JPanel colorPanel;
    private JLabel colorLabel;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandomColourGenerator();
            }
        });
    }
}
