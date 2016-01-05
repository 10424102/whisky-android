package org.team10424102.whisky.components.auth;

public interface AuthenticationService {

    /**
     *
     * @param auth
     * @return if the Authentication instance has been authenticated
     */
    boolean authenticate(Authentication auth);
}
