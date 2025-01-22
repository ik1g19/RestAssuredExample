package com.sparta.simplerestassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SinglePostCodeTest {
    @Test
    @DisplayName("Status code 200 returned")
    void testStatusCode200() {
        Response response = RestAssured
                .given()
                    .baseUri("https://api.postcodes.io")
                    .basePath("/postcodes")
                    .header("Accept", "text/json")
                .when()
                    .log().all()
                    .get("/EC2Y5AS")
                .thenReturn();

        response.then().assertThat().statusCode(200);

//        RestAssured
//                .get("https://api.postcodes.io/postcodes/EC2Y5AS")
//                .then()
//                .assertThat().statusCode(200);
    }
}
