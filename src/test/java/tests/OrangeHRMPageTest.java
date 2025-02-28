package tests;

import Base.Baseclass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.OrangeHRMPage;

public class OrangeHRMPageTest extends Baseclass {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"Admin", "admin123", true},   // Valid credentials
                {"InvalidUser", "invalidPass", false} // Invalid credentials
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean isValid) throws InterruptedException {
        // Create an object of OrangeHRMPage
        String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
        OrangeHRMPage orangeHRMPage = new OrangeHRMPage(driver, url);

        // Open the OrangeHRM login page
        test = extent.createTest("OrangeHRM Login Test for " + username);

        // Perform login
        boolean loginSuccess = orangeHRMPage.login(username, password);

        // Validate login based on expected result
        if (isValid) {
            if (loginSuccess) {
                test.pass("Login test passed for valid credentials.");
            } else {
                test.fail("Login failed for valid credentials.");
            }
        } else {
            if (!loginSuccess && orangeHRMPage.isErrorDisplayed()) {
                test.pass("Error displayed correctly for invalid credentials.");
            } else {
                test.fail("Error not displayed for invalid credentials.");
            }
        }
    }
}


