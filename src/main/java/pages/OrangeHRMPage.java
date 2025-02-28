package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import ch.qos.logback.core.util.Duration;

public class OrangeHRMPage {
	 private WebDriver driver;

	    private By usernameField = By.name("username");
	    private By passwordField = By.name("password");
	    private By loginButton = By.xpath("//button[@type='submit']");
	    private By errorMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
	    By dropDownOption = By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']");
	    By logOutField = By.xpath("//a[contains(text(),'Logout')]");
	    By dashBoardElement = By.xpath("//div[@class='oxd-topbar-header']");
	    	
	

	    public OrangeHRMPage(WebDriver driver, String url) {
	        this.driver = driver;
	        driver.get(url);
	    }
	   
	    public boolean login(String username, String password) throws InterruptedException {
	        driver.findElement(usernameField).sendKeys(username);
	        driver.findElement(passwordField).sendKeys(password);
	        driver.findElement(loginButton).click();

	        // Wait for either dashboard or error message to appear
	        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));

	        try {
	            // Check if the dashboard appears (successful login)
	            wait.until(ExpectedConditions.presenceOfElementLocated(dashBoardElement));
	            logout();
	            return true; // Login successful
	        } catch (Exception e) {
	            // Check if the error message appears (failed login)
	            if (isErrorDisplayed()) {
	                System.out.println("Login failed: Invalid username or password.");
	                return false; // Login failed
	            }
	            throw e; // Unexpected issue
	        }
	    }
	    
	    public void logout() throws InterruptedException{
	    	 Thread.sleep(2000);
		    	WebElement dropDown = driver.findElement(dropDownOption);
	        	dropDown.click();
	        	 Thread.sleep(2000);	
	        	driver.findElement(logOutField).click();
	           }

	    public boolean isErrorDisplayed() {
	        return driver.findElement(errorMessage).isDisplayed();
	    }
	}
