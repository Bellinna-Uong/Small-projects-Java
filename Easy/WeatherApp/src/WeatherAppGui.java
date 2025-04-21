import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    /** Stores the current weather data retrieved from the API */
    private JSONObject weatherData;

    public WeatherAppGui(){
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    /**
     * Creates and arranges all GUI components within the main window.
     * Components include:
     * - Search text field (top)
     * - Weather condition image (center)
     * - Temperature display (below weather image)
     * - Weather condition description
     * - Humidity display (bottom left)
     * - Wind speed display (bottom right)
     * - Search button (top right)
     * 
     * Each component is precisely positioned using absolute positioning (null layout)
     * and configured with specific fonts and sizes.
     */
    private void addGuiComponents(){
        // search field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        // weather
        JLabel weatherConditionImage = new JLabel(loadImage("cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // temperature
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // humidity
        JLabel humidityImage = new JLabel(loadImage("humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // windspeed
        JLabel windspeedImage = new JLabel(loadImage("windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // search button
        JButton searchButton = new JButton(loadImage("search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update
                String weatherCondition = (String) weatherData.get("weather_condition");
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");
                weatherConditionDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    /**
     * Loads and creates an ImageIcon from a resource file in the assets directory.
     * Uses the class loader to find resources in the classpath.
     *
     * @param resourcePath The name of the image file to load (e.g., "cloudy.png")
     * @return ImageIcon the loaded image as an ImageIcon, or null if loading fails
     */
    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image = ImageIO.read(getClass().getResource("/assets/" + resourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }
}