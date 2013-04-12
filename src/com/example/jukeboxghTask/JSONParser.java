package com.example.jukeboxghTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	// STATUS TA
    private static int HTTP_STATUS_OK = 200;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	// Constructor
	public JSONParser(){
		
	}
	
	// Function get json from url
	// by making http post or get request
	public JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params) {
		// Making HTTP request
		try {
			// Check request method
			if(method.equals("POST")) {
				// request method is post
				// default http client
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				// Log.i("response", httpResponse.toString());
				
				// Status of response
				StatusLine status = httpResponse.getStatusLine();
				if (status.getStatusCode() != HTTP_STATUS_OK) {
					Log.e("Invalid response","Invalid response from url");
				}
				
				HttpEntity httpEntity = httpResponse.getEntity();				
				is = httpEntity.getContent();
				
			} else if(method.equals("GET")) {
				// request method is get
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();				
				is = httpEntity.getContent();				
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			//Log.i("Json: ", sb.toString());
			json = sb.toString();
			Log.i("JSON String", sb.toString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.e("Unsupported Encoding", "Encoding exception");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("ReadLine Exception(JsonParser):", "ReadLineException met");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("JSON Parser", "Error parsing data: " + e.toString());
		}
		
		return jObj;
		
	}
}
