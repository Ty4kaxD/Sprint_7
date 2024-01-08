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

public class TestCourierCreate {

    public PensCourier pensCourier = new PensCourier();
    public Config config = new Config();

    @Before
    public void setupBaseUrl() {
        config.setUp();

    }

    @After
    public void deleteCourier() {
        pensCourier.deletingCourier();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера с валидным телом запроса")
    public void creatingCourier() {
        Courier courier = new Courier("Pavlov", "12345", "Roman");
        pensCourier.setCourier(courier);
        pensCourier.creatingCourier()
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка валидации, создание курьера со значение обязательного поля login = null")
    public void createCourierWithoutPassword() {
        Courier courier = new Courier(null, "12345", "Romulus");
        pensCourier.setCourier(courier);
        pensCourier.creatingCourier()
                .then()
                .statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка создания курьера без пароля")
    public void createCourierWithoutLogin() {
        Courier courier = new Courier("Pavlov", "", "Romus");
        pensCourier.setCourier(courier);
        pensCourier.creatingCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка возможности создание курьера без параметра firstName в теле запроса")
    public void createCourierWithoutName() {
        Courier courier = new Courier("Pavlov", "12345qwerty");
        pensCourier.setCourier(courier);
        pensCourier.creatingCourier()
                .then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров")
    @Description("Проверка невозможности создания двух курьеров с одинаковым login")
    public void creationCourierWithDoubleLogin() {
        Courier courierA = new Courier("Pavlov", "12345", "Roman");
        pensCourier.setCourier(courierA);
        pensCourier.creatingCourier();
        Courier courierB = new Courier("Pavlov", "12345", "Roman");
        pensCourier.setCourier(courierB);
        pensCourier.creatingCourier()
                .then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        //на обучающем сайте в описании данной точки входа ошибка - возращается "Этот логин уже используется."
    }

}
