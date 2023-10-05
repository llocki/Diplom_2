package Client;

import Base.BaseUserLogin;
import DTO.UserDTO;
import DTO.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest extends BaseUserLogin {

    @Test
    @DisplayName("Login")
    public void login() { // Вход в систему
        UserDTO user = new UserDTO("ivanov_40@gmail.com", "555");

        UserClientLogin userClientLogin = new UserClientLogin();
        Response response = userClientLogin.Login(user);

        response.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));

        //Десериализация
        UserResponse userResponse = response.as(UserResponse.class);
        //удаление начала токена
        String accessToken = userResponse.getAccessToken().substring(7);

        //Удаление пользователя
        UserClientDeleted userClientDeleted = new UserClientDeleted();
        userClientDeleted.Delete(accessToken);
    }
}