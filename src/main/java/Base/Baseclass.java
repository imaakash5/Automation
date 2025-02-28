package Base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class Baseclass {

	    protected WebDriver driver;
	    protected ExtentReports extent;
	    protected ExtentTest test;
	    
	    
	    @BeforeClass
	    public void setup() {
	    	System.setProperty("webDriver.chrome.driver","/Users/aakash/Downloads/chromedriver-mac-arm64/chromedriver");
	    	driver = new ChromeDriver();
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(12));
	        ExtentSparkReporter reporter = new ExtentSparkReporter("ExtentReport.html");
	        extent = new ExtentReports();
	        extent.attachReporter(reporter);
	    }

	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	        if (extent != null) {
	            extent.flush();
	        }
	    }
	}
