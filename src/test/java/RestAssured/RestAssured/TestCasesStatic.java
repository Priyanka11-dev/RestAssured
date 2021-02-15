package RestAssured.RestAssured;

import org.json.simple.JSONObject;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class TestCasesStatic {
	public static String BaseURIVar;
	
	@BeforeClass
	public static void setup() throws IOException {
		FileInputStream ConfigFile=new FileInputStream("./Config.properties");
		Properties configProp= new Properties();
		configProp.load(ConfigFile);
		BaseURIVar=configProp.getProperty("BaseURI");
		
	}
	
	@Test
	public void getAPI() {
		baseURI= BaseURIVar;
		
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
	
	@Test
	public void postAPI() {
		baseURI=BaseURIVar;
		
		JSONObject req= new JSONObject();
		req.put("name", "Oracle");
		req.put("job", "Foreseeing");
		
		Response resp1=given().body(req.toJSONString()).when().post("/api/users").then().extract().response();
		
		int statusCode= resp1.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		String id=resp1.getBody().path("id");
		System.out.println(id);
	}
	
	@Test
	public void putAPI() {
		baseURI=BaseURIVar;
		JSONObject reqput= new JSONObject();
		reqput.put("name", "Oracle");
		reqput.put("job", "Foreseeing");
		
		Response respput=given().body(reqput.toJSONString()).when().put("/api/users/2").then().extract().response();
		
		int StatusCode=respput.getStatusCode();
		Assert.assertEquals(StatusCode, 200);
		
		String name=respput.getBody().path("name");
		Assert.assertEquals(name, "Oracle");
		
		String job=respput.getBody().path("job");
		Assert.assertEquals(job, "Foreseeing");
		
		
	}
	
	@Test 
	public void patchAPI() {
		baseURI=BaseURIVar;
		JSONObject reqput= new JSONObject();
		reqput.put("name", "Oracle");
		reqput.put("job", "Foreseeing");
		
		Response respput=given().body(reqput.toJSONString()).when().patch("/api/users/2").then().extract().response();
		
		int StatusCode=respput.getStatusCode();
		Assert.assertEquals(StatusCode, 200);
	}

	@Test
	public void deleteAPI() {
		baseURI=BaseURIVar;
		
		given().when().delete("/api/users/2").then().assertThat().statusCode(204);
	}
	
	@Test
	public void authorizationAPI() {
		baseURI=BaseURIVar;
		Response respa=given().auth().basic("eve.holt@reqres.in", "cityslicka").when().get("/api/login").then().extract().response();
		
		Assert.assertEquals(respa.getStatusCode(), 200);
		
		System.out.println(respa.getBody().path("token"));
	}
}
