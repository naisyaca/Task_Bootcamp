import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.checkout.*;

public class Checkout {
    WebDriver driver;

     @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Java Project\\Bootcamp\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testCheckoutProduct() throws InterruptedException {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        Thread.sleep(2000);

        // Add product to cart
        Thread.sleep(1000);
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.goToCart();

        // Proceed to checkout
        Thread.sleep(2000);
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        // Fill checkout details
        Thread.sleep(3000);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCheckoutDetails("Naisya", "Najmi", "12345");
        checkoutPage.finishCheckout();

        // Verify confirmation message
        Thread.sleep(2500);
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        String confirmationMessage = confirmationPage.getConfirmationMessage();
        Assert.assertEquals(confirmationMessage, "Thank you for your order!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}