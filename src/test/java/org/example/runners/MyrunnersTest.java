package org.example.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty","html:target/cucumber","summary"}
        ,features = {"src/test/resources"}
        ,glue = {"org.example.stepdef"}
        ,snippets = CucumberOptions.SnippetType.CAMELCASE
        ,dryRun = false
        ,monochrome = true
        , publish = true
       ,tags = "@test"
)

public class MyrunnersTest {


}
