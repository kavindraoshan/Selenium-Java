package kavindraoshan.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import kavindraoshan.PagObjects.CartPage;
import kavindraoshan.PagObjects.CheckoutPage;
import kavindraoshan.PagObjects.ConfirmationPage;
import kavindraoshan.PagObjects.OrderPage;
import kavindraoshan.PagObjects.ProductCateloguePage;
import kavindraoshan.TestComponents.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SubmitOrderTest extends BaseTest {
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException {

		ProductCateloguePage productCatologue = landingpage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = productCatologue.getProductList();
		productCatologue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatologue.goToCartPage();

		// Assert Item Added Here Returning Boolean Value
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);

		// Click CheckOut Button
		CheckoutPage checkOutPage = cartPage.goToCheckOut();
		checkOutPage.selectCountry("Sri");
		ConfirmationPage confirmationpage = checkOutPage.submitOrder();

		String confirmMessage = confirmationpage.getConfirmationMessage();
		AssertJUnit.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

		// Close all browsers
		tearDown();
	}

	// Assert Order is Place
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest() throws InterruptedException 
	{
		String productName = "ADIDAS ORIGINAL";
		ProductCateloguePage productCatologue = landingpage.loginApplication("kavindra@yopmail.com", "test123");
		OrderPage orderPage = productCatologue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
		
	}

	
	@DataProvider
	public Object[][] getData() throws IOException {
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\kavindraoshan\\Data\\PurchaseOrder.json");
		return new Object[] [] {{data.get(0)}, {data.get(1)}};
		
	}
	

}
