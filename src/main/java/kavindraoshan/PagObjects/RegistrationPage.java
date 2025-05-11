package kavindraoshan.PagObjects;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import kavindraoshan.AbstractComponents.AbstractComponent;

import java.util.Random;

public class RegistrationPage extends AbstractComponent {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userMobile")
    WebElement userMobile;

    @FindBy(css = "select[formcontrolname='occupation']")
    WebElement occupationDropdown;

    @FindBy(css = "input[formcontrolname='gender'][value='Male']")
    WebElement genderMale;

    @FindBy(css = "input[formcontrolname='gender'][value='Female']")
    WebElement genderFemale;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "confirmPassword")
    WebElement confirmPassword;

    @FindBy(css = "input[formcontrolname='required']")
    WebElement ageCheckbox;

    @FindBy(id = "login")
    WebElement registerButton;

    @FindBy(css = "a[routerlink*='/auth/register']")
    WebElement registerLink;

    @FindBy(css = ".headcolor")
    WebElement registerHeader;

    public void enterFirstName(String fName) {
        firstName.sendKeys(fName);
    }

    public void enterLastName(String lName) {
        lastName.sendKeys(lName);
    }

    public void enterEmail(String email) {
        userEmail.sendKeys(email);
    }

    public void enterPhoneNumber(String phone) {
        userMobile.sendKeys(phone);
    }

    public void selectOccupation(String occupation) {
        Select select = new Select(occupationDropdown);
        select.selectByVisibleText(occupation);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            genderMale.click();
        } else if (gender.equalsIgnoreCase("Female")) {
            genderFemale.click();
        }
    }

    public void enterPassword(String password) {
        userPassword.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPwd) {
        confirmPassword.sendKeys(confirmPwd);
    }

    public void checkAgeConfirmation() {
        if (!ageCheckbox.isSelected()) {
            ageCheckbox.click();
        }
    }

    public void clickRegister() {
        registerButton.click();
    }

    public void clickRegisterLink() {
        registerLink.click();
    }

    public void completeRegistration(String fName, String lName, String email, String phone, String occupation, String gender, String password, String confirmPwd) throws InterruptedException {
        enterFirstName(fName);
        enterLastName(lName);
        enterEmail(RandomString.make(2)+email);
        enterPhoneNumber(phone);
        selectOccupation(occupation);
        selectGender(gender);
        enterPassword(password);
        enterConfirmPassword(confirmPwd);
        checkAgeConfirmation();
        clickRegister();
    }

    public String verifyRegistrationSuccess() {
        return registerHeader.getText();

    }

}