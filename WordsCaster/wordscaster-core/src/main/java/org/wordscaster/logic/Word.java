package org.wordscaster.logic;

import java.util.Date;

/**
 * Created by A on 27.06.2016.
 */
public class Word {
    private int wordId;
    private String word;
    private Date creationDate;
    private Date lastRepeatDate;
    private int repeatsCount;
    private WordStatus status;

    public Word() {
        this.status = WordStatus.NEW;
        this.creationDate = new Date();
        this.repeatsCount = 0;
        this.lastRepeatDate = new Date();
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

    public Date getLastRepeatDate() {
        return lastRepeatDate;
    }

    public void setLastRepeatDate(Date lastRepeatDate) {
        this.lastRepeatDate = lastRepeatDate;
    }

    public int getRepeatsCount() {
        return repeatsCount;
    }

    public void setRepeatsCount(int repeatsCount) {
        this.repeatsCount = repeatsCount;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String stringStatus) {
        switch (stringStatus) {
            case "new":
                this.status = WordStatus.NEW;
                break;
            case "repeat":
                this.status = WordStatus.REPEAT;
                break;
            case "unknown":
                this.status = WordStatus.UNKNOWN;
                break;
            case "done":
                this.status = WordStatus.DONE;
                break;
            default:
                break;
        }
    }

    public String toString() {
        return this.word;
    }
}
