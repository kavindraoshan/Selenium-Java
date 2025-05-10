package kavindraoshan.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import kavindraoshan.PagObjects.LandingPage;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://rahulshettyacademy.com/client");
		
		LandingPage landingpage = new LandingPage(driver);

		driver.findElement(By.id("userEmail")).sendKeys("kavindra@yopmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("test123");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card")));
		Thread.sleep(5000);
		
		List<WebElement> products = driver.findElements(By.cssSelector(".card"));
		
		WebElement productToSelect = products.stream().filter(product->
			product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL")).findFirst().orElse(null);
		
		//Click Add To Cart Button
		productToSelect.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//Handle Toast Message
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ng-animating")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		Thread.sleep(5000);

		//Click on Cart Button
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//Assert Item Added Here Returning Boolean Value
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase("ADIDAS ORIGINAL"));

		Assert.assertTrue(match);
		
		//Click CheckOut Button
		driver.findElement(By.xpath("//button[text()='Checkout']")).click();
		
		//Handle Dropdown
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector(".input[placeholder='Select Country']")),"Sr").build().perform();
		
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
		
		driver.quit();
	}

}
