package quickstart.devicemanagment;

import cloud.artik.client.ApiClient;
import cloud.artik.client.Configuration;
import cloud.artik.client.auth.OAuth;

public class ClientHelper {
	

	
	public static ApiClient initClient(ApiClient artikCloud, String accessToken) {

		artikCloud = Configuration.getDefaultApiClient();
		OAuth artikcloud_oauth = (OAuth) artikCloud.getAuthentication("artikcloud_oauth");

		// set access token for client
		artikcloud_oauth.setAccessToken(accessToken);
		
		return artikCloud;
		
	}
}
