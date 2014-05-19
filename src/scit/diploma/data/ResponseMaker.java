package scit.diploma.data;

import jade.lang.acl.MessageTemplate;
import scit.diploma.utils.MetadataHasher;
import scit.diploma.utils.NameTypePair;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static scit.diploma.data.AgentDataContainer.*;

/**
 * Created by scit on 5/6/14.
 */
public class ResponseMaker {
    public static AgentDataContainer makeResponse(ResultSet resultSet, AgentDataContainer agentDataContainer) {
        NameTypePair[] metadata = null;
        List<Object[]> data = null;
        String metadataHash = "";

        if (resultSet != null) {
            try {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                MetadataHasher.reset();

                metadata = new NameTypePair[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    metadata[columnIndex - 1] = new NameTypePair();
                    String name = rsmd.getColumnName(columnIndex);
                    int type = rsmd.getColumnType(columnIndex);
                    metadata[columnIndex - 1].setName(name);
                    metadata[columnIndex - 1].setType(type);

                    MetadataHasher.add(type, name);
                }
                metadataHash = MetadataHasher.get();

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

        String tableName = agentDataContainer.getParam(KEY_TABLE_NAME);
        agentDataContainer = new AgentDataContainer(metadata,data);
        agentDataContainer.setParam(KEY_TABLE_NAME, tableName);
        agentDataContainer.setParam(KEY_METADATA_HASH, metadataHash);

        return agentDataContainer;
    }
}
