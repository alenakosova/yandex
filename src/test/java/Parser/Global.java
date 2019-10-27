package Parser;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by out-kosova-aa on 06.10.2019.
 */
public class Global extends Notebooks {
    private int price;
    private int rating;
    private List<String> vendor;


    public Global() {
        int xmlPrice = Integer.parseInt(xml.getElementsByTagName("Price").item(0).getTextContent().trim());
        int xmlRating = Integer.parseInt(xml.getElementsByTagName("Excluded_vendors").item(0).getAttributes().item(0).getTextContent());

        this.price = xmlPrice;
        this.rating = xmlRating;
        List<String> vendor = new ArrayList<>();
        for (int i = 0; i < xml.getElementsByTagName("Vendor").getLength(); i++) {
            vendor.add(xml.getElementsByTagName("Vendor").item(i).getTextContent());
        }
        this.vendor = vendor;
    }

    public int getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public List<String> getVendor() {
        return vendor;
    }


}
