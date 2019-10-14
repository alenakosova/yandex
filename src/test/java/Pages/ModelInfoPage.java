package Pages;

import Base.Bean;
import Base.Model;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import java.util.HashMap;
import java.util.Map;

public class ModelInfoPage extends BasePage {

    @Step("Сохраняем параметры для производителя {0}")
    public Model saveParams(String name) {
        Map<String, String> savedParams = new HashMap<>();
        String size = new TextBlock(getWebDriver().findElement(By.xpath("//span[contains(., 'дюймов')]"))).getText().substring(0, 2);
        String weight = new TextBlock(getWebDriver().findElement(By.xpath("//span[contains(., 'кг')]"))).getText().replace(" кг", "");


        savedParams.put(size, weight);
        Model model = new Model(name, size, weight);
        Assert.assertNotNull("Отсутствуют параметры для сохранения модели для производителя " + name, model);
        return model;
    }

}
