package com.sparta.advrestassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetCommitCommentWithValidIdTest {
    private static Response response;
    private static final String BASE_URI = "https://api.github.com";
    private static final String PATH = "/repos/{owner}/{repo}/comments/{comment_id}";
    private static final String OWNER = "ik1g19";
    private static final String REPO_NAME = "testing";
    private static final String BEARER_TOKEN = "";
    private static final String COMMENT_ID = "151626468";
    private static final String SERVER_HEADER = "github.com";

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
                            "repo", REPO_NAME,
                            "comment_id", COMMENT_ID
                    ))
                .when()
                    .get()
                .then()
                    .log().all()
                    .extract().response();
    }

    @Test
    @DisplayName("Get comment with a specific Id returns a comment with that Id")
    void getCommentWithId_ReturnsThatComment() {
        assertThat(response.jsonPath().getString("id"), Matchers.is(COMMENT_ID));
    }

    @Test
    @DisplayName("Get comment with a specific Id and check the Server header")
    void getCommentWithId_ChecksServerHeader() {
        assertThat(response.header("Server"), Matchers.is(SERVER_HEADER));
    }

    @Test
    @DisplayName("Get comment with a specific Id and check the status code")
    void getCommentWithId_ChecksStatusCode() {
        assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Get comment with a specific Id and check the reactions total count")
    void getCommentWithId_ChecksReactionsTotalCount() {
        assertThat(response.jsonPath().getInt("reactions.total_count"), Matchers.is(0));
    }
}
