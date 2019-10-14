package Parser;

import Base.BaseTest;
import Base.Bean;
import Base.Props;
import org.junit.After;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import ru.yandex.qatools.allure.annotations.Step;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by out-kosova-aa on 05.10.2019.
 */
public class Notebooks extends BaseTest {
    private Document document;
    private NodeList prices;
    Element xml;


    public Notebooks() {
        File xmlFile = new File(Props.get("notebook.path"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document doc = factory.newDocumentBuilder().parse(xmlFile);
            doc.getDocumentElement().normalize();
            this.document = doc;
            prices = document.getElementsByTagName("Parameters");
            xml = (Element) prices.item(0);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void editFile(String... tagName) {
//        File xmlFile = new File(Props.get("notebook.path"));
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
//            Document doc = factory.newDocumentBuilder().parse(xmlFile);
//            doc.getDocumentElement().normalize();

            //обновляем значение
            updateElementValue(document, tagName);

            //запишем отредактированный элемент в файл
            document.getDocumentElement().normalize();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
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





        public String getMin(){
            return "Min";
        }

        public String getMax(){
            return "Max";
        }

     public String getName(){
        return "Name";
      }




    public List<Manufacturer> getManufacturerList(){
        List<Manufacturer> newList = new ArrayList<>();
        int count = getValueByName("Name").size();
        for (int i = 0; i < count; i++){
            newList.add(i, getSomeManufacturer(i));
        }
        return newList;
    }

    private Manufacturer getSomeManufacturer(int count){
        List<Manufacturer> allManufactures = new ArrayList<>();
        getValueByName("Name").forEach(x -> {

            String min = getValueByName("Min", count).get(0);
            String max = getValueByName("Max", count).get(0);
            List<String> productType = new ArrayList<>();
            String[] products = xml.getElementsByTagName("Manufacturer").item(count).getAttributes().item(0).getTextContent().split(",");
            for (int k = 0; k < products.length; k++) {
                productType.add(k, products[k].replaceAll(" ", ""));
            }
            allManufactures.add(0, new Manufacturer(x, min, max, productType));

        });
        return allManufactures.get(count);
    }

    protected String getValueFromManufacturer(String value, String name){
            String returnValue = null;
        for (int k = 0; k < getManufacturerList().size(); k++) {
            if (getManufacturerList().get(k).getName().equals(name)) {
                switch (value) {
                    case "Name":
                        returnValue = getManufacturerList().get(k).getName();
                        break;
                    case "Min":
                        returnValue = getManufacturerList().get(k).getMin();
                        break;
                    case "Max":
                        returnValue = getManufacturerList().get(k).getMax();
                        break;
                }
            }
        }

        return returnValue;
    }





    private List<String> getValueByName(String tag) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < xml.getElementsByTagName(tag).getLength(); i++) {
            if (xml.getElementsByTagName(tag).item(i) != null) {
                int k = 0 ;
                Node nodeName = xml.getElementsByTagName(tag).item(i).getFirstChild();
                values.add(k, nodeName.getNodeValue());
            }
        }
        return values;
    }

    private List<String> getValueByName(String tag, int count) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < xml.getElementsByTagName(tag).getLength(); i++) {
            if (xml.getElementsByTagName(tag).item(i) != null) {
                int k = 0 ;
                Node nodeName = xml.getElementsByTagName(tag).item(count).getFirstChild();
                values.add(k, nodeName.getNodeValue());
            }
        }
        return values;
    }

//    public List<String> getValueByAttributteValue(String value, String attribute) {
//        List<String> values = new ArrayList<>();
//        for (int i = 0; i < xml.getElementsByTagName(value).getLength(); i++) {
//            String nodeName = xml.getElementsByTagName(value).item(i).getParentNode().getAttributes().item(0).getTextContent();
//            if (nodeName.contains(attribute)) {
//                int k = 0;
//                values.add(k, xml.getElementsByTagName(value).item(i).getTextContent());
//                k++;
//            }
//        }
//
//        return values;
//    }
//
//    public String getIndexByValue(String value, Stream condition) {
//        for (int i = 0; i < getManufacturerList().size(); i++){
//            int k = i;
//            getManufacturerList().forEach(x -> {
//                condition.forEach();
//                x.getValueFromManufacturer(value, getManufacturerList().get(k).getName());
//
//            });
//        }
//
//    }



}
