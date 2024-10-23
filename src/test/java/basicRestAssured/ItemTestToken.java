package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ItemTestToken {

    @Test
    @DisplayName("Verify Create Read Update Delete Item whit Token - Todo.ly")
    public void verifyCRUDProject(){

        Response tokenResponse = given()
                .auth()
                .preemptive()
                .basic("tareapostman@tarea.com","Hola1234")
                .log().all()
                .when()
                .get("https://todo.ly/api/authentication/token.json");

        tokenResponse.then()
                .statusCode(200)
                .log().all();

        String token = tokenResponse.then().extract().path("TokenString");

        JSONObject body = new JSONObject();
        body.put("Content","RebecaPruebaRESTJsonBody");

        // create
        Response response = given()
                .header("Token", token)
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/items.json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .log().all();

        int id = response.then().extract().path("Id");
        // read
        response = given()
                .header("Token", token)
                .log().all()
                .when()
                .get("https://todo.ly/api/items/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .log().all();
        // update
        body.put("Content","RebecaPruebaUpdateRESTJsonBody");
        response = given()
                .header("Token", token)
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/items/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("RebecaPruebaUpdateRESTJsonBody"))
                .log().all();
        // delete
        response = given()
                .header("Token", token)
                .log().all()
                .when()
                .delete("https://todo.ly/api/items/"+id+".json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo("RebecaPruebaUpdateRESTJsonBody"))
                .body("Deleted",equalTo(true))
                .log().all();
    }

}
