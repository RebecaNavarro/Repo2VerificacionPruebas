package testSuite;

import config.ApiConfigUser;
import factoryRequest.FactoryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class CRUDUserTest extends BasicAuthTestBaseUser{

    @Test
    @DisplayName("Verify the create, update, delete user - todo.ly")
    public void createProject() {
        request.setUrl(ApiConfigUser.CREATE_USER)
                .setBody(body.toString());
        response = FactoryRequest.make("post").send(request);

        response.then().statusCode(200).body("Email",equalTo(body.get("Email")));

        body.put("FullName","cruduser");
        request.setUrl(ApiConfigUser.UPDATE_USER)
                .setHeaders(auth,valueAuth)
                .setBody(body.toString());
        response = FactoryRequest.make("put").send(request);

        response.then().statusCode(200).body("FullName",equalTo(body.get("FullName")));



        request.setUrl(ApiConfigUser.DELETE_USER);
        response = FactoryRequest.make("delete").send(request);

        response.then().statusCode(200).body("Email",equalTo(body.get("Email")));

    }

}

