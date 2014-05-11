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


        Object[] dataRow = new Object[] {7, "lololo", "ololol"};
        containerIn = QueryMaker.updateData(dataRow, containerOut);
        containerOut = dbw.execute(containerIn);
    }
}
