package org.team10424102.whisky.components;

public interface Authentication {
    boolean isAuthenticated();

    /**
     *
     * @return if this Authentication instance has been authenticated
     */
    boolean authenticate();
}
