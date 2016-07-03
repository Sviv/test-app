package org.wordscaster.logic;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.wordscaster.Config;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by A on 27.06.2016.
 */
public class ManagmentSystem {
    private Connection con = null;
    private Statement stmt = null;
    private Statement stmt1 = null;
    private ResultSet rs = null;
    final static Logger logger = Logger.getLogger(ManagmentSystem.class);

    public ManagmentSystem() {
        try {
            Class.forName(Config.jdbcDriver());
            con = DriverManager.getConnection(Config.dbUrl(), Config.dbUser(), Config.dbPassword());
            stmt = con.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error message: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error message: Не найден файл " + e.getMessage() + " по пути: " + System.getProperty("user.dir"));
        }
    };
    public JSONObject addWord(Word word) {
        JSONObject result = new JSONObject();
        try {
            //String query = ""
            PreparedStatement stmt1 = con.prepareStatement("insert into word (word, creationDate, status) VALUES (?, ?, ?)");

            stmt1.setString(1, word.getWord());
            stmt1.setDate(2, new java.sql.Date(word.getCreationDate().getTime()));
            stmt1.setInt(3, word.getStatus());
            stmt1.execute();
            //stmt.execute("INSERT INTO word (word, creationDate, status) VALUES(" + word.getWord() + ","+ new java.sql.Date(word.getCreationDate().getTime()) + ", " + word.getStatus() + ")");
            //rs = stmt.executeQuery("SELECT id FROM word WHERE word = " + word.getWord());

            //result.put("id", rs.getInt(1));
            result.put("status", "Ok");

            return result;

        } catch (SQLException | NullPointerException e) {
            logger.error("Error message: " + e.getMessage());
            e.printStackTrace();

            result.put("error", e.getMessage());
            result.put("status", "fail");

            return result;
        }
    };

    public JSONObject findWordById(int id) {
        JSONObject result = new JSONObject();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM word WHERE wordId = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            //JSONArray word = new JSONArray();

            result.put("id", id);
            result.put("word", rs.getString("word"));
            result.put("creationDate", rs.getDate("creationDate"));
            result.put("status", rs.getString("status"));
            result.put("status", "Ok");
        } catch (SQLException e) {
            logger.error("Error message: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    };
    public void modifyWord(int id) {

    }
    public ArrayList<Word> searchWordsByParams(String partWord, Date dateFrom, Date dateTill, int status) {
        ArrayList<Word> words = new ArrayList<Word>();
        return words;
    }
}
