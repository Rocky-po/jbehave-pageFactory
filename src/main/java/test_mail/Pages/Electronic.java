package test_mail.Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by rocky-po on 28.06.17.
 */
public class Electronic {

    private WebDriver driver;

    @FindBy(className = "catalog-menu_js_inited")
    private
    List<WebElement> productTypeList;

    public Electronic(WebDriver driver){
        this.driver = driver;
    }

    public void goToProductListPage(String product) {
        boolean finded = false;
        outer:
        for (WebElement element : productTypeList) {
            List<WebElement> typesProducts = element.findElements(By.xpath("./div"));
            for (WebElement typeProducts : typesProducts) {
                WebElement namedTypeProducts = typeProducts.findElement(By.xpath("./div/a"));
                if (product.equals(namedTypeProducts.getText())) {
                    namedTypeProducts.click();
                    finded = true;
                    break outer;
                }
            }
            if (!finded)
                Assert.fail("Не найдет нужный элемент с списке типов продуктов");
        }
    }
}
