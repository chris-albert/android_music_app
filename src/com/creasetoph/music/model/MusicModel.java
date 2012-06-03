package com.creasetoph.music.model;

import com.creasetoph.music.object.Library;

/**
 * Base abstract class for music models
 */
public abstract class MusicModel {

    /**
     * Gets the library associated with this music model
     * @return Library
     */
    public abstract Library getLibrary();
}
