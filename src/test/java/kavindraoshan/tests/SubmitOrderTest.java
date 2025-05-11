package kavindraoshan.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import kavindraoshan.PagObjects.CartPage;
import kavindraoshan.PagObjects.CheckoutPage;
import kavindraoshan.PagObjects.ConfirmationPage;
import kavindraoshan.PagObjects.OrderPage;
import kavindraoshan.PagObjects.ProductCateloguePage;
import kavindraoshan.PagObjects.RegistrationPage; // Import RegistrationPage
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

public class SubmitOrderTest extends BaseTest {

	/**
	 * Test to submit an order after logging in.
	 *
	 * @param input HashMap containing test data such as email, password, and product name.
	 * @throws InterruptedException if thread sleep is interrupted.
	 * @throws IOException if an I/O error occurs.
	 */
	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException {

		// Login to the application
		ProductCateloguePage productCatologue = landingpage.loginApplication(input.get("email"), input.get("password"));

		// Fetch the list of products and add the specified product to the cart
		List<WebElement> products = productCatologue.getProductList();
		productCatologue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatologue.goToCartPage();

		// Verify if the product is added to the cart
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);

		// Proceed to checkout and place the order
		CheckoutPage checkOutPage = cartPage.goToCheckOut();
		checkOutPage.selectCountry("Sri");
		ConfirmationPage confirmationpage = checkOutPage.submitOrder();

		// Verify the confirmation message
		String confirmMessage = confirmationpage.getConfirmationMessage();
		AssertJUnit.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

		// Close all browsers
		tearDown();
	}

	/**
	 * Test to verify if the placed order appears in the order history.
	 *
	 * @throws InterruptedException if thread sleep is interrupted.
	 */
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest() throws InterruptedException {
		String productName = "ADIDAS ORIGINAL";

		// Login to the application
		ProductCateloguePage productCatologue = landingpage.loginApplication("kavindra@yopmail.com", "test123");

		// Navigate to the orders page and verify the product is displayed
		OrderPage orderPage = productCatologue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}

	/**
	 * Test to register a new user and submit an order.
	 *
	 * @param input HashMap containing test data such as first name, last name, email, phone, etc.
	 * @throws InterruptedException if thread sleep is interrupted.
	 * @throws IOException if an I/O error occurs.
	 */
	@Test(dataProvider = "getRegistrationData", groups = {"Purchase"})
	public void registerAndSubmitOrder(HashMap<String, String> input) throws InterruptedException, IOException {

		// Register a new user
		RegistrationPage registrationPage = new RegistrationPage(driver);

		// Navigate to the registration page
		registrationPage.clickRegisterLink();

		registrationPage.completeRegistration(
				input.get("firstName"),
				input.get("lastName"),
				input.get("email"),
				input.get("phone"),
				input.get("occupation"),
				input.get("gender"),
				input.get("password"),
				input.get("confirmPassword")
		);

		Assert.assertEquals(registrationPage.verifyRegistrationSuccess(), "Account Created Successfully");

		// Close all browsers
		tearDown();
	}

	/**
	 * Data provider to supply test data for the tests.
	 *
	 * @return Object[][] containing test data.
	 * @throws IOException if an I/O error occurs while reading the JSON file.
	 */

	/*
	@DataProvider
	public Object[][] getData() throws IOException {

		// Read test data from JSON file and convert it to a list of HashMaps
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\kavindraoshan\\Data\\PurchaseOrder.json");
		return new Object[][]{{data.get(0)}, {data.get(1)}};
	}
	*/

	@DataProvider
	public Object[][] getData() throws IOException {

		// Read test data from JSON file and convert it to a list of HashMaps
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\kavindraoshan\\Data\\PurchaseOrder.json");

		// Create an Object[][] array dynamically based on the size of the data list
		Object[][] testData = new Object[data.size()][1];
		for (int i = 0; i < data.size(); i++) {
			testData[i][0] = data.get(i);
		}

		return testData;
	}

	@DataProvider
	public Object[][] getRegistrationData() throws IOException {
		// Read test data from JSON file and convert it to a list of HashMaps
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\kavindraoshan\\Data\\RegistrationData.json");

		// Create an Object[][] array dynamically based on the size of the data list
		Object[][] testData = new Object[data.size()][1];
		for (int i = 0; i < data.size(); i++) {
			testData[i][0] = data.get(i);
		}

		return testData;
	}
}