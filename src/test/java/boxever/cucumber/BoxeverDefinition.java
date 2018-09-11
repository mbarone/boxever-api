package boxever.cucumber;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BoxeverDefinition {

    public BoxeverDefinition() {
        RestAssured.baseURI = BoxeverConfiguration.GIST_API_URI;
    }

    /**
     * This method return the list of all public gists
     */
    public void requestPublicGists() {
        Response response =
                given().
                        contentType("application/json").
                        when().
                        get("/gists/public").
                        then().
                        statusCode(200).
                        extract().response();
    }

    /**
     * This method return the list of all public gists
     * @param id: The gist id to remove
     */
    public void deleteGistByID(String id){
        Response response =
                given().
                        authentication().oauth2(BoxeverConfiguration.ACCESS_TOKEN).
                        contentType("application/json").
                        when().
                        delete("/gists/"+id).
                        then().
                        statusCode(204).
                        extract().response();
    }

    /**
     * This method check that the gist with id doesn't exists
     * @param id: The gist id to search
     */
    public void requestNotExistingGistsByID(String id){
        Response res =
                given().
                        contentType("application/json").
                        when().
                        get("/gists/"+id).
                        then().
                        statusCode(404).
                        extract().response();
    }

    /**
     * This method return the gist with the id
     * @param id: The gist id to remove
     * @return Response
     */
    public Response requestGistsByID(String id){
        return
                given().
                        contentType("application/json").
                        when().
                        get("/gists/"+id).
                        then().
                        statusCode(200).
                        extract().response();
    }

    /**
     * This method return JSON Object of a json file
     * @param jsonFileName: The json file name
     * @return the JSONObject element
     */
    public static JSONObject readJsonFromResourceFile(String jsonFileName) throws IOException,JSONException {
        InputStream inputStream = new FileInputStream("src/test/resources/json/" + jsonFileName);
        String jsonTxt = IOUtils.toString(inputStream, "UTF-8");
        return new JSONObject(jsonTxt);
    }

    /**
     * This method return the REsponse of a creation of a Gist
     * @param json: The jsonObject to pass as body in the API call
     * @return the Response
     */
    public static Response createOneGist(JSONObject json){
        return given().
                authentication().oauth2(BoxeverConfiguration.ACCESS_TOKEN).
                contentType("application/json").
                body(json.toString()).
                when().
                post("/gists").
                then().
                statusCode(201).
                extract().response();
    }

    /**
     * This method creates a Gist
     * @param jsonBody: The json body to pass in the API call body
     */
    public void createGist(JSONObject jsonBody ) {
        Response response =
                given().
                        authentication().oauth2(BoxeverConfiguration.ACCESS_TOKEN).
                        contentType("application/json").
                        body(jsonBody.toString()).
                        when().
                        post("/gists").
                        then().
                        statusCode(201).
                        extract().response();
        response.then().body("public", equalTo(true));
    }

    /**
     * This method creates a gist
     * @param jsonBody: The json body to pass in the API call body
     * @return the Gist id
     */
    public String createGistID(JSONObject jsonBody ) {
        String id =
                given().
                        authentication().oauth2(BoxeverConfiguration.ACCESS_TOKEN).
                        contentType("application/json").
                        body(jsonBody.toString()).
                        when().
                        post("/gists").
                        then().
                        statusCode(201).
                        extract().path("id");
        return id;
    }


}
