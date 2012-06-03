package com.creasetoph.music;

public class LibraryItem implements Comparable<LibraryItem> {

    public enum Type {
        Artist,Album,Track
    }

    private Type _type;
    private String _value;

    public LibraryItem(Type type, String value) {
        setType(type);
        setValue(value);
    }

    public void setType(Type type) {
        _type = type;
    }

    public Type getType() {
        return _type;
    }

    public void setValue(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }

    public boolean typeEquals(Type type) {
        return _type == type;
    }

    public int compareTo(LibraryItem another) {
        return _value.compareTo(another.getValue());
    }
}
