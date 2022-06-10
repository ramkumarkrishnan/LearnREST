import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class SnowPost {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtForm = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String strDate = now.format(dtForm);

        try {
            URL url = new URL(
                    "https://dev115005.service-now.com/api/now/table/incident?sysparm_limit=1");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            String userCredentials = "admin:/qHZV0+oXc0v";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encodeToString(userCredentials.getBytes()));
            conn.setRequestProperty("Authorization", basicAuth);

            conn.setDoOutput(true);
            String data = "{\"short_description\":\"OpCTL JavaMain Test-" +
                    strDate +
                    "-accessRequest_url: " +
                    "https://console.ap-chuncheon-1.oraclecloud.com/operator-access-control/" +
                    "access-requests/ocid1.opctlaccessrequest." +
                    "oc1.ap-chuncheon-1.aaaaaaaasgcujttbnki5fuwajivwtyqlmbrowxckwgahdjzquc7urfljuj2q\"}";
            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            OutputStream str = conn.getOutputStream();
            str.write(out);

            if (conn.getResponseCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode()
                        + " " + conn.getResponseMessage());
            }
            System.out.println("Succeeded : " + strDate + " HTTP Code: " +
                    + conn.getResponseCode()
                    + " " + conn.getResponseMessage());

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
