package software.ulpgc;

import java.sql.SQLException;
import java.util.List;

public interface TrackLoader {
    List<Track> loadAll() throws SQLException;
}
