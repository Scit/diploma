package scit.diploma.db;

import scit.diploma.data.AgentDataContainer;
import scit.diploma.data.ResponseMaker;
import scit.diploma.utils.AgentData;
import scit.diploma.utils.MetadataHasher;

import java.sql.*;
import static scit.diploma.data.AgentDataContainer.*;

/**
 * Created by scit on 5/1/14.
 */
public class DBWorker {
    public static final String GET_TABLES_LIST = "\\l";
    public static final String STATUS_OK = "OK";
    public static final String STATUS_NONE = "NONE";

    Connection connection = null;
    PreparedStatement pst = null;

    private String url = "jdbc:postgresql://localhost/postgres";
    private String user = "postgres";
    private String password = "postgres";

    public AgentDataContainer execute(AgentDataContainer agentDataContainer) {
        ResultSet resultSet = null;
        AgentDataContainer outputAgentDataContainer = null;

        try {
            String dataType = AgentDataContainer.VALUE_DATA_TYPE_TABLES;

            connection = DriverManager.getConnection(url, user, password);

            if (agentDataContainer.getParam(KEY_REQUEST_STRING).equals(GET_TABLES_LIST)) {
                // get tables list request
                resultSet = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"});
            } else if (agentDataContainer.getDataLength() > 0) {
                // insert request

                // verify table structure: table name and metadata hash
                boolean verified = verify(connection, agentDataContainer);
                if(verified) {
                    dataType = AgentDataContainer.VALUE_DATA_TYPE_EMPTY;
                    pst = connection.prepareStatement(agentDataContainer.getParam(KEY_REQUEST_STRING));

                    Object[] dataRow = agentDataContainer.getData().get(0);
                    for (int i = 0; i < agentDataContainer.getDataWidth(); i++) {
                        pst.setObject(i + 1, dataRow[i], agentDataContainer.getMetadata()[i].getType());
                    }

                    pst.executeUpdate();
                }

                resultSet = null;
            } else {
                // select request
                dataType = AgentDataContainer.VALUE_DATA_TYPE_CONTENT;
                pst = connection.prepareStatement(agentDataContainer.getParam(KEY_REQUEST_STRING));

                resultSet = pst.executeQuery();
            }

            outputAgentDataContainer = ResponseMaker.makeResponse(resultSet, agentDataContainer);
            outputAgentDataContainer.setParam(KEY_DATA_TYPE, dataType);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return outputAgentDataContainer;
        }
    }

    private boolean verify(Connection connection, AgentDataContainer agentDataContainer) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(null, "public", agentDataContainer.getParam(KEY_TABLE_NAME), new String[]{"TABLE"});
        if( ! resultSet.next()) {
            return false;
        } else {
            resultSet = connection.getMetaData().getColumns(null, "public", agentDataContainer.getParam(KEY_TABLE_NAME), null);
            int type;
            String name;
            MetadataHasher.reset();
            while(resultSet.next()) {
                type = resultSet.getInt("TYPE_NAME");
                name = resultSet.getString("COLUMN_NAME");
                MetadataHasher.add(type, name);
            }

            String metadataHash = MetadataHasher.get();
            if(! metadataHash.equals(agentDataContainer.getParam(KEY_METADATA_HASH))) {
                return false;
            }
        }

        return true;
    }
}
