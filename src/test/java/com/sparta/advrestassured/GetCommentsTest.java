package com.sparta.advrestassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.*;

public class GetCommentsTest {
    private static Response response;
    private static final String BASE_URI = "https://api.github.com";
    private static final String PATH = "/repos/{owner}/{repo}/comments";
    private static final String OWNER = "ik1g19";
    private static final String REPO_NAME = "testing";
    private static final String BEARER_TOKEN = "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";

    @BeforeAll
    static void beforeAll() {
        response = RestAssured
                .given()
                    .baseUri(BASE_URI)
                    .basePath(PATH)
                    .headers(Map.of(
                            "Accept", "application/vnd.github+json",
                            "Authorization", "Bearer " + BEARER_TOKEN,
                            "X-Github-Api-Version", "2022-11-28"
                    ))
                    .pathParams(Map.of(
                            "owner", OWNER,
                            "repo", REPO_NAME
                    ))
                .when()
                    .get()
                .then()
                    .log().all()
                    .extract().response();
    }

    @Test
    @DisplayName("Assert status code is 200")
    void statusCodeIs200() {
        assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Get all repository comments returns 1 comment")
    void getAllRepositoryCommentsReturnsOneComment() {
        assertThat(response.jsonPath().getList("$").size(), Matchers.is(1));
    }

    @Test
    @DisplayName("First comment has correct user name associated")
    void firstCommentHasCorrectUserName() {
        assertThat(response.jsonPath().getList("user.login"), Matchers.contains(OWNER));
    }
}
