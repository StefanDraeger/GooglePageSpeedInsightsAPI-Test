package gpsitest;

import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;

public class GooglePageSpeedInsightsClient {

	private static final String API_URL = "https://www.googleapis.com/pagespeedonline/v5/runPagespeed";

	private String apiKey;
	private String username;

	private String url;

	private boolean useProxy = false;
	private String ipAddress;
	private int portNumber;

	private boolean useAuthentication = false;
	private String authUsername;
	private String authPassword;

	private Map<String, String> parameters;
	
	public GooglePageSpeedInsightsClient(Builder builder) {
		this.apiKey = builder.apiKey;
		this.url = builder.url;

		this.useProxy = builder.useProxy;
		this.ipAddress = builder.ipAddress;
		this.portNumber = builder.portNumber;

		this.useAuthentication = builder.useAuthentication;
		this.authUsername = builder.authUsername;
		this.authPassword = builder.authPassword;
		
		this.parameters = builder.parameters;
	}

	public JsonNode execute() throws UnirestException {
		if (useProxy) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(ipAddress, portNumber),
					new UsernamePasswordCredentials(authUsername, authPassword));

			HttpHost httpHost = new HttpHost(ipAddress, portNumber);

			Unirest.setProxy(httpHost);
		}
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(API_URL);
		urlBuilder.append("?");
		urlBuilder.append("url=");
		urlBuilder.append(url);
		urlBuilder.append("&");
		urlBuilder.append("key=");
		urlBuilder.append(apiKey);
		

		
		if(parameters != null) {
			parameters.keySet().stream().forEach(entry ->{
				urlBuilder.append("&");
				urlBuilder.append(entry);
				urlBuilder.append("=");
				urlBuilder.append(parameters.get(entry));	
			});
				
		}
		return Unirest.get(urlBuilder.toString()).asJson().getBody(); 
				
	}

	public static class Builder {

		private String apiKey = "-undefined-";

		private String url = "https://www.googleapis.com/pagespeedonline/v5/runPagespeed";

		private boolean useProxy = false;
		private String ipAddress;
		private int portNumber;

		private boolean useAuthentication = false;
		private String authUsername;
		private String authPassword;

		private Map<String, String> parameters;

		public GooglePageSpeedInsightsClient build() {
			return new GooglePageSpeedInsightsClient(this);
		}

		public Builder apiKey(String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder proxy(String ipAddress, int portNumber) {
			this.useProxy = true;
			this.ipAddress = ipAddress;
			this.portNumber = portNumber;
			return this;
		}

		public Builder authentication(String username, String password) {
			useAuthentication = true;
			this.authUsername = username;
			this.authPassword = password;
			return this;
		}

		public Builder parameter(Map<String, String> parameter) {
			this.parameters = parameter;
			return this;
		}

	}
}
