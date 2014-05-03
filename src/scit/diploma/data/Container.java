package scit.diploma.data;

import scit.diploma.utils.NameTypePair;

import java.io.Serializable;
import java.util.List;

import static scit.diploma.db.DBWorker.*;

/**
 * Created by scit on 5/6/14.
 */
public class Container implements Serializable {
    private String requestString = "";
    private String tableName = "";
    private NameTypePair[] metadata = null;
    private List<Object[]> data = null;

    public Container(String tableName, String requestString, NameTypePair[] metadata, List<Object[]> data) {
        this.tableName = tableName;
        this.requestString = requestString;
        this.metadata = metadata;
        this.data = data;
    }

    public Container(String tableName, NameTypePair[] metadata, List<Object[]> data) {
        this.tableName = tableName;
        this.metadata = metadata;
        this.data = data;
    }

    public Container(String tableName, String requestString) {
        this.tableName = tableName;
        this.requestString = requestString;
    }

    public Container(String requestString) {
        this.requestString = requestString;
    }

    public String getRequestString() {
        return requestString;
    }

    public NameTypePair[] getMetadata() {
        return metadata;
    }

    public List<Object[]> getData() {
        return data;
    }

    public int getDataWidth() {
        if (data == null || metadata == null) {
            return 0;
        }

        return metadata.length;
    }

    public int getDataLength() {
        if (data == null || metadata == null) {
            return 0;
        }

        return data.size();
    }

    public String getTableName() {
        return tableName;
    }

    public String toString() {
        String string = "";

        string += requestString + "\n";
        string += tableName + "\n";

        if (metadata != null) {
            for(NameTypePair item : metadata) {
                string += item.getName() + ": " + item.getType() + "; ";
            }
        }
        string += "\n";

        if (data != null) {
            for(Object[] row : data) {
                for(Object item : row) {
                    string += item + "; ";
                }
                string += "\n";
            }
            string += "\n";
        }

        return string;
    }
}
