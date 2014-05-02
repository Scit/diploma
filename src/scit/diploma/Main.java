package scit.diploma;

import scit.diploma.service.DBWorker;

import java.util.HashMap;
import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class Main {
    public static void main(String[] args) {
        DBWorker dbw = new DBWorker();

        List<HashMap<String, Object>> data = dbw.execute("select * from users;");

        for(HashMap<String, Object> row : data) {
            for(String column : row.keySet()) {
                System.out.print(column + ": " + row.get(column).toString() + "; ");
            }

            System.out.println();
        }
    }
}
