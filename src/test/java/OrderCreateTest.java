import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


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
        Map<String,String> ingredients = new HashMap<>();
        ingredients.put("ingredients", ingredient.get(2));
        ingredients.put("ingredients", ingredient.get(3));

        given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(ingredients)
                .when()
                .post("/api/orders")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", is(true));;

    }
    }

   


