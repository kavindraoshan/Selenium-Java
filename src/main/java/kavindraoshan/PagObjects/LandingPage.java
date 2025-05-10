package kavindraoshan.PagObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import kavindraoshan.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		
		//Initialize
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//PageFactory Method
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement loginButton;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage; 
	
	public ProductCateloguePage loginApplication(String email, String password) throws InterruptedException {
		
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		loginButton.click();
		Thread.sleep(5000);
		ProductCateloguePage productCatologue = new ProductCateloguePage(driver);
		return productCatologue;
	}
	
	public void goToLandingPage() {
		driver.get("https://rahulshettyacademy.com/client");
	}

	public String getErrorMessage() {
		
		//Wait for Element to Appear
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}

}
