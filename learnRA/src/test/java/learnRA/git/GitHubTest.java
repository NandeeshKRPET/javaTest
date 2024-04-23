package learnRA.git;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class GitHubTest 
{
	String baseURL = "https://api.github.com";
	String token = "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";
	String ownerName = "NandeeshKRPET";
	//@Test
	public void createRepo()
	{
		HashMap repo = new HashMap();
		repo.put("name", "NKS");
		repo.put("description", "Nandeesh RA Test");
		repo.put("private", false);
		
		RestAssured.given().body(repo).contentType(ContentType.JSON)
		.header("Authorization","Bearer "+token)
		.when().post(baseURL+"/user/repos")
		.then().assertThat().statusCode(201)
		.body("name", equalTo("NKS"))
		.body("description", equalTo("Nandeesh RA Test"))
		.header("Server", "GitHub.com")
		.log().all();
	}
	
	
	@Test
	public void getRepo()
	{

		RestAssured.given()
		.header("Authorization","Bearer "+token)
		.when().get(baseURL+"/repos/"+ownerName+"/"+"NKS")
		.then().assertThat().statusCode(200)
		.log().all();
		
		
	}

}
