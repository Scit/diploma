package scit.diploma.utils;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by scit on 5/2/14.
 */
public class SerializableStorage implements Serializable {
    private Pair[] metadata = null;
    private List<Object[]> data = null;

    public SerializableStorage(ResultSet resultSet) {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            metadata = new Pair[columnCount];

            for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                metadata[columnIndex-1] = new Pair();
                metadata[columnIndex-1].name = rsmd.getColumnName(columnIndex);
                metadata[columnIndex-1].type = rsmd.getColumnType(columnIndex);
            }

            data = new ArrayList<Object[]>();
            while(resultSet.next()) {
                Object[] row = new Object[columnCount];
                for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    row[columnIndex-1] = resultSet.getObject(columnIndex);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class Pair implements Serializable {
        private String name;
        private int type;
    }

    public Pair[] getMetadata() {
        return metadata;
    }

    public List<Object[]> getData() {
        return data;
    }
}
