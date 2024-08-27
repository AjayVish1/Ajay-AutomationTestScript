package com.example.testclasses;

import com.example.ApplicationConfigration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserClass extends BaseClass {
    public String UserId;
    public String reference_id;

    @Test
    public void getAllUsers() {
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getUsersWith(accessToken);
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        JSONObject jsonObject = new JSONObject(response.asString());
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");

        // Creating Hamcrest assertions
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("User list."));
        assertThat(data, notNullValue());
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void getAllUsersWithInvalidToken() {
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getUsersWith("12" + accessToken);

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void getUserById() {
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getUserByIDWith(accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");
        String role = data.getString("role");
        boolean emailStatus = data.getBoolean("is_email_verified");
        String firstName = data.getString("first_name");
        String lastName = data.getString("last_name");
        String email = data.getString("email");

        // Creating Hamcrest assertions
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(status, equalTo(true));
        assertThat(firstName, equalTo("Ajay"));
        assertThat(lastName, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
        assertThat(email, equalTo("ajayv.exactink@gmail.com"));
        assertThat(emailStatus, equalTo(true));
        assertThat(message, equalTo("User detail."));
    }

    @Test
    public void getUserByInvalidId() {
        String userId = UUID.randomUUID().toString();
        System.out.println(userId);
        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getUserByIDWith(accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"\"user_id\"\" must be a valid mongo id"));
    }

    @Test
    public void getUserByInvalidToken() {

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getUserByIDWith("12" + accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void getUserByInvalidTokenAndInvalidId() {
        String userId = UUID.randomUUID().toString();

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getUserByIDWith("12" + accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void getFriendsByUserId() {

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getFriendsByUserIDWith(accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("Friends"));
    }


    @Test
    public void getFriendsByInvalidId() {
        String userId = UUID.randomUUID().toString();

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getFriendsByUserIDWith(accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"\"user_id\"\" must be a valid mongo id"));
    }

    @Test
    public void getFriendsByInvalidToken() {

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getFriendsByUserIDWith("12" + accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void getFriendsByInvalidTokenAndInvalidId() {
        String userId = UUID.randomUUID().toString();

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getFriendsByUserIDWith("12" + accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void getCatchesByUserId() {

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getCatchesByUserIDWith(accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        boolean status = jsonObject.getBoolean("status");
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(status, equalTo(true));
        assertThat(message, equalTo("Catch gallary"));
    }


    @Test
    public void getCatchesByInvalidId() {
        String userId = UUID.randomUUID().toString();

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getCatchesByUserIDWith(accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"\"user_id\"\" must be a valid mongo id"));
    }

    @Test
    public void getCatchesByInvalidToken() {

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getCatchesByUserIDWith("12" + accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void getCatchesByInvalidTokenAndInvalidId() {
        String userId = UUID.randomUUID().toString();

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = getCatchesByUserIDWith("12" + accessToken, userId);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void updateUserByUserId() {
        String emailAddress = "TestUser.exactink@gmail.com";

        try {
            String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Test\",\"last_name\": \"User\",\"email\": \"" + emailAddress + "\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";

            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");
            String email = data.getString("email");
            String address = data.getString("address");


            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Test"));
            assertThat(last_name, equalTo("User"));
            assertThat(email, equalTo(emailAddress));
            assertThat(address, equalTo("usa"));

        } finally {
            String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Ajay\",\"last_name\": \"Vish\uD83D\uDE42\uD83D\uDE42%\",\"email\": \"ajayv.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";
            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");
            String email = data.getString("email");
            String address = data.getString("address");


            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Ajay"));
            assertThat(last_name, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
            assertThat(email, equalTo("ajayv.exactink@gmail.com"));
            assertThat(address, equalTo("usa"));
        }
    }


    @Test
    public void updateUserByUserIdWithInvalidEmail() {
        String emailAddress = "TestUser.exactink@Example.com";

        try {
            String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Test\",\"last_name\": \"User\",\"email\": \"" + emailAddress + "\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";

            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");
            String email = data.getString("email");
            String address = data.getString("address");


            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Test"));
            assertThat(last_name, equalTo("User"));
            assertThat(email, equalTo(emailAddress));
            assertThat(address, equalTo("usa"));

        } finally {
            String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Ajay\",\"last_name\": \"Vish\uD83D\uDE42\uD83D\uDE42%\",\"email\": \"ajayv.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";
            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");
            String email = data.getString("email");
            String address = data.getString("address");


            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Ajay"));
            assertThat(last_name, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
            assertThat(email, equalTo("ajayv.exactink@gmail.com"));
            assertThat(address, equalTo("usa"));

        }
    }


    @Test
    public void updateUserWithOnlyRequiredField() {

        try {
            String requestBody = "{\"name\": \"Test1 User1\",\"first_name\": \"Test\",\"last_name\": \"User\",\"address\": \"usa\"}";

            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");

            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Test"));
            assertThat(last_name, equalTo("User"));

        } finally {
            String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Ajay\",\"last_name\": \"Vish\uD83D\uDE42\uD83D\uDE42%\",\"email\": \"ajayv.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";
            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");
            String email = data.getString("email");
            String address = data.getString("address");


            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Ajay"));
            assertThat(last_name, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
            assertThat(email, equalTo("ajayv.exactink@gmail.com"));
            assertThat(address, equalTo("usa"));
        }
    }


    @Test
    public void updateUserWithEmojiInFirstAndLastName() {

        try {
            String requestBody = "{\"name\": \"Test1 User1\",\"first_name\": \"\uD83D\uDE42\uD83D\uDE42\",\"last_name\": \"\uD83D\uDE42\uD83D\uDE42\",\"address\": \"usa\"}";

            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");

            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("\uD83D\uDE42\uD83D\uDE42"));
            assertThat(last_name, equalTo("\uD83D\uDE42\uD83D\uDE42"));

        } finally {
            String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Ajay\",\"last_name\": \"Vish\uD83D\uDE42\uD83D\uDE42%\",\"email\": \"ajayv.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";
            RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
            Response response = updateUserByUserIDWith(accessToken, userId, requestBody);
            JSONObject jsonObject = new JSONObject(response.asString());

            // Extracting values from JSON response
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            String first_name = data.getString("first_name");
            String last_name = data.getString("last_name");
            String email = data.getString("email");
            String address = data.getString("address");


            // Creating Hamcrest assertions
            assertThat("Status code is not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));

            assertThat(status, equalTo(true));
            assertThat(message, equalTo("User successfully updated."));
            assertThat(first_name, equalTo("Ajay"));
            assertThat(last_name, equalTo("Vish\uD83D\uDE42\uD83D\uDE42%"));
            assertThat(email, equalTo("ajayv.exactink@gmail.com"));
            assertThat(address, equalTo("usa"));
        }
    }

    @Test
    public void updateUserByUserIdWithInvalidUserId() {
        String userId = UUID.randomUUID().toString();

        String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Test\",\"last_name\": \"User\",\"email\": \"TestUser.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\",\"profile_image\": \"65a7cb4ff8643609c01473c0\", \"cover_image\": \"65a7cb4ff8643609c01473c0\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = updateUserByUserIDWith(accessToken, userId, requestBody);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 400", response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(message, equalTo("\"\"user_id\"\" must be a valid mongo id"));
    }

    @Test
    public void updateUserByUserIdWithInvalidToken() {

        String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Test\",\"last_name\": \"User\",\"email\": \"TestUser.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = updateUserByUserIDWith("12" + accessToken, userId, requestBody);

        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void updateUserByUserIdWithInvalidTokenAndInvalidId() {
        String requestBody = "{\"name\": \"Test User\",\"first_name\": \"Test\",\"last_name\": \"User\",\"email\": \"TestUser.exactink@gmail.com\",\"address\": \"usa\",\"status\": \"active\", \"about_us\": \"about_us\"}";
        String userId = UUID.randomUUID().toString();

        RestAssured.baseURI = ApplicationConfigration.INSTANCE.getBaseUrl();
        Response response = updateUserByUserIDWith("12" + accessToken, userId, requestBody);


        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        // Creating Hamcrest assertions
        assertThat("Status code is not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(message, equalTo("Please authenticate"));
    }

    @Test
    public void deleteUserByIdWithValidDetails() {
        try {
            createUser();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Response response1 = deleteUserByUserIDWith(accessToken, UserId);
            assertThat("Status code not 200", response1.getStatusCode(), equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    public void deleteUserByIdWithInvalidId() {
        String userID = UUID.randomUUID().toString();
        Response response1 = deleteUserByUserIDWith(accessToken, userID);
        assertThat("Status code not 400", response1.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void deleteUserByIdWithInvalidIdAndToken() {
        String userID = UUID.randomUUID().toString();
        Response response1 = deleteUserByUserIDWith("12" + accessToken, userID);
        assertThat("Status code not 401", response1.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void deleteUserByIdWithInvalidToken() {
        try {
            createUser();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Response response1 = deleteUserByUserIDWith("12" + accessToken, UserId);
            assertThat("Status code not 401", response1.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        }
    }

    @Test
    public void uploadReference_Obj() {
        File imageFile = new File("src/main/resources/media_20240207_122126_9221034820865739533 (1).jpg");
        Response response = uploadReference_ObjWith(accessToken, imageFile);
        JSONObject jsonObject = new JSONObject(response.asString());
        String message = jsonObject.getString("message");

        JSONObject data = jsonObject.getJSONObject("data");
        String uploadStatus = data.getString("status");
        String media = data.getString("media");
        reference_id = data.getString("_id");
        String user = data.getString("user");

        assertThat("status code not 200", response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat("data is null ", data, notNullValue());
        assertThat("media id is null ", media, notNullValue());
        assertThat("id is null ", reference_id, notNullValue());
        assertThat("user is null ", user, notNullValue());
        assertThat(uploadStatus, equalTo("pending"));
        assertThat(message, equalTo("Reference object sucessfully uploaded"));
    }


    @Test
    public void uploadReference_ObjWithInvalidToken() {
        File imageFile = new File("src/main/resources/media_20240207_122126_9221034820865739533 (1).jpg");
        Response response = uploadReference_ObjWith("12" + accessToken, imageFile);

        assertThat("status code not 401", response.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void getReferenceObjById() {
        try {
            File imageFile = new File("src/main/resources/media_20240207_122126_9221034820865739533 (1).jpg");
            Response response = uploadReference_ObjWith(accessToken, imageFile);
            JSONObject jsonObject = new JSONObject(response.asString());
            JSONObject data = jsonObject.getJSONObject("data");
            reference_id = data.getString("_id");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Response response1 = getReferenceObjectWith(accessToken, reference_id);
            JSONObject jsonObject = new JSONObject(response1.asString());
            String message = jsonObject.getString("message");

            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject media = data.getJSONObject("media");
            reference_id = data.getString("_id");

            assertThat("status code not 200", response1.getStatusCode(), equalTo(HttpStatus.SC_OK));
            assertThat("data is null ", data, notNullValue());
            assertThat("media id is null ", media, notNullValue());
            assertThat("id is null ", reference_id, notNullValue());
            assertThat(message, equalTo("Reference object"));
        }
    }

    @Test
    public void getReferenceObjByInvalidId() {
        String referenceId = UUID.randomUUID().toString();
        Response response1 = getReferenceObjectWith(accessToken, referenceId);
        assertThat("status code not 400", response1.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void getReferenceObjByInvalidToken() {
        try {
            File imageFile = new File("src/main/resources/media_20240207_122126_9221034820865739533 (1).jpg");
            Response response = uploadReference_ObjWith(accessToken, imageFile);
            JSONObject jsonObject = new JSONObject(response.asString());
            JSONObject data = jsonObject.getJSONObject("data");
            reference_id = data.getString("_id");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Response response1 = getReferenceObjectWith("12"+accessToken, reference_id);
            assertThat("status code not 401", response1.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        }
    }

    @Test
    public void getReferenceObjByInvalidTokenAndId() {
        String referenceId = UUID.randomUUID().toString();
        Response response1 = getReferenceObjectWith("12"+accessToken, referenceId);
            assertThat("status code not 401", response1.getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    private String createUser() {
        Faker faker = new Faker();

        String number = faker.number().digits(4);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = "fake+" + number + "d@example.com";
        String body = "{\"first_name\": \"" + firstName + "\",\"last_name\": \"" + lastName + "\",\"email\": \"" + email + "\",\"password\": \"test#123\"}";

        Response response = given().contentType(ContentType.JSON).body(body).when().post("/auth/register").then().extract().response();
        JSONObject jsonObject = new JSONObject(response.asString());

        // Extracting values from JSON response
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject createdUser = data.getJSONObject("created_user");
        UserId = createdUser.getString("id");
        return UserId;
    }

    private Response getReferenceObjectWith(String accessToken, String referenceObjId) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).when().get("/users/reference/" + referenceObjId).then().extract().response();
        return response;
    }

    private Response getUsersWith(String accessToken) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).when().get("/users").then().extract().response();
        return response;
    }

    private Response getUserByIDWith(String accessToken, String userId) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).when().get("/users/" + userId).then().extract().response();
        return response;
    }

    private Response getFriendsByUserIDWith(String accessToken, String userId) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).when().get("/users/" + userId + "/friends").then().extract().response();
        return response;
    }


    private Response getCatchesByUserIDWith(String accessToken, String userId) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).when().get("/users/" + userId + "/catches").then().extract().response();
        return response;
    }

    private Response updateUserByUserIDWith(String accessToken, String userId, Object requestBody) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).body(requestBody).when().put("/users/" + userId).then().extract().response();
        return response;
    }

    private Response deleteUserByUserIDWith(String accessToken, String userId) {
        Response response = given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).when().delete("/users/" + userId).then().extract().response();
        return response;
    }

    private Response uploadReference_ObjWith(String accessToken, File imageFile) {
        Response response = given().header("Authorization", "Bearer " + accessToken).multiPart("reference_obj_img", imageFile).when().post("/users/reference_obj/upload").then().extract().response();
        return response;
    }

}