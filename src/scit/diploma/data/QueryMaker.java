package scit.diploma.data;

import scit.diploma.utils.NameTypePair;
import scit.diploma.utils.StringPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class QueryMaker {
    private static final int MODE_UPDATE = 0;
    private static final int MODE_INSERT = 1;

    public static Container selectTables() {
        String queryString = "\\l";

        return new Container(queryString);
    }

    public static Container selectTableContent(String tableName) {
        String queryString = "SELECT * FROM " + tableName;

        return new Container(tableName, queryString);
    }

    public static Container insertData(Object[] dataRow, Container container) {
        String queryString = "INSERT INTO " + container.getTableName();
        StringPair queryParams = generateInsertParams(container.getMetadata());

        queryString += queryParams.getFirst() + " VALUES " + queryParams.getSecond();

        NameTypePair[] metadata = prepareMetadata(container, MODE_INSERT);
        dataRow = prepareObjects(dataRow, container, MODE_INSERT);

        List<Object[]> data = new ArrayList<Object[]>();
        data.add(dataRow);
        return new Container(container.getTableName(),queryString, metadata, data);
    }

    public static Container updateData(Object[] dataRow, Container container) {
        String queryString = "UPDATE " + container.getTableName() + " SET ";
        queryString += generateUpdateParams(container.getMetadata());
        queryString += " WHERE id = ?";

        NameTypePair[] metadata = prepareMetadata(container, MODE_UPDATE);
        dataRow = prepareObjects(dataRow, container, MODE_UPDATE);

        List<Object[]> data = new ArrayList<Object[]>();
        data.add(dataRow);
        return new Container(container.getTableName(), queryString, metadata, data);
    }

    private static StringPair generateInsertParams(NameTypePair[] metadata) {
        String first = "(";
        String second = "(";

        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                first += column.getName() + ",";
                second += "?,";
            }
        }

        if(first.endsWith(",")) {
            first = first.substring(0, first.length() - 1) + ")";
            second = second.substring(0, second.length() - 1) + ")";
        }

        return new StringPair(first, second);
    }

    private static String generateUpdateParams(NameTypePair[] metadata) {
        String updateParams = "";

        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                updateParams += column.getName() + " = ?,";
            }
        }

        if(updateParams.endsWith(",")) {
            updateParams = updateParams.substring(0, updateParams.length() -1);
        }

        return updateParams;
    }

    private static Object[] prepareObjects(Object[] dataRow, Container container, int mode) {
        List<Object> tmpRow = new ArrayList<Object>();

        int counter = 0;
        int idIndex = 0;
        NameTypePair[] metadata = container.getMetadata();
        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                tmpRow.add(dataRow[counter++]);
            } else {
                idIndex = counter;

                if (dataRow.length == container.getDataWidth()) {
                    //for update statements
                    counter++;
                }
            }
        }

        if(mode == MODE_UPDATE) {
            tmpRow.add(dataRow[idIndex]);
        }

        return tmpRow.toArray(new Object[tmpRow.size()]);
    }

    private static NameTypePair[] prepareMetadata(Container container, int mode) {
        List<NameTypePair> tmpMetadata = new ArrayList<NameTypePair>();

        int counter = 0;
        int idIndex = 0;
        NameTypePair[] metadata = container.getMetadata();
        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                tmpMetadata.add(column);
            }
        }

        if(mode == MODE_UPDATE) {
            tmpMetadata.add(metadata[idIndex]);
        }

        return tmpMetadata.toArray(new NameTypePair[tmpMetadata.size()]);
    }
}
