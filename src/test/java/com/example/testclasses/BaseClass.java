package com.example.testclasses;

import com.example.ApplicationConfigration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaseClass {
    public String accessToken;
    public String userId;
    public String UserName = ApplicationConfigration.INSTANCE.getUsername();
    public String password = ApplicationConfigration.INSTANCE.getPassword();

    @BeforeTest
    @Test
    public void testUserLogin() {
        System.out.println("Test Excution started :- ");
        String body = "{ \"email\": \"" + UserName + "\", \"password\": \"" + password + "\" }";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = given().contentType(ContentType.JSON).body(body).when().post("/auth/login").then().statusCode(200).body("message", equalTo("Login successful.")).extract().response();

        JSONObject jsonObject = new JSONObject(response.asString());

        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject userData = data.getJSONObject("user");
        userId = userData.getString("_id");
        String first_name = userData.getString("first_name");
        String last_name = userData.getString("last_name");
        String email = userData.getString("email");

        JSONObject tokens = data.getJSONObject("tokens");
        JSONObject access = tokens.getJSONObject("access");
        accessToken = access.getString("token");

        // Creating Hamcrest assertions
        assertThat(userId, notNullValue());
        assertThat(first_name, equalTo("Ajay"));
        assertThat(last_name, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
        assertThat(email, equalTo("ajayv.exactink@gmail.com"));
        assertThat(tokens, notNullValue());
    }

    @AfterTest
    public void afterAllClass() {
        System.out.println("Test Excution ended :");
    }
}
