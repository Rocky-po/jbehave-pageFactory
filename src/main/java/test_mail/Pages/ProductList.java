package test_mail.Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rocky-po on 28.06.17.
 */
public class ProductList {

    private WebDriver driver;

    @FindBy(xpath = "//div[@class = 'n-filter-panel-aside__content']/div[4]/div[2]/div/div[1]/div")
    private List<WebElement> manufacturesList;

    @FindBy(id = "glf-pricefrom-var")
    private WebElement filterLowPrice;

    @FindBy(id = "glf-priceto-var")
    private WebElement filterHighPrice;

    @FindBy(className = "button_action_n-filter-apply")
    private WebElement filterOkButton;

    @FindBy(className = "snippet-cell_js_inited")
    private List<WebElement> productList;

    public ProductList(WebDriver driver){
        this.driver = driver;
    }

    public void addManufactureFilter(String filter){
        boolean finded = false;
        for (WebElement element : manufacturesList) {
            WebElement namedElement = element.findElement(By.xpath("./a/span/label"));
            if (filter.equals(namedElement.getText())){
                namedElement.findElement(By.xpath("./..")).click();
                finded = true;
                break;
            }
        }
        if (!finded)
            Assert.fail("Не найдет нужный производитель");
    }

    public void inputFilterLowPrice(String price){
        filterLowPrice.clear();
        filterLowPrice.sendKeys(price);
    }

    public void inputFilterHighPrice(String price){
        filterHighPrice.clear();
        filterHighPrice.sendKeys(price);
    }

    public void clickFilterOkButton(){
        filterOkButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            System.out.println("Упали при ожидании");
        }
    }

    public void checkManufacturer(String manufacturer){
        for (WebElement product : productList) {
            assertTrue(product.findElement(By.xpath("./a[3]/h4")).getText()
                    .contains(manufacturer));
        }
    }

    public void checkPrice(String price){
        for (WebElement product: productList) {
            int lowProductPrice = Integer.parseInt(product
                    .findElement(By.xpath("./div[3]/div[1]/span")).getText()
                    .replaceAll(" ", "").replace("руб.", ""));
            int highProductPrice = Integer.parseInt(product
                    .findElement(By.xpath("./div[3]/div[2]/span")).getText()
                    .replaceAll(" ", "").replace("руб.", ""));
            int expectedPrice = Integer.parseInt(price);
            if (expectedPrice < lowProductPrice || expectedPrice > highProductPrice){
                Assert.fail("Фильтрация прошла плохо, фильтруемой цены нет в диапазоне цен" +
                        "ожидаемая цена - " + expectedPrice +
                        "нижняя действительная цена - " + lowProductPrice +
                        "верхняя действительная цена - " + highProductPrice);
            }
        }
    }

    public String rememberFirstProductName() {
        WebElement firstElement = productList.get(0);
        return firstElement.findElement(By.xpath("./a[3]/h4")).getText();
    }

    public void goToViewFirstProduct(){
        productList.get(0).findElement(By.xpath("./a[2]")).click();
    }
}