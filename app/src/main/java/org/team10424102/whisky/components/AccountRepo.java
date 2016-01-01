package org.team10424102.whisky.components;

import java.util.List;

public interface AccountRepo {
    List<Account> all();
    void save(Account account);
}
