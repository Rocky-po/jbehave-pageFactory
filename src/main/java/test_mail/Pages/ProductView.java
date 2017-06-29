package test_mail.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertEquals;

/**
 * Created by rocky-po on 28.06.17.
 */
public class ProductView {

    private WebDriver driver;

    @FindBy(xpath = "//div[@class = 'n-title__text']/h1")
    private WebElement titleText;

    public ProductView(WebDriver driver){
        this.driver = driver;
    }

    public void checkCorrectTitleProduct(String title){
        assertEquals("assert that title product is correct", title,
                titleText.getText());
    }
}
