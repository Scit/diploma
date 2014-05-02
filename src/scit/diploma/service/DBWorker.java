package scit.diploma.service;

import scit.diploma.utils.SerializableStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by scit on 5/1/14.
 */
public class DBWorker {
    Connection connection = null;
    PreparedStatement pst = null;

    private String url = "jdbc:postgresql://localhost/postgres";
    private String user = "postgres";
    private String password = "postgres";

    public SerializableStorage execute(String request) {
        ResultSet resultSet = null;
        List<HashMap<String, Object>> data = null;
        SerializableStorage serializableStorage = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            pst = connection.prepareStatement(request);
            resultSet = pst.executeQuery();
            serializableStorage = new SerializableStorage(resultSet);

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
        }

        return serializableStorage;
    };

    private List<HashMap<String, Object>> resultSetToList(ResultSet resultSet) throws Exception{
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        HashMap<String, Object> row = new HashMap<String, Object>();

        for (int columnIndex = 1; columnIndex <= rsmd.getColumnCount(); columnIndex++) {
            row.put(rsmd.getColumnName(columnIndex), rsmd.getColumnType(columnIndex));
        }
        data.add(row);

        while (resultSet.next()) {
            row = new HashMap<String, Object>();

            for (int columnIndex = 1; columnIndex <= rsmd.getColumnCount(); columnIndex++) {
                row.put(rsmd.getColumnName(columnIndex), resultSet.getObject(columnIndex));
            }
            data.add(row);
        }

        return data;
    }
}
