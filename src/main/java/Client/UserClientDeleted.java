package Client;

import DTO.UserDTO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClientDeleted {

    String basePathDeleted = "/api/auth/user";

    @Step("Request to remove a client")
    public Response Delete (String accessToken) {

        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .delete(basePathDeleted);
        return response;
    }
}
