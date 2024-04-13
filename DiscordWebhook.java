import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscordWebhook {

	public static void main(String[] args) throws JSONException {
	}
    
	public static JSONObject setContentAvatar(JSONObject payload, JSONObject headers, String avatarUrl, String username) throws JSONException {
		payload.put("avatar_url", avatarUrl);
		payload.put("username", username);
		headers.put("Content-Type", "application/json");
		return payload;
	}

	public static void sendPostWebHook(String webhookUrl, String payload, String headers) {
		try {
			URL url = new URL(webhookUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);

			OutputStream os = con.getOutputStream();
			byte[] input = payload.getBytes(StandardCharsets.UTF_8);
			os.write(input, 0, input.length);

			if (con.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
				System.out.println("Message sent successfully!");
			} else {
				System.out.println("Error sending message: " + con.getResponseMessage());
			}
			
			os.flush();
			os.close();
			con.disconnect();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}