package com.creasetoph.music.object;

import com.creasetoph.music.util.Logger;

/**
 * Abstract class for holding any info for a music item (Artist,Album,Track)
 * Every item has a name and getters and setter for it
 */
public abstract class MusicItem {

    //Name of the item Ex. (Artist name,Album name,Track name)
    protected String _name = "";
    protected long _size = -1;
    protected String _path = null;

    protected boolean _opened = false;

    public void open() {
        Logger.info("Opening " + getName());
        _opened = true;
    }

    public void close() {
        Logger.info("Closing " + getName());
        _opened = false;
    }

    public boolean opened() {
        return _opened;
    }

    public MusicItem setSize(long size) {
        _size = size;
        return this;
    }

    public MusicItem setPath(String path) {
        _path = path;
        return this;
    }

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
