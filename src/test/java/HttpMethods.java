import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class HttpMethods {
    String BASE_URL = "http://localhost:8090/api";
    String TOKEN = getToken();
    @Test
    public String getToken() {
        String username = "admin";
        String password = "admin";
        String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"; //json payload

        String token = given()
                .contentType("application/json")
                .body(requestBody)

                .when()
                .post(BASE_URL + "/authenticate")

                .then()
                .statusCode(200)
                .log().all() //logging all details (headers, body, etc)
                .extract()
                .path("token");

        return token;
    }

    @Test
    public void getAllCategories(){
        RestAssured
                .get(BASE_URL + "/category")
                .prettyPeek();
    }

    @Test
    public void createCategory(){
       RestAssured
               .given()
               .header("Authorization", "Bearer " + TOKEN)
               .contentType("application/json")
               .body("{\"name\":\"" + "Lucky Evance category\"}")
               .log().all() //logging all details (headers, body, etc)

               .when()
               .post(BASE_URL + "/category")
               .prettyPeek()

               .then()
               .statusCode(200);



    }

    @Test
    public void updateCategory(){
        RestAssured
                .given()
                .header("Authorization", "Bearer " + TOKEN)
                .contentType("application/json")
                .body("{\"name\":\"" + "Evans Mbongo category\"}")
                .log().all() //logging all details (headers, body, etc)

                .when()
                .put(BASE_URL + "/category/7")
                .prettyPeek()

                .then()
                .statusCode(200);


    }
    @Test
    public void deleteCategory(){
        RestAssured
                .given()
                .auth()
                .oauth2(TOKEN) //Alternative of using the headers
                //.header("Authorization", "Bearer " + TOKEN)
                .contentType("application/json")

                .when()
                .delete(BASE_URL + "/category/5")
                .prettyPeek()

                .then()
                .statusCode(200);
    }

}
