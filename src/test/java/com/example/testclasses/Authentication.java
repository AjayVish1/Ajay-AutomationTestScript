package com.example.testclasses;

import com.example.ApplicationConfigration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Authentication extends BaseClass {

    public String emailAddress;

    @Test
    public void createUser() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        emailAddress = "fake+" + number + "d@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        Response response = createUserWith(body);

        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject createdUser = data.getJSONObject("created_user");
        String first_name = createdUser.getString("first_name");
        String last_name = createdUser.getString("last_name");
        String email = createdUser.getString("email");
        String userId = createdUser.getString("id");

        // Creating Hamcrest assertions
        assertThat("Status code is not 201", response.getStatusCode(), equalTo(HttpStatus.SC_CREATED));

        assertThat(status, equalTo(true));
        assertThat(message, equalTo("User signup successful."));
        assertThat(firstName, equalTo(first_name));
        assertThat(lastName, equalTo(last_name));
//        assertThat(emailAddress, equalTo(email));
        assertThat(userId, notNullValue());
    }

    @Test
    public void createUserWithInvalidEmail() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        emailAddress = "fake+" + number + "@example";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"email\" must be a valid email"));
    }

    @Test
    public void createUserWithSpaceInFirstAndLastName() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = " ";
        String lastName = " ";
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("First name is required, Last name is required"));
    }

    @Test
    public void createUserWithEmojiInFirstAndLastName() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = "\uD83D\uDE0A";
        String lastName = "\uD83D\uDE0A";
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject createdUser = data.getJSONObject("created_user");
        String first_name = createdUser.getString("first_name");
        String last_name = createdUser.getString("last_name");
        String userId = createdUser.getString("id");

        // Creating Hamcrest assertions
        assertThat("Status code is not 201", response.getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("User signup successful."));
        assertThat(firstName, equalTo(first_name));
        assertThat(lastName, equalTo(last_name));
        // assertThat(emailAddress, equalTo(email));
        assertThat(userId, notNullValue());
    }


    @Test
    public void createUserWithSpacialCharacterInFirstAndLastName() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = "#$#";
        String lastName = "%[]";
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject createdUser = data.getJSONObject("created_user");
        String first_name = createdUser.getString("first_name");
        String last_name = createdUser.getString("last_name");
        String userId = createdUser.getString("id");

        // Creating Hamcrest assertions
        assertThat("Status code is not 201", response.getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("User signup successful."));
        assertThat(firstName, equalTo(first_name));
        assertThat(lastName, equalTo(last_name));
//        assertThat(emailAddress, equalTo(email));
        assertThat(userId, notNullValue());
    }


    @Test
    public void createUserWithoutFirstName() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String lastName = faker.name().lastName();
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"first_name\" is required"));
    }

    @Test
    public void createUserWithoutLastName() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = faker.name().firstName();
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"last_name\" is required"));
    }

    @Test
    public void createUserWithoutFirstNameAndLastName() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"first_name\" is required, \"last_name\" is required"));
    }

    @Test
    public void createUserWithoutEmail() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"password\": \"test#123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"email\" is required"));
    }

    @Test
    public void createUserWithoutPassword() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        emailAddress = "fake+" + number + "@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = createUserWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"password\" is required"));
    }

    @Test
    public void LoginWithValidDetails() {
        String body = "{ \"email\": \"ajayv.exactink@gmail.com\", \"password\": \"test#123\" }";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = loginWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject userData = data.getJSONObject("user");
        String userId = userData.getString("_id");
        String first_name = userData.getString("first_name");
        String last_name = userData.getString("last_name");
        String email = userData.getString("email");

        JSONObject tokens = data.getJSONObject("tokens");
        JSONObject access = tokens.getJSONObject("access");
        String accessToken = access.getString("token");

        // Creating Hamcrest assertions
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(userId, notNullValue());
        assertThat(first_name, equalTo("Ajay"));
        assertThat(last_name, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
        assertThat(email, equalTo("ajayv.exactink@gmail.com"));
        assertThat(tokens, notNullValue());
    }


    @Test
    public void LoginWithInvalidEmail() {
        String body = "{ \"email\": \"random.exactink+00@gmail.com\", \"password\": \"test#123\" }";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = loginWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("Incorrect email"));
    }

    @Test
    public void LoginWithInvalidPassword() {
        String body = "{ \"email\": \"ajayv.exactink@gmail.com\", \"password\": \"test@123\" }";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = loginWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("Incorrect password"));
    }

    @Test
    public void LoginWithoutPassword() {
        String body = "{ \"email\": \"random.exactink+00@gmail.com\"}";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = loginWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"password\" is required"));
    }

    @Test
    public void LoginWithoutEmail() {
        String body = "{ \"password\": \"test#123\"}";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = loginWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"email\" is required"));
    }

    @Test
    public void sendOtpVerification() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = "#$#";
        String lastName = "%[]";
        emailAddress = "ajayv.exactinkk+" + number + "@gmail.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";
        String OtpBody = "{ \"email\": \"" + emailAddress + "\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        createUserWith(body);
        Response response = sendOtpVerificationWith(OtpBody);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("Verification code successfully sent to your email."));
    }

    @Test
    public void sendOtpVerificationWithInvalidEmail() {
        String body = "{ \"email\": \"ajayv.exactink+12@gmail.com\"}";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = sendOtpVerificationWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 404", response.getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
        assertThat(message, equalTo("No users found with this email"));
    }

    @Test
    public void sendOtpVerificationWithEmptyBody() {
        String body = "{}";
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        Response response = sendOtpVerificationWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        int statusCode = jsonObject.getInt("code");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"email\" is required"));
    }

    @Test
    public void resetPasswordWithValidDetails() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = "#$#";
        String lastName = "%[]";
        emailAddress = "ajayv.exactinkk+" + number + "@gmail.com";
        String createUserBody = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";
        String body = "{ \"email\": \"" + emailAddress + "\", \"old_password\": \"test#123\", \"password\": \"test@123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        createUserWith(createUserBody);
        Response response = resetPasswordWith(body);
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("Password successfully changed."));
    }

    @Test
    public void resetPasswordWithoutOldPassword() {
        Faker faker = new Faker();
        String number = faker.number().digits(4);
        String firstName = "#$#";
        String lastName = "%[]";
        emailAddress = "ajayv.exactinkk+" + number + "@gmail.com";
        String createUserBody = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + emailAddress + "\",\"password\": \"test#123\"}";
        String body = "{ \"email\": \"" + emailAddress + "\", \"password\": \"test@123\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();

        createUserWith(createUserBody);
        Response response = resetPasswordWith(body);

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    private Response resetPasswordWith(String body) {
        Response response = given().contentType(ContentType.JSON).body(body).when().post("/auth/reset-password").then().extract().response();
        return response;
    }

    private Response createUserWith(String body) {
        Response response = given().contentType(ContentType.JSON).body(body).when().post("/auth/register").then().extract().response();
        return response;
    }

    private Response sendOtpVerificationWith(String body) {
        Response response = given().contentType(ContentType.JSON).body(body).when().post("/auth/send-otpverification-email").then().extract().response();
        return response;
    }

    private Response loginWith(String body) {
        Response response = given().contentType(ContentType.JSON).body(body).when().post("/auth/login").then().extract().response();
        return response;
    }
}
