package com.example.testclasses;

import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.example.model.Root;
import org.example.model.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GetApi extends BaseClass {
    public int userID;
    public int number;


    @Test
    public void testCreateUser() throws IOException {

        Faker faker = new Faker();
        number = faker.random().hex(6).hashCode();
        System.out.println("randomNumber :-" + number);

        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON string into a JsonNode
        UserData user = objectMapper.readValue(new File("src/main/resources/requestData.json"), UserData.class);

        user.setName("Test User");
        user.setEmail("testUser" + number + "@gmail.com");

        // Send POST request to create a user
        Root response = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + accessToken).body(user).when().post("users").then().statusCode(HttpStatus.SC_OK).extract().response().as(Root.class);

        userID = response.data.getId();
        System.out.println("userEmail :-" + response.data.getEmail());
    }

    @Test
    public void getUserDetails() {
        Response response = given().header("Authorization", "Bearer " + accessToken).when().get("users/" + 5970250);

        System.out.println("getUserData :- " + response.asString());
    }

    @Test
    public void demoTest() {

        System.out.println("Test Works Correctly");
    }
}
