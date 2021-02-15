package RestAssured.RestAssured;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestCasesNonStatic {
	@Test
	public void getNonStatic() {
		RestAssured.baseURI="https://reqres.in";
		
		Response resp=RestAssured.get("/api/users?page=2");
		Assert.assertEquals(resp.getStatusCode(), 200);
	}
	
	@Test
	public void postNonStatic() {
		RestAssured.baseURI="https://reqres.in";
		
		JSONObject reqput= new JSONObject();
		reqput.put("name", "Oracle");
		reqput.put("job", "Foreseeing");
		
		 RequestSpecification req = RestAssured.given();
		 req.body(reqput);
		 Response resp=req.post("/api/users");
		 
		 //Assert.assertEquals(resp.getStatusCode(),201);
	}
}
