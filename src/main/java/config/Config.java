package config;

import io.restassured.RestAssured;

public class Config {
    public final void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
}
