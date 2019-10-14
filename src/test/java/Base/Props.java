package Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by out-kosova-aa on 05.10.2019.
 */
public class Props {
    private static Props instance;
    private static Properties props;


    private synchronized static Props getInstance() {
        if (instance == null) {
            instance = new Props();
        }
        return instance;
    }


    private Props() {
        try {
            System.setProperty("logback.configurationFile", "config/logback.xml");
            String sConfigFile = System.getProperty("BDDConfigFile", "config/application.properties");
            //first try to load properties from resources
            InputStream in = Props.class.getClassLoader().getResourceAsStream(sConfigFile);
            //if failed try to load from filesystem
            if (in == null) {
                in = new FileInputStream(sConfigFile);
            }
            props = new Properties();
            props.load(in);
        } catch (IOException e) {
            System.out.println("Failed to initialize props: " + e);
        }
    }

    public static String get(String prop) {
        return Props.getInstance().getProp(prop);
    }


    private String getProp(String name) {
        String val = getProps().getProperty(name, "");
        if (val.isEmpty()) {
            System.out.println("Property {} was not found in Base.Props" + name);
        }
        return val.trim();
    }

    /**
     * @return the props
     */
    public static Properties getProps() {
        return props;
    }
}
