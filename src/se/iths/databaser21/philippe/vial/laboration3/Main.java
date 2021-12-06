package se.iths.databaser21.philippe.vial.laboration3;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Artist artist = new Artist();
        try {
            artist.connection();
            artist.runApp();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
