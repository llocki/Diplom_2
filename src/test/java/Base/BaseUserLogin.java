package Base;

import Client.UserClientCreating;
import Client.UserClientDeleted;
import DTO.UserDTO;
import DTO.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

public class BaseUserLogin {
    String baseUrl = RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void creatingUser(){
        UserDTO user = new UserDTO("ivanov_40@gmail.com", "555", "ivanov");

        UserClientCreating userClientCreating = new UserClientCreating();
        Response response = userClientCreating.Creating(user);
    }
}
