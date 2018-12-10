package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{

	TestBase testBase;
	String apiUrl;
	String serviceUrl;
	String Url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp(){
		testBase = new TestBase();
		
		serviceUrl = prop.getProperty("url");
		apiUrl = prop.getProperty("serviceURL");
		
		Url = serviceUrl + apiUrl;
	}
	
/*	@Test
	public void getAPITest(){
		restClient = new RestClient();
		restClient.get(Url);
	}*/
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(Url);

		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is nto 200!");

			
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJsonObj = new JSONObject(responseString);
		
		//per page
		String perPageValue = TestUtil.getValueByJPath(responseJsonObj, "/per_page");
		System.out.println("per page value => " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		// total attribute value
		String totalValue = TestUtil.getValueByJPath(responseJsonObj, "/total");
		System.out.println("total value => " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		// get the value from JSON Array
		String lastName = TestUtil.getValueByJPath(responseJsonObj, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJsonObj, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJsonObj, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJsonObj, "/data[0]/first_name");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		
			
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
			
		for(Header header : headerArray){
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Array => " + allHeaders);
			
	}
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		closeableHttpResponse = restClient.get(Url, headerMap);

		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is nto 200!");

			
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJsonObj = new JSONObject(responseString);
		
		//per page
		String perPageValue = TestUtil.getValueByJPath(responseJsonObj, "/per_page");
		System.out.println("per page value => " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		// total attribute value
		String totalValue = TestUtil.getValueByJPath(responseJsonObj, "/total");
		System.out.println("total value => " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
			
		for(Header header : headerArray){
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Array => " + allHeaders);
			
	}
	
	
	
	
}
