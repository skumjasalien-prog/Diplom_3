package org.example.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String BASE_URI = "https://stellarburgers.education-services.ru";

    static {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Создать рандомного тестового пользователя через API")
    public TestUser createRandomUser() {
        String email = "auto_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        String password = "Pass" + UUID.randomUUID().toString().substring(0, 6);
        String name = "Автотест";

        TestUser user = new TestUser(email, password, name);

        CreateUserRequest requestBody = new CreateUserRequest(email, password, name);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/register");

        String accessToken = response
                .then()
                .extract()
                .path("accessToken");

        user.setAccessToken(accessToken);
        return user;
    }

    @Step("Удалить тестового пользователя через API")
    public void deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return;
        }

        given()
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }
}

