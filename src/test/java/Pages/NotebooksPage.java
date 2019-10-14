package Pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class NotebooksPage extends BasePage {
    @FindBy(xpath = "//div[contains(., 'Компьютерная техника')]/a/..")
    private Button categoryPc;

    @FindBy(xpath = "//li[contains(., 'Ноутбуки')]")
    private Button categoryNotebooks;

    @FindBy(xpath = "//input[@name='Цена от']")
    private TextInput priceFrom;

    @FindBy(xpath = "//input[@name='Цена до']")
    private TextInput priceTo;

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


    @Step("Устанавливаем категорию \"Ноутбуки\"")
    public void setCategory() {
        Assert.assertTrue("Не найден раздел \"Компьютерная техника\"", categoryPc.exists());
        categoryPc.click();
        Assert.assertTrue("Не найдена категория \"Ноутбуки\"", categoryNotebooks.exists());
        categoryNotebooks.click();
    }


    @Step("Устанавливаем цены от {0} до {1}")
    public void setPrice(String priceF, String priceT) {
        Assert.assertTrue("Не найдено поле \"Цена от\" для фильтра", priceFrom.exists());
        priceFrom.click();
        priceFrom.sendKeys(priceF);

        Assert.assertTrue("Не найдено поле \"Цена до\" для фильтра", priceTo.exists());
        priceTo.click();
        priceTo.sendKeys(priceT);
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
        Assert.assertTrue("Отсутствует третий сверху элемент для производителя " + proisvoditel, getThirdFromTop.exists());
        getThirdFromTop.click();
        checkPage(proisvoditel);
        Assert.assertTrue("Отсутствует кнопка \"Характеристики\" для производителя " + proisvoditel, characteristics.exists());
        characteristics.click();
        makeScreenshot();
    }

    @Step("Проверяем что заголовок содержит производителя {0}")
    private void checkPage(String proisvoditel) {
        String title = new TextBlock(getWebDriver().findElement(By.xpath("//h1[contains(., '" + proisvoditel + "')]"))).getText();
        Assert.assertTrue("Заголовок не содержит производителя " + proisvoditel, title.contains(proisvoditel));
    }


    public CheckBox getCheckBoxPath(String name) {
        String element = "//input[@name='Производитель " + name + "']/../div";
        CheckBox checkBox = new CheckBox(getWebDriver().findElement(By.xpath(element)));
        Assert.assertTrue("Отсутствует чекбокс для выбора производителя \"" + name + "\" в фильтре", checkBox.exists());
        return checkBox;
    }


}
