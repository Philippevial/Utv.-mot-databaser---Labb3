package se.iths.databaser21.philippe.vial.laboration3;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        Artist artist = new Artist();
            artist.connection();
            artist.runApp();
    }

}
