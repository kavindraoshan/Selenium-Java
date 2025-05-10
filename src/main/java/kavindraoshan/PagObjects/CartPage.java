package kavindraoshan.PagObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import kavindraoshan.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		// Initialize
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cartSection h3")
	List<WebElement> cartProducts;
	
	@FindBy(xpath="//button[text()='Checkout']")
	WebElement checkOutButton;

	public Boolean VerifyProductDisplay(String productName) throws InterruptedException {
		
		Thread.sleep(5000);
		Boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase("ADIDAS ORIGINAL"));
		return match;
	}

	public CheckoutPage goToCheckOut() {

		checkOutButton.click();
		CheckoutPage checkOutPage = new CheckoutPage(driver);
		return checkOutPage;
		
	}
}
