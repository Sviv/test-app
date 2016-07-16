package org.wordscaster.logic;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wordscaster.Config;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.wordscaster.logic.ResultSetConverter.convert;

/**
 * Created by A on 27.06.2016.
 */
public class ManagmentSystem {
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    final static Logger logger = Logger.getLogger(ManagmentSystem.class);
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    }

    ;

    public JSONArray addWord(Word word) {
        JSONArray result = new JSONArray();
        JSONObject error = new JSONObject();

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO word (word, creationDate, lastRepeatDate, repeatCounts, status) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, word.getWord());
            stmt.setString(2, dateFormat.format(word.getCreationDate()));
            stmt.setString(3, dateFormat.format(word.getLastRepeatDate()));
            stmt.setInt(4, word.getRepeatCounts());
            stmt.setString(5, word.getStatus());
            logger.debug("query: " + stmt.toString());
            stmt.execute();
            rs = stmt.getGeneratedKeys();
            result = convert(rs);//TODO change name "GENERATED_KEYS" to "wordId"

            return result;

        } catch (SQLException | NullPointerException e) {
            logger.error("Error message: " + e.getMessage());
            e.printStackTrace();
            error.put("error", e.getMessage());
            error.put("status", "fail");
            result.put(error);

            return result;
        }
    }

    public JSONArray findWordById(int id) {
        JSONArray result = new JSONArray();
        JSONObject error = new JSONObject();
        logger.debug("findWordById:");
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM word WHERE wordId = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            logger.debug("query: " + stmt.toString());
            result = convert(rs);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error message: " + e.getStackTrace());
            error.put("error", e.getMessage());
            error.put("status", "fail");
            result.put(error);
            return result;
        }
    }

    public JSONArray modifyWord(HashMap<String, String> params) {
        JSONArray result = new JSONArray();
        JSONObject error = new JSONObject();
        String id = "";
        logger.debug("modifyWord:");
        for (Map.Entry<String, String> pair : params.entrySet()) {
            String key = pair.getKey();                      //ключ
            String value = pair.getValue();                  //значение
            logger.debug(key + ": " + value + ";");
        }
        try {
            StringBuilder query = new StringBuilder("UPDATE word SET ");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> pair = iterator.next();
                String key = pair.getKey();
                String value = pair.getValue();
                if (!key.equals("wordId")) {
                    query.append(key);
                    query.append("=\'");
                    query.append(value);
                    query.append("\'");
                    if (iterator.hasNext()) {
                        query.append(", ");
                    }
                } else {
                    id = value;
                }
            }
            if (id.isEmpty()) {
                throw new NullPointerException("id is empty");
            }
            query.append(" WHERE wordId=\'" + id + "\'");
            logger.debug("query: " + query);
            logger.debug("query: " + stmt.toString());
            stmt.executeUpdate(query.toString());
            error.put("status", "Ok");
            result.put(error);
            //result = convert(rs);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error message: " + e.getStackTrace());
            error.put("error", e.getMessage());
            error.put("status", "fail");
            result.put(error);
            return result;
        }
    }

    public JSONArray searchWordsByParams(HashMap<String, String> params) {
        JSONArray result = new JSONArray();
        JSONObject error = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.mm.yyyy");
        logger.debug("searchWordsByParams:");
        for (Map.Entry<String, String> pair : params.entrySet()) {
            String key = pair.getKey();                      //ключ
            String value = pair.getValue();                  //значение
            logger.debug(key + ": " + value + ";");
        }
        try {
            StringBuilder query = new StringBuilder("SELECT wordId, creationDate, lastRepeatDate, repeatCounts, status FROM word");
            if (params != null) {
                prepareDates(params);
                query.append(" WHERE ");
                Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> pair = iterator.next();
                    String key = pair.getKey();
                    String value = pair.getValue();
                    if ((key.equals("creationDateRange")) || (key.equals("creationDate")) || (key.equals("lastRepeatDate"))) {
                        query.append(value);
                    } else {
                        query.append(key);
                        query.append("=\'");
                        query.append(value);
                        query.append("\'");
                    }
                    if (iterator.hasNext()) {
                        query.append(" AND ");
                    }
                }
            }
            logger.debug("query: " + stmt.toString());
            rs = stmt.executeQuery(query.toString());
            result = convert(rs);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error message: " + e.getStackTrace());
            error.put("error", e.getMessage());
            error.put("status", "fail");
            result.put(error);
            return result;
        }
    }

    public static void prepareDates(HashMap<String, String> params) {
        String dateFrom = "";
        String dateTill = "";
        String creationDate = "";
        String condition = "";
        String lastRepeatDate = "";
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> pair = iterator.next();
            if (pair.getKey().equals("dateFrom")) {
                dateFrom = pair.getValue();
                iterator.remove();
            }
            if (pair.getKey().equals("dateTill")) {
                dateTill = pair.getValue();
                iterator.remove();
            }
            if (pair.getKey().equals("creationDate")) {
                creationDate = pair.getValue();
                iterator.remove();
            }
            if (pair.getKey().equals("lastRepeatDate")) {
                creationDate = pair.getValue();
                iterator.remove();
            }
        }
        if ((!dateFrom.isEmpty()) && (!dateTill.isEmpty())) {
            dateTill = increaseDateByOneDay(dateTill);
            condition = "creationDate >= \'" + dateFrom + "\' AND creationDate < \'" + dateTill + "\'";
            params.put("creationDateRange", condition);
        }
        if (!creationDate.isEmpty()) {
            String creationDateTill = increaseDateByOneDay(creationDate);
            condition = "creationDate >= \'" + creationDate + "\' AND creationDate < \'" + creationDateTill + "\'";
            params.put("creationDate", condition);
        }
        if (!lastRepeatDate.isEmpty()) {
            String lastRepeatDateTill = increaseDateByOneDay(lastRepeatDate);
            condition = "lastRepeatDate >= \'" + creationDate + "\' AND lastRepeatDate < \'" + lastRepeatDateTill + "\'";
            params.put("lastRepeatDate", condition);
        }

    }

    public static String increaseDateByOneDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String increasedDate = "";
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(dateFormat.parse(date));
            c.add(Calendar.DATE, 1);
            return dateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return increasedDate;
    }
}
