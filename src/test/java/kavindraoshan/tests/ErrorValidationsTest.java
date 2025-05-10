package kavindraoshan.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import kavindraoshan.PagObjects.CartPage;
import kavindraoshan.PagObjects.ProductCateloguePage;
import kavindraoshan.TestComponents.BaseTest;
import kavindraoshan.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws InterruptedException, IOException {

		landingpage.loginApplication("kavra@yopmail.com", "test123");
		AssertJUnit.assertEquals(landingpage.getErrorMessage(), "Incorrect email or password.");

	}

	@Test
	public void ProductErrorValidation() throws InterruptedException, IOException {

		String productName = "ZARA COAT 33";
		ProductCateloguePage productCatologue = landingpage.loginApplication("anshika@gmail.com", "Iamking@000");

		List<WebElement> products = productCatologue.getProductList();
		productCatologue.addProductToCart(productName);
		CartPage cartPage = productCatologue.goToCartPage();

		// Assert Item Added Here Returning Boolean Value
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertFalse(match);
	}

}
