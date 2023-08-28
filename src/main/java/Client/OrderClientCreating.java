package Client;

import DTO.OrderDTO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClientCreating {

    String basePathCreatingOrder = "/api/orders";

    @Step("Request to create an order")
    public Response CreatingOrder (OrderDTO order) {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(basePathCreatingOrder);
        return  response;
    }

@Step("Request to get an order")
public Response GetOrdersWithAccessToken (String accessToken, OrderDTO order) {

    Response response = given()
            .header("Content-type", "application/json")
            .auth().oauth2(accessToken)
            .and()
            .body(order)
            .when()
            .get(basePathCreatingOrder);
    return  response;
}

    @Step("Request to get an order without accessToken")
    public Response GetOrdersWithoutAccessToken ( OrderDTO order) {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .get(basePathCreatingOrder);
        return  response;
    }

}
