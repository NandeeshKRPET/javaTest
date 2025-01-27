package learn.ra.petStore;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Pet 
{
	
	long petId;
	
	//@Test
	public void createPet()
	{
		HashMap data = new HashMap();
		data.put("name", "tiger");
		
		HashMap<String, String> category = new HashMap<String, String>();
		category.put("id", "0");
		category.put("name", "dog");
		
		
		data.put("category", category);
		
		
		RestAssured.given().log().all().body(data).contentType(ContentType.JSON)
		.when().post("https://petstore.swagger.io/v2/pet")
		.then().assertThat().statusCode(200).log().all();
		
	}
	
	//@Test 
	public void createPetAndCaptureId()
	{
		HashMap data = new HashMap();
		data.put("name", "tiger");
		
		HashMap<String, String> category = new HashMap<String, String>();
		category.put("id", "0");
		category.put("name", "dog");
	
		data.put("category", category);	
		
		String body = RestAssured.given().log().all().body(data).contentType(ContentType.JSON).
				
				                  when().post("https://petstore.swagger.io/v2/pet")
				                  .then().assertThat().statusCode(200).log().all().extract().body().asString();
	
		
		JsonPath respData = new JsonPath(body);
		long petId  = respData.get("id");
		System.out.println(petId);
		
	}
	
	@Test(priority = 1)
	public void createPetAndCaptureIdFromResponse()
	{
		HashMap data = new HashMap();
		data.put("name", "tiger");
		
		HashMap<String, String> category = new HashMap<String, String>();
		category.put("id", "0");
		category.put("name", "dog");
	
		data.put("category", category);	
		
		Response res = RestAssured.given().body(data).contentType(ContentType.JSON)
		.when().post("https://petstore.swagger.io/v2/pet");
	
		int statusCode = res.getStatusCode();
		String contentType = res.getContentType();
		petId = res.jsonPath().get("id");
	
		System.out.println(statusCode);
		System.out.println(contentType);
		System.out.println(petId);
		
	}
	
	//@Test(priority = 2)
	public void getPet()
	{
		Response res = RestAssured.given()
		.when().get("https://petstore.swagger.io/v2/pet/"+petId);
	
		int statusCode = res.getStatusCode();
		String contentType = res.getContentType();
		
		Assert.assertEquals(statusCode, 200);
		
		Assert.assertEquals(res.getHeader("content-type") ,"application/json");
		
		long actualPetId = res.jsonPath().get("id");
		
		Assert.assertEquals(actualPetId, petId);
		
		Assert.assertEquals(res.header("Server"), "Jetty(9.2.9.v20150224)");
		
	}

@Test(priority = 2)
public void updatePet()
{
	
	HashMap data = new HashMap();
	data.put("id", petId);
	data.put("name", "tiger123");
	
	HashMap category = new HashMap();
	
	category.put("name", "dog");
	data.put("category", category);	
	
	Response res = RestAssured.given().body(data).contentType(ContentType.JSON)
	.when().put("https://petstore.swagger.io/v2/pet");

	int statusCode = res.getStatusCode();
	String contentType = res.getContentType();

	Assert.assertEquals(statusCode, 200);
	
}

//@Test(priority = 2)
public void deletePet()
{	
	Response res = RestAssured.given()
	.when().put("https://petstore.swagger.io/v2/pet"+petId);

	int statusCode = res.getStatusCode();
	String contentType = res.getContentType();

	Assert.assertEquals(statusCode, 400);
	
}
}





/*

	
Pet object that needs to be added to the store

Example Value
Model
{
  "category": {
    "id": 0,
    "name": "string"
  },
  "name": "doggie",
 
}

*/