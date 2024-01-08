package courier;

import builder.Courier;
import config.Config;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.PensCourier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestCourierLogin {
    PensCourier pensCourier = new PensCourier();
    Config config = new Config();

    @Before
    public void setupBaseURL() {
        config.setUp();
    }

    @After
    public void deleteCourier() {
        pensCourier.deletingCourier();
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверки возможности авторизаваться курьером при введении валидных данных")
    public void AutorizationСourier() {
        Courier courier = new Courier("Pavlov", "12345", "Romantik");
        pensCourier.setCourier(courier);
        pensCourier.creatingCourier();
        pensCourier.loginCourier()
                .then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка возможности авторизоваться с пустым значением поля login")
    public void loginCourierWithoutPassword() {
        Courier courierA = new Courier("Pavlov123", "1234567876", "qaazxsw21");
        pensCourier.setCourier(courierA);
        pensCourier.creatingCourier();
        Courier courierB = new Courier("", "1234567876");
        pensCourier.setCourier(courierB);
        pensCourier.loginCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без password")
    @Description("Проверка возможности авторизоваться с пустым значением поля password")
    public void loginACourierWithoutALogin() {
        Courier courierA = new Courier("Pavlov123", "12345", "Romandiy");
        pensCourier.setCourier(courierA);
        pensCourier.creatingCourier();
        Courier courierB = new Courier("Pavlov123", "");
        pensCourier.setCourier(courierB);
        pensCourier.loginCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация несуществующим пользователем")
    @Description("Проверка валидации параметров при входе, авторизация с несуществующими login,password")
    public void loginWithWrongLogin() {
        Courier courier = new Courier("qazwsxedcvfr", "123wafet454tregdf");
        pensCourier.setCourier(courier);
        pensCourier.loginCourier()
                .then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

}
