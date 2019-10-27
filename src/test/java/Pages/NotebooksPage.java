package Pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.element.TextInput;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NotebooksPage extends BasePage {
    @FindBy(xpath = "//div[contains(., 'Компьютерная техника')]/a/..")
    private Button categoryPc;

    @FindBy(xpath = "//li[contains(., 'Ноутбуки')]")
    private Button categoryNotebooks;

    @FindBy(xpath = "//input[@name='Цена от']")
    private TextInput priceF;

    @FindBy(xpath = "//input[@name='Цена до']")
    private TextInput priceT;

    @FindBy(xpath = "//span[contains(., 'Показывать по 48')]/button")
    private Button showBy;

    @FindBy(xpath = "//div[@class=\"popup__content\"][contains(., 'Показывать по 12')]/div/div[1]")
    private Button show12Result;

    @FindBy(xpath = "//a[contains(., 'по цене')]")
    private Button sortByPrice;

    @FindBy(xpath = "//div[@class='n-filter-applied-results__content preloadable i-bem preloadable_js_inited']/div/div[3]/div[4]/div/div/a")
    private Button getThirdFromTop;


    @FindBy(xpath = "//a[contains(., 'Характеристики')]")
    private Button characteristics;

    @FindBy(xpath = "//legend[contains(., 'Магазины')]/following-sibling::footer/button")
    private Button showAllShops;


    @Step("Устанавливаем категорию \"Ноутбуки\"")
    public void setCategory() {
        pageIsLoad();
        makeScreenshot();
        Assert.assertTrue("Не найден раздел \"Компьютерная техника\"", categoryPc.exists());
        categoryPc.click();
        pageIsLoad();
        Assert.assertTrue("Не найдена категория \"Ноутбуки\"", categoryNotebooks.exists());
        categoryNotebooks.click();
    }


    @Step("Устанавливаем цены от {0} до {1}")
    public void setPrice(String priceFrom, String priceTo) {
        Assert.assertTrue("Не найдено поле \"Цена от\" для фильтра", priceF.exists());
        priceF.click();
        priceF.sendKeys(priceFrom);

        Assert.assertTrue("Не найдено поле \"Цена до\" для фильтра", priceT.exists());
        priceT.click();
        priceT.sendKeys(priceTo);
    }

    @Step("Фильтр по производителям кроме {0}")
    public void setManufacturers(List<String> except) {
        Assert.assertTrue("Не найдено поле \"Показать всё\" для фильтра", showAllShops.exists());
        showAllShops.click();
        HashMap<String, Boolean> clicked = new HashMap<>();
        while (true) {
            AtomicBoolean finished = new AtomicBoolean(true);
            List<WebElement> elements = getWebElements(By.cssSelector("[data-zone-name=\"search-filter\"] [data-autotest-id=\"fesh\"] label"));
            try {
                elements.forEach(el -> {
                    try {
                        WebElement input = null;
                        String labelFor = el.getAttribute("for");
                        if (labelFor.equals("fesh-suggester")) {
                            return;
                        }
                        try {
                            input = el.findElement(By.cssSelector("input[type=checkbox]"));
                        } catch (Exception e) {
                            return;
                        }
                        if (input == null) {
                            return;
                        }
                        String name = input.getAttribute("name");
                        if (clicked.containsKey(name)) {
                            return;
                        }
                        clicked.put(name, true);
                        for (String shop : except) {
                            if (name.toLowerCase().contains(shop.toLowerCase())) {
                                return;
                            }
                        }
                        finished.set(false);
                        el.findElement(By.cssSelector("div")).click();
                        getWebDriver().executeScript("document.querySelector('[for=" + labelFor + "]').scrollIntoView({block: 'center'})");
                    } catch (Exception ignored) {
                    }
                });
            } catch (Exception ignored) {

            }
            if (finished.get()) {
                break;
            }
        }
    }

    @Step("Показываем по 12 элементов на странице")
    public void setParams(String rating) {
        Assert.assertTrue("Не найдена кнопка \"Показать по количеству страниц\"", showBy.exists());
        showBy.click();
        Assert.assertTrue("Не найдена кнопка \"Показать по 12\"", show12Result.exists());
        show12Result.click();

        goTo(getWebDriver().getCurrentUrl() + "&how=dprice");
        String element = "//input[@id='qrfrom_" + rating + "']/following-sibling::div";
        Button button = new Button(getWebDriver().findElement(By.xpath(element)));
        Assert.assertTrue("Отсутствует кнопка с рейтингом " + rating, button.exists());
        button.click();
    }

    @Step("Выбираем третью сверху модель производителя {0}")
    public void getThirdFromTop(String proisvoditel) {
        pageIsLoad();
        Assert.assertTrue("Отсутствует третий сверху элемент для производителя " + proisvoditel, getThirdFromTop.exists());
        getThirdFromTop.click();
        checkPage(proisvoditel);
        Assert.assertTrue("Отсутствует кнопка \"Характеристики\" для производителя " + proisvoditel, characteristics.exists());
        characteristics.click();
        makeScreenshot();
    }

    @Step("Проверяем что заголовок содержит производителя {0}")
    private void checkPage(String proisvoditel) {
        pageIsLoad();
        if (getChromeDriver().getWindowHandles().size() > 1) {
            for (String handle : getChromeDriver().getWindowHandles()) {
                getChromeDriver().switchTo().window(handle);
            }
        }
        pageIsLoad();
        TextBlock title = new TextBlock(getWebDriver().findElement(By.xpath("//h1[contains(., '" + proisvoditel + "')]")));
        Assert.assertTrue("Не найден заголовок для производителя " + proisvoditel, title.exists());
        Assert.assertTrue("Заголовок не содержит производителя " + proisvoditel, title.getText().contains(proisvoditel));
    }


    public CheckBox getManufacturerCheckBoxPath(String name) {
        pageIsLoad();
        String element = "//input[@name='Производитель " + name + "']/../div";
        CheckBox checkBox = new CheckBox(getWebDriver().findElement(By.xpath(element)));
        Assert.assertTrue("Отсутствует чекбокс для выбора производителя \"" + name + "\" в фильтре", checkBox.exists());
        return checkBox;
    }


}
