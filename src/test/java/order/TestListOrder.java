package order;

import config.Config;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pens.PensOrder;

import static org.hamcrest.CoreMatchers.notNullValue;

public class TestListOrder {
    PensOrder pensOrder = new PensOrder();
    Config config = new Config();

    @Before
    public void setupBaseURL() {
        config.setUp();
    }

    @Test
    @DisplayName("Получение списка заказов(позитив)")
    @Description("Проверка получения списка заказов")
    public void checkGetOrders() {
        pensOrder.getOrdersRequest()
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
