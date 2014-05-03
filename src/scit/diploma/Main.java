package scit.diploma;

import scit.diploma.data.Container;
import scit.diploma.data.QueryMaker;
import scit.diploma.db.DBWorker;

import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class Main {
    public static void main(String[] args) {
        DBWorker dbw = new DBWorker();
        Container containerIn;
        Container containerOut;

        containerIn = QueryMaker.selectTables();
        containerOut = dbw.execute(containerIn);

        System.out.println(containerOut.toString());


        containerIn = QueryMaker.selectTableContent("users");
        containerOut = dbw.execute(containerIn);

        System.out.println(containerOut.toString());


        String[] dataRow = new String[] {"login", "password"};
        containerIn = QueryMaker.insertData(dataRow, containerOut);
        containerOut = dbw.execute(containerIn);
    }
}
