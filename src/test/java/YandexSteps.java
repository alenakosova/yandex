import Base.Model;
import Base.Props;
import Pages.ModelInfoPage;
import Pages.NotebooksPage;
import Pages.YandexPage;
import Parser.Global;
import Parser.Notebooks;
import io.qameta.allure.Step;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by out-kosova-aa on 05.10.2019.
 */

public class YandexSteps extends Notebooks {


    @Step("Редактируем файл")
    public void editFile(String... tagName) {
        File xmlFile = new File(Props.get("notebook.path"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document doc = factory.newDocumentBuilder().parse(xmlFile);
            doc.getDocumentElement().normalize();

            //обновляем значение
            updateElementValue(doc, tagName);

            //запишем отредактированный элемент в файл
            doc.getDocumentElement().normalize();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("notebooks.xml"));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML успешно изменен");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    //изменяем значение существующего элемента name
    private static void updateElementValue(Document doc, String... tagNames) {
        NodeList prices = doc.getElementsByTagName("Parameters");
        Element xml;
        Element iterator = (Element) prices.item(0);

        //проходим по каждому элементу
        for (String tagName : tagNames) {
            for (int i = 0; i < iterator.getElementsByTagName(tagName).getLength(); i++) {
                xml = (Element) prices.item(0);
                if (xml.getElementsByTagName(tagName).item(i) != null) {
                    Node nodeName = xml.getElementsByTagName(tagName).item(i).getFirstChild();
                    if (nodeName.getTextContent().matches("\\d+")) {
                        nodeName.setNodeValue(nodeName.getNodeValue() + "0");
                    }
                }
            }

        }
    }

    @Step("Создаем файл")
    void createFile(String name, String display, String weight) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element company = doc.createElement("Products");
            doc.appendChild(company);
            Element staff = doc.createElement("Manufacturer");
            company.appendChild(staff);
            Attr id = doc.createAttribute("id");
            id.setTextContent("1");
            staff.setAttributeNode(id);
            staff.setAttribute("id", "notebook");
            Element firstname = doc.createElement("Производитель");
            firstname.setTextContent(name);
            staff.appendChild(firstname);
            Element lastname = doc.createElement("Диагональ");
            lastname.setTextContent(display);
            staff.appendChild(lastname);
            Element nickname = doc.createElement("Вес");
            nickname.setTextContent(weight);
            staff.appendChild(nickname);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("result.xml"));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML успешно изменен");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Step("Поиск по продуктам")
    List<Model> findProduct(String globalRating, int globalPrice) {
        List<Model> newList = new ArrayList<>();
        findByProductType("Name", "notebook").forEach(x -> {
            goToNotebooks();
            String min = getValueFromManufacturer(getMin(), x);
            String max;
            Model model;
            if (Integer.valueOf(getValueFromManufacturer(getMax(), x)) > globalPrice) {
                max = String.valueOf(globalPrice);
                model = searchEach(x, min , max, globalRating);
            } else {
                max = getValueFromManufacturer(getMax(), x);
                model = searchEach(x, min, max , globalRating);
            }
            newList.add(model);
        });
        return newList;
    }

    @Step("Поиск максимальной диагонали")
    List<String> findMaxDisplay(List<Model> file) {
        int maxDisplay = 0;
        List<String> result = new ArrayList<>();
        for (Model model : file) {
            if (maxDisplay < Integer.valueOf(model.getDisplay())) {
                maxDisplay = Integer.valueOf(model.getDisplay());
                result.clear();
                result.add(0, model.getName());
                result.add(1, model.getDisplay());
                result.add(2, model.getWeight());
            }
        }
        return result;
    }

    @Step("Поиск города по буквам {0}")
    void findCity(String text) {
        new YandexPage().findCity(text);
    }

    @Step("Переход к категории ноутбуки")
    private void goToNotebooks() {
       new NotebooksPage().setCategory();
    }

    @Step("Поиск параметров для производителя {0}")
    private Model searchEach(String name, String minPrice, String maxPrice, String rating) {
        NotebooksPage page = new NotebooksPage();
        page.getManufacturerCheckBoxPath(name).click();
        page.setPrice(minPrice, maxPrice);
        page.setParams(rating);
        page.setManufacturers(new Global().getVendor());
        page.getThirdFromTop(name);
        return new ModelInfoPage().saveParams(name);
    }


    private List<String> findByProductType(String findValue, String condition) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < getManufacturerList().size(); i++) {
            if (getManufacturerList().get(i).getProductType().contains(condition)) {
                switch (findValue) {
                    case "Name":
                        result.add(getManufacturerList().get(i).getName());
                        break;
                    case "Min":
                        result.add(getManufacturerList().get(i).getMin());
                        break;
                    case "Max":
                        result.add(getManufacturerList().get(i).getMax());
                        break;
                    default:
                        throw new RuntimeException("Неверное вхождение значений для поиска по типу " + condition + ". " +
                                " Указанное значение: \"" + findValue + "\"");
                }

            }


        }
        return result;

    }

//    public List<String> findByName(String findValue, String name) {
//        List<String> result = new ArrayList<>();
//        for (int i = 0; i < getManufacturerList().size(); i++) {
//            if (getManufacturerList().get(i).getName().contains(name)) {
//                switch (findValue) {
//                    case "Products":
//                        result = getManufacturerList().get(i).getProductType();
//                        break;
//                    case "Min":
//                        result.add(getManufacturerList().get(i).getMin());
//                        break;
//                    case "Max":
//                        result.add(getManufacturerList().get(i).getMax());
//                        break;
//                }
//
//            }
//
//
//        }
//        return result;
//
//    }

}

