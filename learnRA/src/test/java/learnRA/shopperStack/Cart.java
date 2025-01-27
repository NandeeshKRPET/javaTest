package learnRA.shopperStack;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Cart {

	
	String baseURL = "https://www.shoppersstack.com/shopping";
	int shopperId;
	String token;
	int productId = 5;
	int itemId;

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
	public void addProductToCart()
	{
		HashMap product = new HashMap();
		product.put("productId", 7);
		product.put("quantity", 1);
		
		Response resp = RestAssured.given().body(product).contentType(ContentType.JSON).log().all()
				.pathParam("shopperId", shopperId)
				.header("Authorization","Bearer "+token)
				.when().post(baseURL+"/shoppers/{shopperId}/carts");
		
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		itemId = resp.jsonPath().get("data.itemId");
	}
	
	
	@Test(priority = 3)
	public void getAllProducts()
	{
		Response resp = RestAssured
				         .given()
				         .pathParam("shopperId", shopperId)
				         .header("Authorization","Bearer "+token)
								   .when().get(baseURL+"/shoppers/{shopperId}/carts");
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		productId = resp.jsonPath().get("data[0].productId");
		System.out.println("Product Id : "+productId);
	}
	
	
	
	//@Test
	public void updateProductInCart()
	{
		HashMap product = new HashMap();
		product.put("productId", productId);
		product.put("quantity", 0);
		
		Response resp = RestAssured.given().body(product).contentType(ContentType.JSON)
				.header("Authorization","Bearer "+token)
				.pathParam("shopperId", shopperId)
				.when().post(baseURL+"/shoppers/{"+shopperId+"}/wishlist");
		
		int statusCode = resp.getStatusCode();
		Assert.assertEquals(statusCode, 201);
	}
	
	
	@Test(priority = 4)
	public void deleteProductFromCart()
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
