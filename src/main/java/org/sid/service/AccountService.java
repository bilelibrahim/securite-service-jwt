package org.sid.service;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;

/**
 * Created by Bilel on 21/03/2019.
 */
public interface AccountService {
    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole save(AppRole appRole);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username, String roleName);
}
