package test_mail.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by rocky-po on 28.06.17.
 */
public class Home {

    private WebDriver driver;

    @FindBy(linkText = "Маркет")
    private WebElement marketLink;

    public Home(WebDriver driver){
        this.driver = driver;
    }

    public void goToMarketPage(){
        marketLink.click();
    }
}
