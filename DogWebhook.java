import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.google.gson.Gson;

public class DogWebhook {

    public static void main(String[] args) throws Exception {
        String webhookUrl = "https://discord.com/api/webhooks/1228809675085054042/e-7QONLpgKn0xhhp4RJkxPzXlFfZM81zPqWX0USwI6J7D04vE8PvXKIqhuUoKY9HeVce";
        String dogApiUrl = "https://api.thedogapi.com/v1/images/search?size=full&mime_types=jpg,png,gif&limit=1&format=json&api_key=live_vAEviySb7LZuhoIqqFjs8QkVaaNXd2HWd8XobXB2hyjEXHgUQH4xAxDBhChWVzBZ";
        String avatarUrl = "https://media.discordapp.net/attachments/472874732019122186/1092156158111908001/Zolomon_caracal_studio_ghibli_anime_detailed_fine_lines_natural_a77bedcd-a425-4987-b2ef-f07d83f68949.png?width=658&height=658";
        String username = "Benson";
        JSONObject payload = new JSONObject();
        JSONObject headers = new JSONObject();
        JSONObject mensagem = new JSONObject();
        URL url = new URL(dogApiUrl);
        String imageUrl = new String();
        payload = DiscordWebhook.setContentAvatar(payload, headers, avatarUrl, username);
        while (true) {
            try {
                String urlImagemResponse = sendRequestApiDog(payload, imageUrl, url);
                mensagem.put("content", urlImagemResponse);
                DiscordWebhook.sendPostWebHook(webhookUrl, mensagem.toString(), headers.toString());
                Thread.sleep(1800000); // Espera 30 minutos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String sendRequestApiDog(JSONObject payload, String imageUrl, URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            Gson gson = new Gson();
            CatApiResponse[] apiResponse = gson.fromJson(response.toString(), CatApiResponse[].class);
            imageUrl = apiResponse[0].getUrl();
            System.out.println("Image URL: " + imageUrl);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return imageUrl;
    }

}
