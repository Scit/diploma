package scit.diploma.utils;

import java.io.Serializable;

/**
 * Created by scit on 5/3/14.
 */
public class ObjectIntPair implements Serializable{
    private Object object;
    private int anInt;

    public ObjectIntPair() {

    }

    public ObjectIntPair(Object object, int anInt) {
        this.object = object;
        this.anInt = anInt;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getInt() {
        return anInt;
    }

    public void setInt(int anInt) {
        this.anInt = anInt;
    }
}
