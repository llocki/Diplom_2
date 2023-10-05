package Client;

import Base.BaseUser;
import DTO.UserDTO;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserCreatingErrorTest extends BaseUser {

    @Test
    @DisplayName("Create a user and leave one of the fields blank")
    public void requiredFields(){
        UserDTO user = new UserDTO("ivanov_38@gmail.com", "555");

        UserClientCreating userClientCreating = new UserClientCreating();
        Response response = userClientCreating.Creating(user);

        response.then().assertThat().statusCode(403)
                .and()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}
