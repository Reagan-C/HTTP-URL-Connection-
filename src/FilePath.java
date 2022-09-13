import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FilePath {
    static URL url;

    public FilePath(String path) {
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
    }
public static void setUpConnection () {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                System.out.println("Error reading web page");
                System.out.println(connection.getResponseMessage());
            }

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                                            connection.getInputStream()));
            String line;
            while ((line = inputReader.readLine()) != null) {
                System.out.println(line);
            }
            inputReader.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
