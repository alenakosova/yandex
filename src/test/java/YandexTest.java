import Base.Model;
import Parser.Global;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.Tag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by out-kosova-aa on 05.10.2019.
 */
public class YandexTest extends YandexSteps {

    @Test
    @TmsLink("000")
    @Tag("000")
    @Description("Получение модели с масимальной диагональю")
    public void getMaxDisplayFromNotebooks() {
//                    editFile("Min", "Max", "Price");

        String globalRating = String.valueOf(new Global().getRating());
        findCity("Сан");
        List<Model> result = new ArrayList<>(findProduct(globalRating, new Global().getPrice()));
        String name = findMaxDisplay(result).get(0);
        String display = findMaxDisplay(result).get(1);
        String weight = findMaxDisplay(result).get(2);
        createFile(name, display, weight);

    }


}


