package Client;

import Base.BaseUser;
import DTO.OrderDTO;
import DTO.UserDTO;
import DTO.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class OrderCreatingTest extends BaseUser {

    UserDTO user = new UserDTO("ivanov_65@gmail.com", "555", "ivanov");

    @Test
    @DisplayName("Creating an order with authorization")
    public void orderWithAuthorization() {
        //Создание и логин в систему

        UserClientCreating userClientCreating = new UserClientCreating();
        userClientCreating.Creating(user);

        UserClientLogin userClientLogin = new UserClientLogin();
        Response response_1 = userClientLogin.Login(user);
        //------------------------------------------------------------------------------------------------------------
        //Создание заказа с авторизацией
        String[] ingredientsOrder = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa70"};
        OrderDTO order = new OrderDTO(ingredientsOrder);

        OrderClientCreating orderClientCreating = new OrderClientCreating();
        Response response_2 = orderClientCreating.CreatingOrder(order);

        response_2.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("name", equalTo("Бессмертный метеоритный флюоресцентный бургер"))
                .and()
                .assertThat().body("success", equalTo(true));
        //-------------------------------------------------------------------------------------------------------------
        //Десериализация
        UserResponse userResponse = response_1.as(UserResponse.class);
        //удаление начала токена
        String accessToken = userResponse.getAccessToken().substring(7);

        //Удаление пользователя
        UserClientDeleted userClientDeleted = new UserClientDeleted();
        userClientDeleted.Delete(accessToken);
    }


    @Test
    @DisplayName("Creating an order with authorization and without ingredients")
    public void orderWithAuthorizationWithoutIngredients() {
        //Создание и логин в систему


        UserClientCreating userClientCreating = new UserClientCreating();
        userClientCreating.Creating(user);

        UserClientLogin userClientLogin = new UserClientLogin();
        Response response_1 = userClientLogin.Login(user);
        //------------------------------------------------------------------------------------------------------------
        //Создание заказа с авторизацией

        OrderDTO order = new OrderDTO();

        OrderClientCreating orderClientCreating = new OrderClientCreating();
        Response response_2 = orderClientCreating.CreatingOrder(order);

        response_2.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
        //-------------------------------------------------------------------------------------------------------------
        //Десериализация
        UserResponse userResponse = response_1.as(UserResponse.class);
        //удаление начала токена
        String accessToken = userResponse.getAccessToken().substring(7);

        //Удаление пользователя
        UserClientDeleted userClientDeleted = new UserClientDeleted();
        userClientDeleted.Delete(accessToken);
    }


    @Test
    @DisplayName("Creating an order without authorization")
    public void orderWithoutAuthorization() {

        String[] ingredientsOrder = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa70"};
        OrderDTO order = new OrderDTO(ingredientsOrder);

        OrderClientCreating orderClientCreating = new OrderClientCreating();
        Response response = orderClientCreating.CreatingOrder(order);

        response.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("name", equalTo("Бессмертный метеоритный флюоресцентный бургер"))
                .and()
                .assertThat().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Creating an order without ingredients")
    public void orderWithoutIngredients() {

        OrderDTO order = new OrderDTO();

        OrderClientCreating orderClientCreating = new OrderClientCreating();
        Response response = orderClientCreating.CreatingOrder(order);

        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }


    @Test
    @DisplayName("Creating an order with invalid ingredient hash")
    public void orderWithIngredientsError() {

        String[] ingredientsOrder = {"61c0c5a71d1f8", "61c0c5a7","61c0c5aaa70"};
        OrderDTO order = new OrderDTO(ingredientsOrder);

        OrderClientCreating orderClientCreating = new OrderClientCreating();
        Response response = orderClientCreating.CreatingOrder(order);

        response.then().assertThat().statusCode(500);

    }

//-----------------------------------------------------------

    @Test
    @DisplayName("Get an order with authorization")
    public void getOrderWithAuthorization() {
        //Создание пользователя
        UserClientCreating userClientCreating = new UserClientCreating();
        userClientCreating.Creating(user);

        UserClientLogin userClientLogin = new UserClientLogin();
        Response response_1 = userClientLogin.Login(user);

        //------------------------------------------------------------------------------------------------------------
        //Создание заказа
        String[] ingredientsOrder = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa70"};
        OrderDTO order = new OrderDTO(ingredientsOrder);

        OrderClientCreating orderClientCreating = new OrderClientCreating();
        orderClientCreating.CreatingOrder(order);
        //--------------------------------------------------------------------------------------------------------------
        //Десериализация
        UserResponse userResponse = response_1.as(UserResponse.class);
        //Удаление начала токена
        String accessToken = userResponse.getAccessToken().substring(7);

        //-------------------------------------------------------------------------------------------------------------
        //Возврат списка заказов
        Response response_3 = orderClientCreating.GetOrdersWithAccessToken(accessToken, order);

        response_3.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));

        //-------------------------------------------------------------------------------------------------------------
        //Удаление пользователя
        UserClientDeleted userClientDeleted = new UserClientDeleted();
        userClientDeleted.Delete(accessToken);

    }


    @Test
    @DisplayName("Get an order Without authorization")
    public void getOrderWithoutAuthorization() {

        String[] ingredientsOrder = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa70"};
        OrderDTO order = new OrderDTO(ingredientsOrder);

        OrderClientCreating orderClientCreating = new OrderClientCreating();

        Response response_3 = orderClientCreating.GetOrdersWithoutAccessToken(order);

        response_3.then().assertThat().statusCode(401)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));


    }


}