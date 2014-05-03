package scit.diploma.data;

import scit.diploma.utils.NameTypePair;
import scit.diploma.utils.ObjectIntPair;
import scit.diploma.utils.StringPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class QueryMaker {
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
        StringPair queryTuples = generateTuples(container.getMetadata());
        NameTypePair[] metadata = prepareMetadata(container);

        queryString += queryTuples.getFirst() + " VALUES " + queryTuples.getSecond();

        dataRow = prepareObjects(dataRow, container);

        List<Object[]> data = new ArrayList<Object[]>();
        data.add(dataRow);
        return new Container(container.getTableName(),queryString, metadata, data);
    }

    private static StringPair generateTuples(NameTypePair[] metadata) {
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

    private static Object[] prepareObjects(Object[] dataRow, Container container) {
        List<Object> tmpRow = new ArrayList<Object>();

        int counter = 0;
        NameTypePair[] metadata = container.getMetadata();
        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                tmpRow.add(dataRow[counter++]);
            }
        }

        return tmpRow.toArray(new Object[tmpRow.size()]);
    }

    private static NameTypePair[] prepareMetadata(Container container) {
        List<NameTypePair> tmpMetadata = new ArrayList<NameTypePair>();

        int counter = 0;
        NameTypePair[] metadata = container.getMetadata();
        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                tmpMetadata.add(column);
            }
        }

        return tmpMetadata.toArray(new NameTypePair[tmpMetadata.size()]);
    }
}
