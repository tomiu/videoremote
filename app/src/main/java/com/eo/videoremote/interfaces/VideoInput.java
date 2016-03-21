package com.eo.videoremote.interfaces;

import java.io.IOException;

/**
 * Created by tom on 21.3.2016.
 */
public interface VideoInput {
    String getPath();
    String getName();
    boolean isDirectory() throws IOException;
}
