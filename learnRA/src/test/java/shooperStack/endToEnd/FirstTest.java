package shooperStack.endToEnd;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import shooperStack.endToEnd.pojo.Address;
import shooperStack.endToEnd.pojo.Item;
import shooperStack.endToEnd.pojo.Login;

public class FirstTest 
{
	String baseURL = "https://www.shoppersstack.com/shopping";
	int shopperId;
	int productId;
	String token;
	int itemId;
	int addressId;
	
	@Test(priority = 1)
	public void login()
	{
		Login lb = new Login();
		lb.setEmail("nandi.kshankar@gmail.com");
		lb.setPassword("Nandi@123");
		lb.setRole("SHOPPER");
		
		Response loginResp = RestAssured.given()
					.body(lb)
					.contentType(ContentType.JSON)
					.when().post(baseURL+"/users/login");
		
		shopperId = loginResp.jsonPath().get("data.userId");
		token = loginResp.jsonPath().get("data.jwtToken");
		Assert.assertEquals(loginResp.getStatusCode(), 200);
		System.out.println("ShopperId = "+shopperId);
		System.out.println("Bearer Token = "+token);
	}


	@Test(priority = 2)
	public void viewAllProducts()
	{
		Response productsResp = RestAssured.given().when().get(baseURL+"/products/alpha");
		productId  = productsResp.jsonPath().get("data[0].productId");
		Assert.assertEquals(productsResp.getStatusCode(), 200);
		System.out.println("Product Id = "+productId);
	}
	
	@Test(priority = 3)
	public void addProductToCart()
	{
		Item item = new Item();
		item.setProductId(productId);
		item.setQuantity(2);
		
		Response resp = RestAssured.given().body(item).contentType(ContentType.JSON).log().all()
				.pathParam("shopperId", shopperId)
				.header("Authorization","Bearer "+token)
				.when().post(baseURL+"/shoppers/{shopperId}/carts");
		
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		itemId = resp.jsonPath().get("data.itemId");
		
		System.out.println("ItemId Id = "+itemId);
	}
	
	@Test (priority = 4)
	public void addAddress()
	{
		//HashMap ab = new HashMap();
		
		Address ab = new Address();
		ab.setAddressId(0);
		ab.setBuildingInfo("Annnapurneshwari nilaya");
		ab.setCity("Bengaluru");
		ab.setCountry("India");
		ab.setName("Nandi");
		ab.setPhone(987654345698l);
		ab.setPincode(560010);
		ab.setState("Karnataka");
		ab.setStreetInfo("2nd Street");
		ab.setType("Home");
		
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

	@Test(priority = 4)
	public void getAllAddress()
	{
		Response resp = RestAssured.given().log().all()
				.pathParam("shopperId", shopperId)
				.header("Authorization","Bearer "+token)
				.when()
				.post(baseURL+"/shoppers/{shopperId}/address");
		
		int statusCode  = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		addressId = resp.jsonPath().get("data.addressId");		
	
		System.out.println(addressId);
		
	}
	
}
