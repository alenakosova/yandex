package Parser;


import org.junit.Assert;

import java.util.List;

/**
 * Created by out-kosova-aa on 06.10.2019.
 */
public class Global extends Notebooks{
    private int price;
    private int rating;
    private List<String> vendor;


    public Global(){
        int xmlPrice = Integer.valueOf(xml.getElementsByTagName("Price").item(0).getTextContent().trim());
        int xmlRating = Integer.valueOf(xml.getElementsByTagName("Excluded_vendors").item(0).getAttributes().item(0).getTextContent());
        this.price = xmlPrice;
        this.rating = xmlRating;
    }
    public int getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public List<String> getVendor() {
        vendor.add(0, "microprice");
        vendor.add(1, "полюс");
        return vendor;
    }





}
