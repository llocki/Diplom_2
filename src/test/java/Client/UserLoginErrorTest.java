package Client;

import Base.BaseUser;
import DTO.UserDTO;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginErrorTest extends BaseUser {
    @Test
    @DisplayName("Login with wrong username and password")
    public void loginError() {// Вход в систему с неверным логином и паролем
        UserDTO user = new UserDTO("ivanov_40@gmail", "555");

        UserClientLogin userClientLogin = new UserClientLogin();
        Response response = userClientLogin.Login(user);

        response.then().assertThat().statusCode(401)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
