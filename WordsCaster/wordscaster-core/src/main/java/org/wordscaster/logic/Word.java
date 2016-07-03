package org.wordscaster.logic;

import java.util.Date;

/**
 * Created by A on 27.06.2016.
 */
public class Word {
    private int wordId;
    private String word;
    private Date creationDate;
    private int status;
    public Word(){
        this.status = 1;
        this.creationDate = new Date();
    }
    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString() {
        return this.word;
    }
}
