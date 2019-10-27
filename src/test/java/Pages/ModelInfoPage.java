package Pages;

import Base.Model;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

import java.util.HashMap;
import java.util.Map;

import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.exists;

public class ModelInfoPage extends BasePage {
    @FindBy(xpath = "//span[contains(., 'дюймов')]")
    private Button size;

    @FindBy(xpath = "//span[contains(., 'кг')]")
    private Button weightBlock;

    @Step("Сохраняем параметры для производителя {0}")
    public Model saveParams(String name) {
        pageIsLoad();
        Map<String, String> savedParams = new HashMap<>();
        String size = this.size.getText().substring(0, 2);
        if (weightBlock.exists()) {
            String weight = weightBlock.getText().replace(" кг", "");
            savedParams.put(size, weight);
            Model model = new Model(name, size, weight);
            Assert.assertNotNull("Отсутствуют параметры для сохранения модели для производителя " + name, model);
            return model;
        } else {
            collector.checkThat(weightBlock, exists());
            makeScreenshot();
            savedParams.put(size, "вес не найден");
            Model model = new Model(name, size, "вес не найден");
            Assert.assertNotNull("Отсутствуют параметры для сохранения модели для производителя " + name, model);
            return model;
        }

    }

}
