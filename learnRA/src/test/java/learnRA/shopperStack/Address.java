package learnRA.shopperStack;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Address 
{
	String baseURL = "https://www.shoppersstack.com/shopping";
	int shopperId;
	String token;
	int addressId;
	
	@Test(priority = 1)
	public void login()
	{
		HashMap lb = new HashMap();
		lb.put("email","nandi.kshankar@gmail.com" );
		lb.put("password", "Nandi@123");
		lb.put("role", "SHOPPER");
		
		
		Response resp = RestAssured.given().body(lb).contentType(ContentType.JSON)
		.when().post(baseURL+"/users/login");
		
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		shopperId = resp.jsonPath().get("data.userId");
		token = resp.jsonPath().get("data.jwtToken");
		
		System.out.println("shopper Id  : "+shopperId);
		System.out.println("Token : "+token);
		
		Assert.assertEquals(resp.header("Server"), "nginx");
	}
	
	@Test (priority = 2)
	public void addAddress()
	{
		Address ab = new Address();
		
		
		
		Response resp = RestAssured.given().body(ab).contentType(ContentType.JSON).log().all()
				.pathParam("shopperId", shopperId)
				.header("Authorization","Bearer "+token)
				.when()
				.post(baseURL+"/shoppers/{shopperId}/address");
		
		int statusCode  = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		addressId = resp.jsonPath().get("data.addressId");
		System.out.println("AddressId = "+addressId);
			
	}
	
	//@Test(priority = 3)
	public void getAllAddress()
	{

		Response resp = RestAssured.given().log().all()
				.pathParam("shopperId", shopperId)
				.header("Authorization","Bearer "+token)
				.when()
				.post(baseURL+"/shoppers/{shopperId}/address");
		
		int statusCode  = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		
		int actualAddressId = resp.jsonPath().get("data.addressId");
		Assert.assertEquals(actualAddressId, addressId);
		
		
		
	}
	
	
	
	
	

}







///shoppers/{shopperId}/address

/*
 * {
  "addressId": 0,
  "buildingInfo": "string",
  "city": "string",
  "country": "string",
  "landmark": "string",
  "name": "string",
  "phone": "string",
  "pincode": "string",
  "state": "string",
  "streetInfo": "string",
  "type": "string"
}
 */





