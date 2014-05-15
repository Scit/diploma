package scit.diploma.data;

import scit.diploma.utils.NameTypePair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static scit.diploma.db.DBWorker.*;

/**
 * Created by scit on 5/6/14.
 */
public class AgentDataContainer implements Serializable {
    public final static String KEY_TABLE_NAME = "KEY_TABLE_NAME";
    public final static String KEY_REQUEST_STRING = "KEY_REQUEST_STRING";
    public final static String KEY_SERVICE_NAME = "KEY_SERVICE_NAME";

    private HashMap<String, String> params = null;
    private NameTypePair[] metadata = null;
    private List<Object[]> data = null;

    public AgentDataContainer() {
        params = new HashMap<String, String>();
    }

    public AgentDataContainer(NameTypePair[] metadata, List<Object[]> data) {
        params = new HashMap<String, String>();

        this.metadata = metadata;
        this.data = data;
    }

    public NameTypePair[] getMetadata() {
        return metadata;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setMetadata(NameTypePair[] metadata) {
        this.metadata = metadata;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
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

    public HashMap<String, String> getParams() {
        return params;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public void setParam(String key, String value) {
        params.put(key, value);
    }

    public void clearParams() {
        this.params = new HashMap<String, String>();
    }

    public String toString() {
        String string = "";

        for(String key : params.keySet()) {
            string += key + ": " + params.get(key) + "\n";
        }

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
