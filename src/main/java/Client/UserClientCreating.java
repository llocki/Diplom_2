package Client;

import DTO.UserDTO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClientCreating {

    String basePathCreating = "/api/auth/register";

    @Step("Request to create a courier")
    public Response Creating (UserDTO user) {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(basePathCreating);
        return  response;
    }
}
