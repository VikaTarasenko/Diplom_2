import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class OrderTest {
    private final OrdersGenerator ordersGenerator = new OrdersGenerator();
    private final RegisterClient client = new RegisterClient();
    private final RegisterAssertions check = new RegisterAssertions();

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void order() {
        var order = ordersGenerator.random();
        ValidatableResponse creationResponse = client.createOrder(order);
        check.createdOrderSuccessfully(creationResponse);

    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void order2() {
        var order = ordersGenerator.generic();
        ValidatableResponse creationResponse = client.createOrder(order);
        check.createdOrderUnSuccessfully(creationResponse);
}

    }
