package tikape.runko.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Keraaja<T> {

    T keraa(ResultSet rs) throws SQLException;
}
