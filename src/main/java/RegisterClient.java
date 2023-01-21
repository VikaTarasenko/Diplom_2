import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.is;

public class RegisterClient { // В Client выносить все, что дергает ручки апи
    private final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private final String ROOT = "/api/auth/register";

    private final String ROOT1 = "/api/auth/user";
    private final String ROOT2 = "/api/auth/login";
    private final String ROOT3 = "/api/orders";


    @Step("POST /api/auth/register")
    public ValidatableResponse create(Register register) { // вынесли повторяющуюся часть дергания апи отдельно
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(register)
                .when()
                .post(ROOT)
                .then().log().all();

    }

    @Step("POST  /auth/login ")
    public ValidatableResponse autorization(Credentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(creds)
                .when()
                .post(ROOT2)
                .then().log().all();

    }

    @Step("DELETE /api/auth/user")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .when()
                .delete("/api/auth/user")
                .then()
                .assertThat().statusCode(SC_ACCEPTED);

    }

    @Step("POST /api/orders")
    public ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT3)
                .then().log().all();
    }

    @Step("GET /api/orders авториз.")
    public ValidatableResponse orderList(Order order, String accessToken) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .header("Autorization", accessToken)
                .body(order)
                .when()
                .get(ROOT3)
                .then().log().all();
    }

    @Step("GET /api/orders неавториз.")
    public ValidatableResponse orderListNotAutorization(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .get(ROOT3)
                .then().log().all();
    }

    @Step("POST /auth/login вытаскиваем токен")
    public ValidatableResponse userNameUpdated  (Register register) {

        return  given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(register)
                .when()
                .post(ROOT2)
                .then().statusCode(200)
                .extract().path("accessToken");

    }

    }

