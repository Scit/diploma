package scit.diploma.data;

import scit.diploma.utils.NameTypePair;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scit on 5/6/14.
 */
public class ResponseMaker {
    public static AgentDataContainer makeResponse(ResultSet resultSet, AgentDataContainer agentDataContainer) {
        NameTypePair[] metadata = null;
        List<Object[]> data = null;

        if (resultSet != null) {
            try {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();

                metadata = new NameTypePair[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    metadata[columnIndex - 1] = new NameTypePair();
                    String name = rsmd.getColumnName(columnIndex);
                    int type = rsmd.getColumnType(columnIndex);
                    metadata[columnIndex - 1].setName(name);
                    metadata[columnIndex - 1].setType(type);
                }

                data = new ArrayList<Object[]>();
                while (resultSet.next()) {
                    Object[] row = new Object[columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        row[columnIndex - 1] = resultSet.getObject(columnIndex);
                    }
                    data.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new AgentDataContainer(agentDataContainer.getTableName(), metadata, data);
    }
}
