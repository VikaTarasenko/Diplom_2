import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.ValidatableResponse;
import org.apache.groovy.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderCreateTest {
private final RegisterClient client = new RegisterClient();
    private final RegisterAssertions check = new RegisterAssertions();
    private final RegisterGenerator registerGenerator = new RegisterGenerator();
    private final String BASE_URI = "https://stellarburgers.nomoreparties.site";


    @Test
    @DisplayName("Создание заказа")
    public void order() { // создаем заказ

        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(register);
        ValidatableResponse loginResponse = client.autorization(creds);
        check.loggedInSuccessfully(loginResponse);

        List<String> ingredient = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("https://stellarburgers.nomoreparties.site/api/ingredients")
                .then()
                .extract().path("data._id");
        var Ing = new String[]{"ingredients", ingredient.toString()};
        given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(Ing)
                .when()
                .post("/api/orders")
                .then().log().all();

    }
    }

   


