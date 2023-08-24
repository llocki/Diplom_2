package Client;

import Base.BaseUserCreating;
import DTO.UserDTO;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserCreatingTest extends BaseUserCreating {

    UserDTO user = new UserDTO("ivanov_1@gmail.com", "555", "ivanov");

    @Test
    @DisplayName("Create a unique user")
    public void creatingUser() {


        UserClientCreating userClientCreating = new UserClientCreating();
        Response response = userClientCreating.Creating(user);

        response.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Create a user who is already registered")
    public void twoIdenticalUsers(){


        UserClientCreating userClientCreating = new UserClientCreating();
        Response response = userClientCreating.Creating(user);

        UserClientCreating userClientCreating_1 = new UserClientCreating();
        Response response_1 = userClientCreating.Creating(user);

        response_1.then().assertThat().statusCode(403)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("User already exists"));

    }
}