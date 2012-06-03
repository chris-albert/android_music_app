package com.creasetoph.music.item;

/**
 * Holds items for the library list view
 */
public class LibraryItem implements Comparable<LibraryItem> {

    /**
     * Type fo library item
     */
    public enum Type {
        Artist,Album,Track
    }

    //The LibraryItem type
    private Type _type;
    //The value
    private String _value;

    /**
     * Constructor set the type and value
     * @param type The type
     * @param value The value
     */
    public LibraryItem(Type type, String value) {
        setType(type);
        setValue(value);
    }

    /**
     * Setter for the type
     * @param type Type of LibraryItem
     */
    public void setType(Type type) {
        _type = type;
    }

    /**
     * Getter for the type
     * @return Type of LibraryItem
     */
    public Type getType() {
        return _type;
    }

    /**
     * Setter for the value
     * @param value LibraryItem Value
     */
    public void setValue(String value) {
        _value = value;
    }

    /**
     * Getter for the value
     * @return LibraryItem value
     */
    public String getValue() {
        return _value;
    }

    /**
     * Checks if the passed in type is equal to this objects
     * @param type Type to check if equal to this objects
     * @return True if type are the same
     */
    public boolean typeEquals(Type type) {
        return _type == type;
    }

    /**
     * Comparator so we can sort library items
     * @param another Other PlaylistItem to compare to
     * @return 0 if the strings are equal, a negative integer if this string is
     *         before the specified string, or a positive integer if this string is
     *         after the specified string.
     */
    public int compareTo(LibraryItem another) {
        return _value.compareTo(another.getValue());
    }
}
