package boxever.cucumber.steps;

import boxever.cucumber.BoxeverDefinition;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import java.io.IOException;

public class BoxeverSteps {

    BoxeverDefinition service;
    private static String gistId = null;


    @When("^I access the gist endpoint$")
    public void iAccessTheBoxeverEndpoint() throws Throwable {
        service = new BoxeverDefinition();
    }

    @When("^I retrieve the gists current list$")
    public void iRetrieveTheGistCurrentList() throws Throwable {
        service.requestPublicGists();
    }

    @Then("^I create a new Gist with json (.*) successfully$")
    public void iCreateNewGist(String jsonFileName) throws IOException {
        JSONObject json = BoxeverDefinition.readJsonFromResourceFile(jsonFileName);
        service.createGist(json);
    }

    @And("^I have a gist already created with the json (.*)$")
    public void  iHaveGistAlreadyCreatedWithJson(String jsonFileName) throws IOException {
        JSONObject json = BoxeverDefinition.readJsonFromResourceFile(jsonFileName);
        gistId  = service.createGistID(json);
        Assert.assertEquals(true, gistId.length()>0);
    }

    @Then("^I get the gist and contains the expected values")
    public void iGetTheGist(){
        Response response = service.requestGistsByID(gistId);
        response.then().body("public", equalTo(true)).
                body("id", notNullValue());
    }

    @And("^I delete the gist")
    public void iDeleteTheGist(){
        service.deleteGistByID(gistId);
    }

    @Then("^the gist is deleted")
    public void theGistIsDeleted(){
        service.requestNotExistingGistsByID(gistId);

    }
}


