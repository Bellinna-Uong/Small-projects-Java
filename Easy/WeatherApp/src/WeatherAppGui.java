import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    public WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JTextField searchField = new JTextField();
        searchField.setBounds(15,15,351,45);
        searchField.setFont(new Font("Dialog", Font.PLAIN,24));
        add(searchField);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcepath) {
        try{
            BufferedImage image = ImageIO.read(new File(resourcepath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Couldn't load image");
        return null;
    }
}