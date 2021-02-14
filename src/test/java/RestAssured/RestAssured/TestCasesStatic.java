package RestAssured.RestAssured;

import org.junit.Assert;
import org.junit.Test;


import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class TestCasesStatic {
	
	@Test
	public void getAPI() {
		baseURI= "https://reqres.in";
		
		Response resp=given().when().get("/api/users?page=2").then().extract().response();
		
		int statusCode=resp.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		String statusLine=resp.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
		
		String firstName=resp.body().path("data[0].email");
		Assert.assertEquals(firstName, "michael.lawson@reqres.in");
		
		ArrayList<String> firstNames= new ArrayList<String>();
		firstNames=resp.getBody().path("data.email");
		Assert.assertTrue(firstNames.contains("michael.lawson@reqres.in"));
		
		String header1=resp.header("Content-Type");
		Assert.assertEquals(header1 ,"application/json; charset=utf-8");
	}

}
