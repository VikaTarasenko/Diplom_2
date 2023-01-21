import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;


public class OrdersGenerator {
    public Order generic(){
        return new Order (Collections.singletonList("60d3b41abdacab0026a733c6"));//(RandomStringUtils.randomAlphanumeric(24)));

    }
    public Order random() {
        return new Order(Collections.singletonList("61c0c5a71d1f82001bdaaa70")); //создаем рандом

    }
}
