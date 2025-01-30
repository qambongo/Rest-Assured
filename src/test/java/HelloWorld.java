import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import static org.hamcrest.Matchers.*;

public class HelloWorld {
    String BASE_URL = "https://jsonplaceholder.typicode.com";
    @Test
    public void testHelloWorld1() {
        Response response = RestAssured.get(BASE_URL + "/posts" );
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");
        response.prettyPeek();

//                .prettyPeek(); // prints and formats response and headers
//                 .prettyPrint(); // prints and formats only the body of the response
    }

    @Test
    public void parameters() {
       RestAssured
               .get(BASE_URL + "/posts?id=5")
               .prettyPeek()
               .then()
               .statusCode(200)
               .statusCode(equalTo(200)) //using matchers to verify status code
               .body("userId", contains(1)) //using matchers to verify response body
               .contentType("application/json; charset=utf-8");

    }

    @Test
    public void parameters2() {
        RestAssured
                .given()
                .params(Map.of("id", "5"))
                .get(BASE_URL + "/posts" )
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusCode(equalTo(200)) //using matchers to verify status code
                .body("userId", contains(1)) //using matchers to verify response body
                .contentType("application/json; charset=utf-8");

    }

    @Test
    public void nestedResponseBodyValidation() {
        RestAssured
                .given()
                .params(Map.of("name", "Leanne Graham"))
                .get(BASE_URL + "/users")
                .prettyPeek()
                .then()
                .rootPath("address.geo")
                    .body("lat", Matchers.contains("-37.3159"));

    }
}
