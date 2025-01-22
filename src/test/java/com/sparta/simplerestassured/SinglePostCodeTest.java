package com.sparta.simplerestassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SinglePostCodeTest {

    private static Response response;

    @BeforeAll
    static void beforeAll() {
        response = RestAssured
                .given()
                    .baseUri("https://api.postcodes.io")
                    .basePath("/postcodes")
                    .header("Accept", "text/json")
                .when()
                .get("/EC2Y5AS")
                .thenReturn();

        response.then().log().all();
    }

    @Test
    @DisplayName("Status code 200 returned")
    void testStatusCode200() {
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("The server name in the headers is cloudflare")
    void testServerNameInCloudflare() {
        response.then().assertThat().header("Server", "cloudflare");
    }

    @Test
    @DisplayName("The correct postcode is returned in response")
    void testCorrectPostcodeInResponse() {
        assertThat(response.jsonPath().getString("result.postcode"), is("EC2Y 5AS"));
    }

    @Test
    @DisplayName("Primary Care Trust is City and Hackney Teaching")
    void testPrimaryCareTrustInResponse() {
        assertThat(response.jsonPath().getString("result.primary_care_trust"),
                is("City and Hackney Teaching"));
    }

    @Test
    @DisplayName("The total number of codes returned is 14")
    void testTotalNumberOfCodesInResponse() {
        // Assert that the number of keys (codes) is 14
        assertThat(response.jsonPath().getMap("result.codes").size(), is(14));
    }

}
