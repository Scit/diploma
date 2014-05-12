package scit.diploma;

import scit.diploma.data.AgentDataContainer;
import scit.diploma.data.QueryMaker;
import scit.diploma.db.DBWorker;

/**
 * Created by scit on 5/2/14.
 */
public class Main {
    public static void main(String[] args) {
        DBWorker dbw = new DBWorker();
        AgentDataContainer agentDataContainerIn;
        AgentDataContainer agentDataContainerOut;

        agentDataContainerIn = QueryMaker.selectTables();
        agentDataContainerOut = dbw.execute(agentDataContainerIn);

        System.out.println(agentDataContainerOut.toString());


        agentDataContainerIn = QueryMaker.selectTableContent("users");
        agentDataContainerOut = dbw.execute(agentDataContainerIn);

        System.out.println(agentDataContainerOut.toString());


        Object[] dataRow = new Object[] {7, "lololo", "ololol"};
        agentDataContainerIn = QueryMaker.updateData(dataRow, agentDataContainerOut);
        agentDataContainerOut = dbw.execute(agentDataContainerIn);
    }
}
