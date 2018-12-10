package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {

	//1. GET Method
	public void getRequest(String url){
		CloseableHttpClient httpCient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse closeableHttpResponse = httpCient.execute(httpGet);
			int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
			System.out.println("Status Code: " + statusCode);
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			JSONObject responseJsonObj = new JSONObject(responseString);
			System.out.println("<-- Response JSON -->" + responseJsonObj);
			
			Header[] headerArray = closeableHttpResponse.getAllHeaders();
			HashMap<String, String> allHeaders = new HashMap<String, String>();
			
			for(Header header : headerArray){
				allHeaders.put(header.getName(), header.getValue());
			}
			System.out.println("Header Array => " + allHeaders);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	// GET Method without Headers:
	public CloseableHttpResponse get(String wsUrl) throws ClientProtocolException, IOException{
		CloseableHttpClient httpCient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(wsUrl);
		CloseableHttpResponse closeableHttpResponse = httpCient.execute(httpGet);
		
		return closeableHttpResponse;
	}
	
	//GET Method with Headers:
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpCient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		for(Map.Entry<String, String> entry : headerMap.entrySet()){
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse = httpCient.execute(httpGet);
		return closeableHttpResponse;
	}


	
}
