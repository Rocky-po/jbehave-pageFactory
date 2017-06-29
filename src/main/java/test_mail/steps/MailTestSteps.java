package test_mail.steps;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;
import test_mail.Pages.*;

public class MailTestSteps {

    private WebDriver driver;
    private String productName;

    private Home homePage;
    private Market marketPage;
    private Electronic electronicPage;
    private ProductList productListPage;
    private ProductView productViewPage;

    @BeforeStories
    public void beforeStories() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("marionette", true);
        driver = new ChromeDriver();
        initPages();
    }

    @BeforeStory
    public void beforeStory() {
        System.out.println("первая история");
    }

    @Given("I am on yandex page")
    @Step("I am on yandex page")
    public void goToYandexPage() {
        driver.get("https://yandex.ru/");
        driver.manage().window().maximize();
    }

    @When("I go to the market page")
    @Step("I go to the market page")
    public void goToMarketPage() {
        homePage.goToMarketPage();
    }

    @When("I go to the electronic page")
    @Step("I go to the electronic page")
    public void goToElectronicPage() {
        marketPage.goToElectronicPage();
    }

    @When("I go to the <product> page")
    @Step("I go to the <product> page")
    public void goToProductPage(@Named("product") String product) {
        electronicPage.goToProductListPage(product);
    }

    @When("I input low <lowPrice> I will search")
    @Step("I input low <lowPrice> I will search")
    public void inputLowFilterPrice(@Named("lowPrice") String lowPrice) {
        productListPage.inputFilterLowPrice(lowPrice);
    }

    @When("I input high <highPrice> I will search")
    @Step("I input high <highPrice> I will search")
    public void inputHighFilterPrice(@Named("highPrice") String highPrice) {
        productListPage.inputFilterHighPrice(highPrice);
    }

    @When("I choice <manufacture>")
    @Step("I choice <manufacture>")
    public void choiceManufacture(@Named("manufacture") String manufacture) {
        productListPage.addManufactureFilter(manufacture);
    }

    @When("I click on apply button")
    @Step("I click on apply button")
    public void clickOkButton() {
        productListPage.clickFilterOkButton();
    }

    @When("I remember the first element")
    @Step("I remember the first element")
    public void rememberNameFirstElement(){
        productName = productListPage.rememberFirstProductName();
    }

    @When("I go to view this product")
    @Step("I go to view this product")
    public void goToProductViewPage() {
        productListPage.goToViewFirstProduct();
    }

    @Then("I see that name this product equals remember name")
    @Step("I see that name this product equals remember name")
    public void checkProductTitle() {
        productViewPage.checkCorrectTitleProduct(productName);
    }

    @AfterStory(uponGivenStory = true)
    public void afterStory() {
        driver.close();
    }

    @AfterStories
    public void afterStories() {
        System.out.println("А завершение интересно работает?");
        driver.quit();
    }

    private void initPages() {
        homePage = PageFactory.initElements(driver, Home.class);
        marketPage = PageFactory.initElements(driver, Market.class);
        electronicPage = PageFactory.initElements(driver, Electronic.class);
        productListPage = PageFactory.initElements(driver, ProductList.class);
        productViewPage = PageFactory.initElements(driver, ProductView.class);
        System.out.println("Страницы проинициализировали - " + homePage);
    }
}
