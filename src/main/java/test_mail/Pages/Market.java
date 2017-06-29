package test_mail.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by rocky-po on 28.06.17.
 */
public class Market {

    private WebDriver driver;

    @FindBy(linkText = "Электроника")
    private WebElement electronicLink;

    public Market(WebDriver driver){
        this.driver = driver;
    }

    public void goToElectronicPage() {
        electronicLink.click();
    }
}
