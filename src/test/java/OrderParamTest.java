import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderParamTest {
    private final RegisterClient client = new RegisterClient();
    private final RegisterAssertions check = new RegisterAssertions();


    private List<String> ingredients;
    public OrderParamTest(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    @Parameterized.Parameters
    public static Object[][] dataGen() {
        return new Object[][] {
                {List.of("61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa79" )},
                {List.of("61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa7a")},

        };
    }
    @Test
    @DisplayName("Создание заказа, разные варианты")
    public void order() { // создаем заказ
        Order order = new Order(ingredients);
        ValidatableResponse creationResponse = client.createOrder(order);
        check.createdOrderSuccessfully(creationResponse);

    }
}
