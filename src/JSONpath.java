import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONpath {
    static URL url;

    public JSONpath(String path) {
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
    }

    public static void setUpConnection() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

            if (responseCode != 200) {
                System.out.println("Error reading web page");
                System.out.println(connection.getResponseMessage());
            }

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                                            connection.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = inputReader.readLine()) != null) {
                builder.append(line);
            }
            Object obj = new JSONParser().parse(builder.toString());
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            inputReader.close();

//            System.out.println(jsonObject.get("id"));
//            System.out.println(jsonObject.get("completed"));
//            System.out.println(jsonObject.get("userId"));
//            System.out.println(jsonObject.get("title"));
        } catch (IOException |NullPointerException e) {
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e.getMessage());
        }
    }
}
