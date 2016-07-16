package org.wordscaster.logic;

/**
 * Created by A on 03.07.2016.
 */
public enum WordStatus {
    NEW, REPEAT, UNKNOWN, DONE;

    @Override
    public String toString() {
        switch (this){
            case NEW: return "new";
            case REPEAT: return "repeat";
            case UNKNOWN: return "unknown";
            case DONE: return "done";
            default: return "error";
        }
    }
}
