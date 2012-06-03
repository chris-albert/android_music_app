package com.creasetoph.music.object;

/**
 * Abstract class for holding any info for a music item (Artist,Album,Track)
 * Every item has a name and getters and setter for it
 */
public abstract class MusicItem {

    //Name of the item Ex. (Artist name,Album name,Track name)
    protected String _name = "";

    /**
     * Getter for the name of the item
     * @return The item name
     */
    public String getName() {
        return _name;
    }

    /**
     * Setter for the name of the item
     * @param name The item name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Pretty printer for the item
     * @return The item name
     */
    public String toString() {
        return getName();
    }
}
