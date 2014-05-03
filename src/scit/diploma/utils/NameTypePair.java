package scit.diploma.utils;

import java.io.Serializable;

/**
 * Created by scit on 5/6/14.
 */
public class NameTypePair implements Serializable {
    private String name = null;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
