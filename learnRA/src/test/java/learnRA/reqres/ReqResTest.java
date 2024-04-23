package learnRA.reqres;


import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReqResTest 
{
	
	//given
	//when
	//then
	//@Test
	public void createUser()
	{
		RestAssured.given().log().all().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}").contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/users")
		.then().assertThat().statusCode(201).log().all();
		
		
		
		
	}
	
	//@Test
	public void getUser()
	{
		RestAssured.given()
		.when().get("https://reqres.in/api/users/2")
		.then().assertThat().statusCode(200).log().all();
		
	}
	
	//@Test
	public void getListOfUsers()
	{
		RestAssured.given().log().all()
		.when().get("https://reqres.in/api/users?page=2")
		.then().assertThat().statusCode(200).log().all();
		
	}
	//@Test
	public void UpdateUser()
	{
		RestAssured.given().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"zion resident\"\r\n"
				+ "}")
		.when().put("https://reqres.in/api/users/2")
		.then().statusCode(200).log().all();
	}
	
	//@Test
	public void UpdateUserWithPatch()
	{
		RestAssured.given().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"zion resident\"\r\n"
				+ "}")
		.when().patch("https://reqres.in/api/users/2")
		.then().statusCode(200).log().all();
	}
	
	//@Test
	public void deleteUser()
	{
		RestAssured.given()
		.when().delete("https://reqres.in/api/users/2")
		.then().statusCode(204).log().all();
	}
	
	//@Test
	public void registerUser()
	{
		RestAssured.given().log().all().body("{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
				+ "    \"password\": \"pistol\"\r\n"
				+ "}").contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/register")
		.then().assertThat().statusCode(200).log().all();
		
	}
	//@Test
	public void registerUserUnSuccess()
	{
		RestAssured.given().body("{\r\n"
				+ "    \"email\": \"sydney@fife\"\r\n"
				+ "}").contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/register")
		.then().assertThat().statusCode(400).log().all();
	}
	
	//@Test
	public void loginUserSuccess()
	{
		RestAssured.given().body("{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
				+ "    \"password\": \"cityslicka\"\r\n"
				+ "}").contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/login")
		.then().assertThat().statusCode(200).log().all();
	}
	
	//@Test
	public void loginUserUnSuccess()
	{
		RestAssured.given().body("{\r\n"
				+ "    \"email\": \"peter@klaven\"\r\n"
				+ "}").contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/login")
		.then().assertThat().statusCode(400).log().all();
	}
	
	@Test
	public void delayedResponse()
	{
		RestAssured.given().when().get("https://reqres.in/api/users?delay=3").then().assertThat().statusCode(200).log().all();
	}
}
