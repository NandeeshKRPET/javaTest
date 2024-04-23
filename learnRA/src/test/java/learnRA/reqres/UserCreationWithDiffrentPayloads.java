package learnRA.reqres;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class UserCreationWithDiffrentPayloads 
{
	//@Test
	public void createUserWithStringPayload()
	{
		RestAssured.given().log().all().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}").contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/users")
		.then().assertThat().statusCode(201).log().all();
	}
	
	
	//@Test
	public void createUserWithHashMapPayload()
	{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "morpheus");
		data.put("job", "leader");
		RestAssured.given().log().all().body(data).contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/users")
		.then().assertThat().statusCode(201).log().all();
	}
	
	//@Test
	public void createUserWithJsonObjectPayload()
	{
		JSONObject data = new JSONObject();
		data.put("name", "morpheus");
		data.put("job", "leader");
		RestAssured.given().log().all().body(data).contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/users")
		.then().assertThat().statusCode(201).log().all();
	}
	
	public void createUserWithPojoClassPayload()
	{
		UserBean data = new UserBean();
		
		
		data.setName("morpheus");
		data.setJob( "leader");
		RestAssured.given().log().all().body(data).contentType(ContentType.JSON)
		.when().post("https://reqres.in/api/users")
		.then().assertThat().statusCode(201).log().all();
	}
}
