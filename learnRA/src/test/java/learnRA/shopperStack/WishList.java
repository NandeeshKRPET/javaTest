package learnRA.shopperStack;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class WishList
{
	String baseURL = "https://www.shoppersstack.com/shopping";
	int shopperId;
	String token;
	int productId;
	
//	@Test
	/*
	 * public void login() { HashMap lb = new HashMap();
	 * lb.put("email","nandi.kshankar@gmail.com" ); lb.put("password", "Nandi@123");
	 * lb.put("role", "SHOPPER");
	 * 
	 * 
	 * String body = RestAssured.given().log().all().body(lb)
	 * .contentType(ContentType.JSON) .when().post(baseURL+"/users/login")
	 * .then().assertThat().log().all() .statusCode(200).extract().asString();
	 * 
	 * System.out.println(body);
	 * 
	 * JsonPath respData = new JsonPath(body);
	 * 
	 * shopperId = respData.get("data.userId"); token =
	 * respData.get("data.jwtToken");
	 * 
	 * System.out.println(shopperId); System.out.println(token); }
	 */
	
	
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
	
	@Test(priority = 2)
	public void getAllProducts()
	{
		Response resp = RestAssured.given()
								   .when().get(baseURL+"/products/alpha");
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		productId = resp.jsonPath().get("data[0].productId");
		System.out.println("Product Id : "+productId);
	}
	

	@Test(priority = 3)
	public void addProductToWishList()
	{
		HashMap product = new HashMap();
		product.put("productId", productId);
		product.put("quantity", 0);
		
		Response resp = RestAssured.given().body(product).contentType(ContentType.JSON).log().all()
				.pathParam("shopperId", shopperId)
				.header("Authorization","Bearer "+token)
				.when().post(baseURL+"/shoppers/{shopperId}/wishlist");
		
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
	}
	
	
	//@Test
	public void updateProductWishList()
	{
		HashMap product = new HashMap();
		product.put("productId", productId);
		product.put("quantity", 0);
		
		Response resp = RestAssured.given().body(product).contentType(ContentType.JSON)
				.when().post(baseURL+"/shoppers/{"+shopperId+"}/wishlist");
		
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
	}
	
	
	@Test(priority = 4)
	public void deleteProductWishList()
	{
		
		 RestAssured
				.given()
				.pathParam("shopperId", shopperId)
				.pathParam("productId",productId)
				.header("Authorization","Bearer "+token)
				.when().delete(baseURL+"/shoppers/{shopperId}/wishlist/{productId}")
				.then()
				.assertThat().statusCode(204).log().all();
				
	}

}
