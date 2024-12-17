import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.*;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;


public class CountryAPITestUnhappyPath {
    private static RequestSpecification request;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://restcountries.com/v3.1/";
        request = RestAssured.given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON);
    }

    @Test
    public void getCountryByNotExactFullName_returnNotFound(){
        request
                .when()
                .log().all()
                .get("name/Congo?fullText=true")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getCountryByInvalidCode_returnBadRequest(){
        request
                .when()
                .log().all()
                .get("alpha/coli")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400);
    }
}
