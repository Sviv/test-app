package org.wordscaster.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wordscaster.logic.ManagmentSystem;
import org.wordscaster.logic.Word;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by A on 27.06.2016.
 */
public class WordServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String method = req.getParameter("method");
        PrintWriter out = resp.getWriter();
        switch (method) {
            case "addWord": {
                out.print(addWord(req));
            }
                break;
            case "searchWord": {
                out.print(searchWord(req));
            }
                break;
            case "findWordById": {
                out.print(findWordById(req));
            }
                break;
            case "modifyWord": {
                out.print(modifyWord(req));
            }
            break;
            case "deleteWord": {
                out.print(deleteWord(req));
            }
            break;
            default:
                break;
        }
    }
    private JSONArray addWord(HttpServletRequest req) {
        Word word = prepareWord(req);
        ManagmentSystem ms = new ManagmentSystem();
        return ms.addWord(word);
    }
    private JSONArray searchWord(HttpServletRequest req) {
        HashMap<String, String> params = prepareParams(req);
        ManagmentSystem ms = new ManagmentSystem();
        return ms.searchWordsByParams(params);
    }
    private JSONArray findWordById(HttpServletRequest req) {
        String id = prepareId(req);
        ManagmentSystem ms = new ManagmentSystem();
        return ms.findWordById(id);
    }
    private JSONArray modifyWord(HttpServletRequest req) {
        HashMap<String, String> params = prepareParams(req);
        ManagmentSystem ms = new ManagmentSystem();
        return ms.modifyWord(params);
    }
    private JSONArray deleteWord(HttpServletRequest req) {
        String id = prepareId(req);
        ManagmentSystem ms = new ManagmentSystem();
        return ms.deleteWord(id);
    }
    private Word prepareWord(HttpServletRequest req) {
        Word word = new Word();
        String wordName = req.getParameter("word");
        word.setWord(wordName);
        return word;
    }
    private HashMap<String, String> prepareParams(HttpServletRequest req) {
        HashMap<String, String> params = new HashMap<String, String>();
        addToMap(params, "word", req.getParameter("word"));
        addToMap(params, "creationDate", req.getParameter("creationDate"));
        addToMap(params, "dateFrom", req.getParameter("dateFrom"));
        addToMap(params, "dateTill", req.getParameter("dateTill"));
        addToMap(params, "translation", req.getParameter("translation"));
        addToMap(params, "lastRepeatDate", req.getParameter("lastRepeatDate"));
        addToMap(params, "repeatsCount", req.getParameter("repeatsCount"));
        addToMap(params, "status", req.getParameter("status"));
        addToMap(params, "wordId", req.getParameter("wordId"));
        return params;
    }
    private String prepareId(HttpServletRequest req) {
        return req.getParameter("wordId");
    }
    private static void addToMap(HashMap<String, String> params, String paramKey, String paramValue) {
        if (paramValue != null) {
            params.put(paramKey, paramValue);
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
