package pens;

import builder.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PensOrder {
    private final static String PENS_ORDERS = "/api/v1/orders";
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ, проверить код ответа и номер заказа(track)")
    public Response createOrderRequest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post(PENS_ORDERS);
        return response;
    }

    @Step("Получить список заказов, проверить что он не пустой и код ответа")
    public Response getOrdersRequest() {
        Response response =
                given()
                        .get(PENS_ORDERS);
        return response;
    }
}
