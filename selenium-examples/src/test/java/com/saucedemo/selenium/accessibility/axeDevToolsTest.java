package com.saucedemo.selenium.accessibility;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import static com.deque.html.axedevtools.matchers.IsAccessible.isAxeClean;
import static org.hamcrest.MatcherAssert.assertThat;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axedevtools.selenium.AxeDriver;
import com.deque.html.axedevtools.selenium.AxeSelenium;
import com.deque.html.axedevtools.selenium.reporter.AxeReportingOptions;
import com.saucedemo.selenium.accessibility.DequeAxeTest.SauceTestWatcher;

public class axeDevToolsTest {
	public RemoteWebDriver driver;
    AxeDriver axeDriver;
    private AxeReportingOptions _reportOptions = new AxeReportingOptions();

	
	@RegisterExtension
	public SauceTestWatcher watcher = new SauceTestWatcher();

	@BeforeEach
	public void setup(TestInfo testInfo) throws MalformedURLException {
	    ChromeOptions options = new ChromeOptions();
	    options.setPlatformName("Windows 10");
	    options.setBrowserVersion("latest");

     Map<String, Object> sauceOptions = new HashMap<>();
	    sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
	    sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
	    sauceOptions.put("name", testInfo.getDisplayName());

	    options.setCapability("sauce:options", sauceOptions);
	    URL url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");

	    driver = new RemoteWebDriver(url, options);
	    axeDriver = new AxeDriver(driver);
	}
	
	@DisplayName("Deque Axe DevTools Test With Selenium")
    @Test
    public void accessibilityTest() {
        driver.navigate().to("http://abcdcomputech.dequecloud.com/");

        assertThat(axeDriver, isAxeClean().logResults(_reportOptions.uiState("ABC")));

        
        
	}
	
	
	public class SauceTestWatcher implements TestWatcher {
        @Override
        public void testSuccessful(ExtensionContext context) {
            driver.executeScript("sauce:job-result=passed");
            driver.quit();
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            driver.executeScript("sauce:job-result=failed");
            driver.quit();
        }
    }

}
