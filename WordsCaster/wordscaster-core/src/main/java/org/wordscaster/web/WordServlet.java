package org.wordscaster.web;

import org.json.JSONObject;
import org.wordscaster.logic.ManagmentSystem;
import org.wordscaster.logic.Word;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by A on 27.06.2016.
 */
public class WordServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(addWord(req));
    }
    private Word prepareWord(HttpServletRequest req) {
        Word word = new Word();
        String wordName = req.getParameter("word");
        word.setWord(wordName);
        return word;
    }
    private JSONObject addWord(HttpServletRequest req) {
        Word word = prepareWord(req);
        ManagmentSystem ms = new ManagmentSystem();
        return ms.addWord(word);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
