package Base;

import java.util.Objects;


public class Model {


    private String name;


    private String display;

        private String weight;


    public Model (String name, String display, String weight) {
        this.name = name;
        this.display = display;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplay() {
        return this.display;
    }

    public String getWeight() {
        return this.weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Model)) {
            return false;
        } else {
            Model a = (Model) obj;
            if (a.getName() == getName() && Objects.equals(a.getDisplay(), getDisplay())
                    && Objects.equals(a.getWeight(), getWeight())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int mult = 31;
        int result = 17;
        result = mult * result + (this.name == null ? 0 : this.name.hashCode());
        result = mult * result + (this.display == null ? 0 : this.display.hashCode());
        result = mult * result + (this.weight == null ? 0 : this.weight.hashCode());
        return result;
    }

}
