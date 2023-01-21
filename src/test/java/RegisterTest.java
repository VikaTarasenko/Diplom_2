import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;



public class RegisterTest {

    private final RegisterGenerator registerGenerator = new RegisterGenerator();
    private final RegisterClient client = new RegisterClient();
    private final RegisterAssertions check = new RegisterAssertions();
    private String accessToken;


    @Before


    @After
    @DisplayName("Удаление пользователя")
    public void deleteRegister() {
        if (accessToken != null) {
            ValidatableResponse response = client.delete(accessToken);
            check.deletedSuccessfully(response);
        }
    }

    @Test
    @DisplayName("Создание пользователя")
    public void register() {
        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")

    public void login() {

        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(register);
        ValidatableResponse loginResponse = client.autorization(creds);
        check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Не заполнено 1 поле")
    public void loginFails() { // негативный тест на проверку заполнения 1 поля
        var register = registerGenerator.generic();
        register.setPassword(null);
        ValidatableResponse nameResponse = client.create(register);
        String message = check.registerFailed(nameResponse);
        assert !message.isBlank();
    }

    @Test
    @DisplayName("Создание 2х одинаковых пользователей")
    public void checkSameRegister() {
        var register = registerGenerator.generic();
        ValidatableResponse creationResponse = client.create(register);
        check.createdUnSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Авторизация под несуществуюшим пользователем")
    public void checkNonexistentCred() {
        var courier = registerGenerator.random();
        ValidatableResponse autorizationResponse = client.autorization(Credentials.from(courier));
        check.createdNonexistent(autorizationResponse);

    }

    @Test
    @DisplayName("Авторизация и создание заказа")

    public void autotorizationOrder() {

        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(register);
        ValidatableResponse loginResponse = client.autorization(creds);
        check.loggedInSuccessfully(loginResponse);
        OrdersGenerator orderGenerator = new OrdersGenerator();
        var order = orderGenerator.random();
        ValidatableResponse response = client.createOrder(order);
        check.createdOrderSuccessfully(response);
    }

    @Test
    @DisplayName("Получение списка заказов конкретного неавториз.пользователя")

    public void orderListUnAutorization() {
        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);
        Credentials creds = Credentials.from(register);
        ValidatableResponse loginResponse = client.autorization(creds);
        check.loggedInSuccessfully(loginResponse);
        OrdersGenerator orderGenerator = new OrdersGenerator();
        var order = orderGenerator.random();
        ValidatableResponse response = client.createOrder(order);
        check.createdOrderSuccessfully(response);
        ValidatableResponse listResponse = client.orderListNotAutorization(order);
        check.unAutorizationOrderList(listResponse);
    }

    @Test
    @DisplayName("Обновление пользователя успешно")
    public void userNameUpdatedSuccesfully() {
        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);
        Credentials credentials = Credentials.from(register);
        String accessToken = given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(credentials)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/login")
                .then().statusCode(200)
                .extract().path("accessToken");
        register.setName("Обновленное имя"); //обновили
        given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(register)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then().statusCode(200)
                .body("success", is(true));

    }
    @Test
    @DisplayName("Обновление пользователя неуспешно")
    public void userNameUpdateFailed() {
        var register = registerGenerator.random();
        ValidatableResponse creationResponse = client.create(register);
        check.createdSuccessfully(creationResponse);
        register.setName("Обновленное имя"); //обновили
        given().log().all()
                .contentType(ContentType.JSON)
                .auth().oauth2("") //передаем пустой токен
                .and()
                .body(register)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then().statusCode(401)
                .body("success", is(false));
    }
}




