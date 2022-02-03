package com.lerkin.soundrewriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RewriteScriptInfo {
    private List<String> toFind;
    private List<String> founded;
    private List<String> notFound;

    public RewriteScriptInfo() {
        this.toFind = new ArrayList<>();
        this.founded = new ArrayList<>();
        this.notFound = new ArrayList<>();
    }

    public List<String> getToFind() {
        return toFind;
    }

    public List<String> getFounded() {
        return founded;
    }

    public List<String> getNotFound() {
        return notFound;
    }

    public void setToFind(List<String> toFind) {
        this.toFind = toFind;
    }

    public void setFounded(List<String> founded) {
        this.founded = founded;
    }

    public void setNotFound(List<String> notFound) {
        this.notFound = notFound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RewriteScriptInfo that = (RewriteScriptInfo) o;
        return Objects.equals(toFind, that.toFind) &&
                Objects.equals(founded, that.founded) &&
                Objects.equals(notFound, that.notFound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toFind, founded, notFound);
    }

    @Override
    public String toString() {
        return "RewriteScriptInfo{" +
                "toFind=" + toFind +
                "\nfounded=" + founded +
                "\nnotFound=" + notFound +
                '}';
    }
}
