package Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by out-kosova-aa on 06.10.2019.
 */
public class Manufacturer extends Global{
    private String name;
    private String min;
    private String max;
    private List<String> productType;

    Manufacturer(String name, String min, String max, List<String> productType){
        this.name = name;
        this.min = min;
        this.max = max;
        this.productType = productType;
    }

//    public String getVal(String value) {
//        String val = null;
//        switch (value){
//            case("Name"):
//                 val = name;break;
//            case("min"):
//                val = min;break;
//            case("max"):
//                val =  max;break;
//
//        }
//        return val;
//    }

    public String getName() {
        return name;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public List<String> getProductType() {
        return productType;
    }



}
