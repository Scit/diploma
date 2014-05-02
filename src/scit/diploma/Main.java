package scit.diploma;

import scit.diploma.service.DBWorker;
import scit.diploma.utils.SerializableStorage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by scit on 5/2/14.
 */
public class Main {
    public static void main(String[] args) {
        DBWorker dbw = new DBWorker();

        SerializableStorage serializableStorage = dbw.execute("select * from users;");
        List<Object[]> data = serializableStorage.getData();

        for(Object[] row : data) {
            for(Object cell : row) {
                System.out.print(cell.toString() + "; ");
            }

            System.out.println();
        }
    }
}
