package site.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
public class Search {

	public static void main(String[] args) throws Exception {
		
		//search string is 'spring mvc' here
		String payload = "{" +
                "\"query\": \"spring mvc\", " +
                "\"responseType\": \"JsonObject\", " +
                "\"template\": \"MainSite Template\"" +
                "}";
		//Get the below Ids from Cludo Dashboard after login
		String strCustomerId="xxxx";
		String strEngineId="xxxx";
		String strSearchKey="xxxx";
		String url = "https://api.cludo.com/api/v3/"+strCustomerId+"/"+strEngineId+"/search";
		//Generate Base64 code
		String strKeys=strCustomerId+":"+strEngineId+":"+strSearchKey;
		String strBase64SiteKey="SiteKey "+Base64.getEncoder().encodeToString(strKeys.getBytes());
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestProperty("Authorization", strBase64SiteKey);
		con.setRequestProperty("Content-Type","application/json");
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		byte[] out=payload.getBytes("UTF-8");
		OutputStream os=con.getOutputStream();
		os.write(out);
		os.close();
		BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine=null;
		JSONObject myresponse=null;
		while((inputLine=in.readLine())!=null){
			myresponse=new JSONObject(inputLine);
		}
		in.close();
		JSONArray searchArray=(JSONArray) myresponse.get("TypedDocuments");
		JSONObject map=(JSONObject) searchArray.get(0);
		
		//You can iterate on JSONArray object to get the list of search results
		System.out.println(map.get("Score"));
		System.out.println(map.get("ResultIndex"));
		System.out.println(myresponse.get("TotalDocument"));
	}
}
