package software.ulpgc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqliteTrackLoader implements TrackLoader{
    private final Connection connection;
    private String queryAllSql = "SELECT tracks.name As track, composer, milliseconds, title, genres.name As genre, artists.name As artist FROM tracks, albums, artists, genres WHERE \n" +
            "tracks.AlbumId = albums.AlbumId AND \n" +
            "tracks.GenreId = genres.GenreId AND \n" +
            "albums.ArtistId = artists.ArtistId";

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> loadAll() throws SQLException {
        return load(resultOfSet(queryAllSql));
    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(trackFrom(resultSet));
        }
        return list;
    }

    private Track trackFrom(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getString("track"),
                resultSet.getString("composer"),
                resultSet.getInt("milliseconds"),
                resultSet.getString("title"),
                resultSet.getString("genre"),
                resultSet.getString("artist")
        );
    }

    private ResultSet resultOfSet(String queryAllSql) throws SQLException {
        return connection.createStatement().executeQuery(queryAllSql);
    }
}
