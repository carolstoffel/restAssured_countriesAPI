import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.*;
import org.junit.runner.Request;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;
import static org.hamcrest.Matchers.*;



public class CountryAPITest {
    //private static RequestSpecification request;

    @BeforeClass  // Use TestNG annotation for class-level setup
    public void setUp() {
        RestAssured.baseURI = "https://restcountries.com/v3.1/";
        //request = RestAssured;
        //request = RestAssured.given();
        //request = RestAssured.given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                //.contentType(ContentType.JSON);
    }
    @Test
    public void getCountryByNotExactName_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("/name/brasil")
                .then()
                //.log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Brazil"))
                .and()
                .body("results", hasSize(greaterThan(0)));
    }

    @Test
    public void getCountryByName_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("/name/brazil")
                .then()
                //.log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Brazil"))
                .and()
                .body("results", hasSize(greaterThan(0)));

    }


    @Test
    public void getCountryByExactFullName_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("name/Republic of the Congo?fullText=true")
                .then()
                //.log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Republic of the Congo"))
                .and()
                .body("results", hasSize(greaterThan(0)));
    }
    @Test
    public void getCountryByValidCode_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("alpha/col")
                .then()
                //.log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Colombia"))
                .and()
                .body("results", hasSize(greaterThan(0)));
    }
    @Test
    public void getListOfCountriesByCode_oneCode_returnOk(){
        RestAssured.given()
                .when()
                .log().all()
                .get("alpha?codes=170")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Colombia"))
                .and()
                .body("results", hasSize(equalTo(1)));

    }
    @Test
    public void getListOfCountriesByCode_manyCodes_returnOk() {
        //Response response =
        RestAssured.given()
                .when()
                .log().all()
                .get("alpha?codes=170,no,est,pe")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Colombia"))
                .and()
                .body("results", hasSize(equalTo(4)))
                .and()
                .body("name.common", contains("Colombia", "Norway", "Estonia", "Peru"))
                .log().all();
        //.extract()
        //.response();
        /*
        example to save the response in a var and assert:
        // Extract the list of countries as maps
        List<Map<String, Object>> countries = response.jsonPath().getList("");
        List<String> expectedNames = List.of("Colombia", "Norway", "Estonia", "Peru");

        for (int i = 0; i < countries.size(); i++) {
            Map<String, Object> name = (Map<String, Object>) countries.get(i).get("name");
            String commonName = (String) name.get("common");
            assertEquals(expectedNames.get(i), commonName, "Country name does not match!");
        }*/
    }
    @Test
    public void getListOfCountriesByCurrency_returnOk(){
        RestAssured.given()
                .when()
                .log().all()
                .get("currency/cop")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Colombia"))
                .and()
                .body("results", hasSize(equalTo(1)));
        }
    @Test
    public void getListOfCountriesByLanguage_returnOK() {
        RestAssured.given()
                .when()
                .log().all()
                .get("lang/spanish")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name.common", hasItems(
                        "Argentina", "Bolivia", "Chile", "Colombia", "Costa Rica", "Cuba",
                        "Dominican Republic", "Ecuador", "El Salvador", "Equatorial Guinea",
                        "Guatemala", "Honduras", "Mexico", "Nicaragua", "Panama",
                        "Paraguay", "Peru", "Spain", "Uruguay", "Venezuela"))
                .body("results", hasSize(greaterThan(19)));
    }
    @Test
    public void getCapitalCityByCountry_returnOk(){
        RestAssured.given()
                .when()
                .log().all()
                .get("capital/tallinn")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name.common", equalTo("Estonia"))
                .and()
                .body("results", hasSize(equalTo(1)));
    }
    @Test
    public void getListOfCountriesByRegion_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("region/South America")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name.common", hasItems(
                        "Argentina", "Bolivia", "Brazil", "Chile", "Colombia", "Ecuador",
                        "Guyana", "Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela"
                ))
                .body("name.common.size()", equalTo(12));
    }
    @Test
    public void getListOfCountriesBySubregion_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("region/Northern Europe")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                // Assert that the list contains the expected countries
                .body("name.common", hasItems(
                        "Norway", "Guernsey", "Åland Islands", "Svalbard and Jan Mayen",
                        "Sweden", "Jersey", "Faroe Islands", "Iceland", "Latvia",
                        "Denmark", "Isle of Man", "Estonia", "Lithuania", "United Kingdom",
                        "Finland", "Ireland"
                ))
                // Assert the correct number of countries (16)
                .body("name.common.size()", equalTo(16));
    }
    @Test
    public void getCountryByTranslationName_returnOk() {
            RestAssured.given()
                    .when()
                    .log().all()
                    .get("translation/alemania")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("[0].name.common", equalTo("Germany"))
                    .and()
                    .body("results", hasSize(equalTo(1)));
    }
    @Test
    public void getCountriesByFilteredResponse_returnOk() {
        RestAssured.given()
                .when()
                .log().all()
                .get("all?fields=name,capital,currencies")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .and()
                .body("[0]", hasKey("name"))
                .and()
                .body("[0]", hasKey("capital"))
                .and()
                .body("[0]", hasKey("currencies"));
    }
}