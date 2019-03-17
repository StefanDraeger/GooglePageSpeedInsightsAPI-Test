package gpsitest;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

class GooglePageSpeedInsightsClientTest {

	private static final String API_KEY = "apikey";
	
	@Test
	void generateReport() throws UnirestException {
		GooglePageSpeedInsightsClient.Builder builder = new GooglePageSpeedInsightsClient.Builder();
		builder.apiKey(API_KEY);
		
		Map<String, String> parameter = new HashMap<>();
		parameter.put("locale", "de_DE");
		builder.parameter(parameter);
		
		builder.url("https://draeger-it.blog");
		
		GooglePageSpeedInsightsClient client = builder.build();
		assertNotNull(client);
		
		JsonNode jsonRespond = client.execute();
		assertNotNull(jsonRespond);
		
		System.out.println(jsonRespond.toString());		
	}

}
