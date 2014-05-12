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

    public static AgentDataContainer selectTables() {
        String queryString = "\\l";

        return new AgentDataContainer(queryString);
    }

    public static AgentDataContainer selectTableContent(String tableName) {
        String queryString = "SELECT * FROM " + tableName;

        return new AgentDataContainer(tableName, queryString);
    }

    public static AgentDataContainer insertData(Object[] dataRow, AgentDataContainer agentDataContainer) {
        String queryString = "INSERT INTO " + agentDataContainer.getTableName();
        StringPair queryParams = generateInsertParams(agentDataContainer.getMetadata());

        queryString += queryParams.getFirst() + " VALUES " + queryParams.getSecond();

        NameTypePair[] metadata = prepareMetadata(agentDataContainer, MODE_INSERT);
        dataRow = prepareObjects(dataRow, agentDataContainer, MODE_INSERT);

        List<Object[]> data = new ArrayList<Object[]>();
        data.add(dataRow);
        return new AgentDataContainer(agentDataContainer.getTableName(),queryString, metadata, data);
    }

    public static AgentDataContainer updateData(Object[] dataRow, AgentDataContainer agentDataContainer) {
        String queryString = "UPDATE " + agentDataContainer.getTableName() + " SET ";
        queryString += generateUpdateParams(agentDataContainer.getMetadata());
        queryString += " WHERE id = ?";

        NameTypePair[] metadata = prepareMetadata(agentDataContainer, MODE_UPDATE);
        dataRow = prepareObjects(dataRow, agentDataContainer, MODE_UPDATE);

        List<Object[]> data = new ArrayList<Object[]>();
        data.add(dataRow);
        return new AgentDataContainer(agentDataContainer.getTableName(), queryString, metadata, data);
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

    private static Object[] prepareObjects(Object[] dataRow, AgentDataContainer agentDataContainer, int mode) {
        List<Object> tmpRow = new ArrayList<Object>();

        int counter = 0;
        int idIndex = 0;
        NameTypePair[] metadata = agentDataContainer.getMetadata();
        for(NameTypePair column : metadata) {
            if( ! column.getName().equals("id")) {
                tmpRow.add(dataRow[counter++]);
            } else {
                idIndex = counter;

                if (dataRow.length == agentDataContainer.getDataWidth()) {
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

    private static NameTypePair[] prepareMetadata(AgentDataContainer agentDataContainer, int mode) {
        List<NameTypePair> tmpMetadata = new ArrayList<NameTypePair>();

        int counter = 0;
        int idIndex = 0;
        NameTypePair[] metadata = agentDataContainer.getMetadata();
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
