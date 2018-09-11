package boxever.cucumber;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;

public abstract class BoxeverConfiguration {

    public static final String GIST_API_URI = "https://api.github.com";
    public static final String ACCESS_TOKEN = readConfigurationFile();


    /**
     * This method read the configuration file that contains the API access token
     * @return String: the access token
     */
    public static String readConfigurationFile() {
        String tokenValue = null;

        Properties prop = new Properties();
        InputStream input;

        {
            try {
                input = new FileInputStream("src/test/resources/credential.config");
                prop.load(input);
                tokenValue = prop.getProperty("token");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tokenValue;
    }
}
