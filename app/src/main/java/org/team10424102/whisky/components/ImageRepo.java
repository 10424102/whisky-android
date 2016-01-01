package org.team10424102.whisky.components;

public interface ImageRepo {

    byte[] getImage(String hash);

    void save(byte[] data);
}
