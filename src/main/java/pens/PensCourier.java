package pens;

import builder.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PensCourier {
    private final static String PENS_CREATE_COURIER = "/api/v1/courier";
    private final static String PENS_LOGIN_COURIER = "/api/v1/courier/login";
    private Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    //Ручка для создания курьера
    @Step("Создать курьера, проверить код ответа")
    public Response creatingCourier() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PENS_CREATE_COURIER);
        return response;
    }

    //Ручка для логирования курьера
    @Step("Авторизоваться курьером и получить id, проверить код ответа")
    public Response loginCourier() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PENS_LOGIN_COURIER);
        return response;
    }

    //Ручка для запроса id
    @Step("Запрос Id")
    public Response getId() {
        Response response = given()
                .get(PENS_CREATE_COURIER)
                .then().extract().body().path("id");
        return response;
    }

    //Ручка для удаления курьера
    @Step("Удалить курьера")
    public void deletingCourier() {
        Integer id = given().
                header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PENS_LOGIN_COURIER)
                .then().extract().body().path("id");
        if (id != null) {
            given()
                    .delete("/api/v1/courier/" + id);
        }
    }
}
