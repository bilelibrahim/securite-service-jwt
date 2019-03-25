package org.sid.dao;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Bilel on 21/03/2019.
 */
@RepositoryRestResource
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    public AppRole findByRoleName(String roleName);
}
