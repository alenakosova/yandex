package Pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class YandexPage extends BasePage {

    @FindBy(xpath = "//span[@class='header2-menu__text'][contains(., 'Москва')]/..")
    private Button moscow;

    @FindBy(xpath = "//input[@placeholder=\"Укажите другой регион\"]")
    private TextInput findCity;

    @FindBy(xpath = "//span[contains(., 'Санкт-Петербург')]")
    private Button petersburgCity;

    @FindBy(xpath = "//span[contains(., 'Продолжить с новым регионом')]/..")
    private Button next;


    @Step("Поиск города по буквам {0}")
    public void findCity(String text) {
        goTo("https://market.yandex.ru");
        pageIsLoad();
        Assert.assertTrue("Не найден раздел с выбором города", moscow.exists());
        moscow.click();
        Assert.assertTrue("Не найдена кнопка \"Укажите другой регион\"", findCity.exists());
        findCity.click();
        findCity.sendKeys(text);
        Assert.assertTrue("Не найден город \"Санкт-Петербург\"", petersburgCity.exists());
        petersburgCity.click();
        Assert.assertTrue("Не найдена кнопка \"Продолжить с новым регионом\"", next.exists());
        next.click();
    }
}
