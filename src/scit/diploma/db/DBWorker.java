package scit.diploma.db;

import scit.diploma.data.Container;
import scit.diploma.data.ResponseMaker;
import scit.diploma.utils.ObjectIntPair;

import java.sql.*;

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

    public Container execute(Container container) {
        ResultSet resultSet = null;
        Container outputContainer = null;

        try {

            connection = DriverManager.getConnection(url, user, password);

            if (container.getRequestString().equals(GET_TABLES_LIST)) {
                // get tables list request
                resultSet = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"});
            } else if (container.getDataLength() > 0) {
                // insert request
                pst = connection.prepareStatement(container.getRequestString());

                Object[] dataRow = container.getData().get(0);
                for(int i=0; i < container.getDataWidth(); i++) {
                    pst.setObject(i+1, dataRow[i], container.getMetadata()[i].getType());
                }

                pst.executeUpdate();
                resultSet = null;
            } else {
                // select request
                pst = connection.prepareStatement(container.getRequestString());

                resultSet = pst.executeQuery();
            }

            outputContainer = ResponseMaker.makeResponse(resultSet, container);

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

            return outputContainer;
        }
    }
}
