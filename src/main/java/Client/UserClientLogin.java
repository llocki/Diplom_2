package Client;

import DTO.UserDTO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClientLogin {

    String basePathLogin = "/api/auth/login";

    @Step("Request for client login in the system")
    public Response Login (UserDTO user) {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(basePathLogin);
        return response;
    }
}
