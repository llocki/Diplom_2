package Client;

import Base.BaseUser;
import DTO.UserDTO;
import DTO.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserChangeTest extends BaseUser {

    UserDTO user = new UserDTO("ivanov_59@gmail.com", "555", "ivanov");
    @Test
    @DisplayName("Change username with authorization")
    public void ChangeName() {

        //Создание пользователя
        UserClientCreating userClientCreating = new UserClientCreating();
        userClientCreating.Creating(user);

        //Авторизация пользователя
        UserClientLogin userClientLogin = new UserClientLogin();
        Response response_1 = userClientLogin.Login(user);

        //Десериализация
        UserResponse userResponse = response_1.as(UserResponse.class);
        //удаление начала токена
        String accessToken = userResponse.getAccessToken().substring(7);

        //Смена логина
        UserClientChange userClientChange = new UserClientChange();
        Response response_2 = userClientChange.UserChangeName(accessToken, "Bob");

        response_2.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("user.name", equalTo("Bob"));

        //Удаление пользователя
        UserClientDeleted userClientDeleted = new UserClientDeleted();
        userClientDeleted.Delete(accessToken);

    }

    @Test
    @DisplayName("Changing user mail with authorization")
    public void ChangeEmail() {

        //Создание пользователя
        UserClientCreating userClientCreating = new UserClientCreating();
        userClientCreating.Creating(user);

        //Авторизация пользователя
        UserClientLogin userClientLogin = new UserClientLogin();
        Response response_1 = userClientLogin.Login(user);

        //Десериализация
        UserResponse userResponse = response_1.as(UserResponse.class);
        //удаление начала токена
        String accessToken = userResponse.getAccessToken().substring(7);

        //Смена почты
        UserClientChange userClientChange = new UserClientChange();
        Response response_2 = userClientChange.UserChangeEmail(accessToken, "ivanov_59@yandex.ru");

        response_2.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("user.email", equalTo("ivanov_59@yandex.ru"));

        //Удаление пользователя
        UserClientDeleted userClientDeleted = new UserClientDeleted();
        userClientDeleted.Delete(accessToken);
    }

    @Test
    @DisplayName("Changing user data without authorization")
    public void Change() {

        //Смена почты
        UserClientChange userClientChange = new UserClientChange();
        Response response_2 = userClientChange.UserChangeNotAuthorization( "ivanov_59@yandex.ru");

        response_2.then().assertThat().statusCode(401)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}