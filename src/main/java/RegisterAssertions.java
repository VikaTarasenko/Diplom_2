import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class RegisterAssertions { // В  Assertions выносим проверки
    @Step("Успешное создание пользователя")
    public void createdSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Удаление успешно")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("ok", is(true))
        ;
    }
    @Step("1 из полей не заполнено")
    public String registerFailed(ValidatableResponse response){
        return response.assertThat()
                .statusCode(403)
                .body("message", notNullValue())
                .extract()
                .path("message");

    }
    @Step("Пользователь уже используется")
    public void createdUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message",equalTo("User already exists"));

    }
   @Step("Пользователь логиниться успешно")
    public ValidatableResponse loggedInSuccessfully(ValidatableResponse response) {
      return  response.assertThat()
                .statusCode(200)
                .body("success", is(true));

    }
    @Step("Логинится несуществующий пользователь")
    public void createdNonexistent(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401);
               // .body("message", equalTo("email or password are incorrect"));

    }
    @Step("Изменение данных пользователя успешно")
    public ValidatableResponse changeInSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Создание заказа успешно")
    public void createdOrderSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));

    }
    @Step("Создание заказа неуспешно")
    public void createdOrderUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("success", is(false));

    }

    @Step("Список заказов получен")
    public void createdOrderList(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract()
                .path("orders");

    }
    @Step("Список заказов не получен")
    public void unAutorizationOrderList(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", is(false));

    }

    }


