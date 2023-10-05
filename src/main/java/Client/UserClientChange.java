package Client;

import DTO.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClientChange {

    String basePathChange = "/api/auth/user";

    @Step("Name change")
    public Response UserChangeName (String accessToken, String name) {

        User user = new User();
        user.setName(name);

        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(user)
                .patch(basePathChange);
        return response;
    }

    @Step("Email change")
    public Response UserChangeEmail (String accessToken, String email) {

        User user = new User();
        user.setEmail(email);

        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(user)
                .patch(basePathChange);
        return response;
    }

    @Step("Кequest without authorization")
    public Response UserChangeNotAuthorization (String email) {

        User user = new User();
        user.setEmail(email);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .patch(basePathChange);
        return response;
    }
}
