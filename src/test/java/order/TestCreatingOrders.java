package order;

import builder.Order;
import config.Config;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pens.PensOrder;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreatingOrders {

    Order order;
    Config config = new Config();
    PensOrder pensOrder = new PensOrder();

    public TestCreatingOrders(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getTestOrderData() {
        return new Object[][]{
                {new Order("Pavlov", "Roman", "Москва", "Первая", "8 921 234 21 21", 99, "2024-03-03", "Привет", new String[]{"BLACK"})},
                {new Order("Петр", "Иван", "Питер", "0", "+7 999 999 99 99", 2, "2024-01-01", "КУ-ку", new String[]{"GREY"})},
                {new Order("Хиляков", "Саламон", "Макдональдас", "1", "+6 123 456 78 90", 5, "2024-02-02", "Яхуууу", new String[]{"BLACK", "GREY"})},
                {new Order("Вельзевул", "Ужас", "круг 4", "666", "1231123123", 10, "2024-04-04", "оу,ес!", new String[]{})},
        };
    }

    @Before
    public void setupBaseURL() {
        config.setUp();
    }

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void checkCreateOrder() {
        pensOrder.setOrder(order);
        pensOrder.createOrderRequest()
                .then().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
