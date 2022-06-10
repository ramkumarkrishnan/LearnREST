import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class SnowGet {
    public static void main(String[] args) {
        try {
            URL url = new URL(
"https://dev115005.service-now.com/api/now/table/incident?sysparm_limit=1");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            String userCredentials = "admin:/qHZV0+oXc0v";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encodeToString(userCredentials.getBytes()));
                    // new String(Base64.getEncoder().encodeToString(userCredentials.getBytes(StandardCharsets.UTF_8)));
            conn.setRequestProperty("Authorization", basicAuth);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode()
                        + " " + conn.getResponseMessage());
            }

            System.out.println("Output from Server .... \n");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((conn.getInputStream())));
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
