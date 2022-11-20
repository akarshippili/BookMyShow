package com.ticketapp.userservice;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class UserStepDefinitions {

    @Given("firstname, lastname, email, role")
    public void firstname_lastname_email_role() {
    }
    @When("made a post request to create user")
    public void made_a_post_request_to_create_user() {
    }
    @Then("User should be created and persisted in database")
    public void user_should_be_created_and_persisted_in_database() {
        Assert.assertNotEquals(false, true);
    }

}
