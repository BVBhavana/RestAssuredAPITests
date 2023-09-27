package com.way2.automation.steps;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.junit.annotations.Concurrent;

@Concurrent(threads = "4x")
@RunWith(CucumberWithSerenity.class)

@CucumberOptions(features = "src/test/resources/feature/Way2Automation", strict = true, tags = {
		"@way2Automation" }, glue = "com.way2.automation.steps")

public class TestWay2AutomationRunner {

}
