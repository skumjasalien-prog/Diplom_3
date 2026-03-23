package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопки хедера/главной
    private final By loginToAccountButton = By.xpath("//button[.='Войти в аккаунт']");
    // Ссылка личного кабинета в хедере
    private final By personalAccountButton = By.xpath("//a[@href='/account']");

    // Вкладки конструктора (div-элементы с текстом вкладки)
    private final By bunsTab = By.xpath("//div[contains(@class,'tab_tab') and .//span[normalize-space(.)='Булки']]");
    private final By saucesTab = By.xpath("//div[contains(@class,'tab_tab') and .//span[normalize-space(.)='Соусы']]");
    private final By fillingsTab = By.xpath("//div[contains(@class,'tab_tab') and .//span[normalize-space(.)='Начинки']]");
    private final By modalOverlay = By.cssSelector(".Modal_modal_overlay__x2ZCr");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной")
    public LoginPage clickLoginToAccount() {
        driver.findElement(loginToAccountButton).click();
        return new LoginPage(driver);
    }

    @Step("Нажать кнопку 'Личный кабинет'")
    public LoginPage clickPersonalAccount() {
        driver.findElement(personalAccountButton).click();
        return new LoginPage(driver);
    }

    @Step("Проверить, что кнопка 'Личный кабинет' видна в хедере")
    public boolean isPersonalAccountButtonVisible() {
        return driver.findElement(personalAccountButton).isDisplayed();
    }

    @Step("Перейти во вкладку 'Булки'")
    public void openBunsTab() {
        clickTabWhenReady(bunsTab);
        wait.until(d -> isCurrentTab("Булки"));
    }

    @Step("Перейти во вкладку 'Соусы'")
    public void openSaucesTab() {
        clickTabWhenReady(saucesTab);
        wait.until(d -> isCurrentTab("Соусы"));
    }

    @Step("Перейти во вкладку 'Начинки'")
    public void openFillingsTab() {
        clickTabWhenReady(fillingsTab);
        wait.until(d -> isCurrentTab("Начинки"));
    }

    private void clickTabWhenReady(By tabLocator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tabLocator));
        tab.click();
    }

    private By currentTabText(String tabText) {
        return By.xpath("//div[contains(@class,'tab_tab_type_current')]//span[normalize-space(.)='" + tabText + "']");
    }

    private boolean isCurrentTab(String tabText) {
        return !driver.findElements(currentTabText(tabText)).isEmpty();
    }

    @Step("Проверить, что активна вкладка 'Булки'")
    public boolean isBunsTabActive() {
        return isCurrentTab("Булки");
    }

    @Step("Проверить, что активна вкладка 'Соусы'")
    public boolean isSaucesTabActive() {
        return isCurrentTab("Соусы");
    }

    @Step("Проверить, что активна вкладка 'Начинки'")
    public boolean isFillingsTabActive() {
        return isCurrentTab("Начинки");
    }
}

