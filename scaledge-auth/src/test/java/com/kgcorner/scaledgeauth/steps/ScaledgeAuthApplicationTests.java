package com.kgcorner.scaledgeauth.steps;

import com.kgcorner.scaledgeauth.AuthServiceTestConf;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
		glue="com.kgcorner.scaledgeauth.steps",
		format = {
				"pretty",
				"html:target/cucumber-reports/cucumber-pretty",
				"json:target/cucumber-reports/CucumberTestReport.json",
				"rerun:target/cucumber-reports/rerun.txt"
		}
)
public class ScaledgeAuthApplicationTests {
}

