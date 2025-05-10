package kavindraoshan.PagObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import kavindraoshan.AbstractComponents.AbstractComponent;

public class ProductCateloguePage extends AbstractComponent {
	
	WebDriver driver;
	
	public ProductCateloguePage(WebDriver driver) {
		
		//Initialize
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	 
	//PageFactory Method
	@FindBy(css =".card")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productsBy = By.cssSelector(".card");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList() {
		
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByNames(String productName) {
		
		WebElement productToSelect = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL")).findFirst().orElse(null);
	
		return productToSelect;
		
	}
	
	public void addProductToCart(String productName) throws InterruptedException {
		
		Thread.sleep(5000);
		WebElement prod = getProductByNames(productName);
		//Click Add To Cart Button
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
				
	}


}
