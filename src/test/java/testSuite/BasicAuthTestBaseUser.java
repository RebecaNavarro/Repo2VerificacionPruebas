package testSuite;

import config.ApiConfigUser;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Base64;
import java.util.Date;

public class BasicAuthTestBaseUser {
    public RequestInfo request = new RequestInfo();
    public Response response;
    public String auth="";
    public String valueAuth="";
    public JSONObject body = new JSONObject();

    @BeforeEach
    public void before() {
        body.put("Email",new Date().getTime()+"@upb2028.com");
        body.put("FullName","upb1");
        body.put("Password","12345!");

        auth="Authorization";
        valueAuth="Basic "+ Base64.getEncoder().encodeToString((body.get("Email")+":"+body.get("Password")).getBytes());

    }
}
