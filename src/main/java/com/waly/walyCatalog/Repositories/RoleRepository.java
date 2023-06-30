package com.waly.walyCatalog.Repositories;

import com.waly.walyCatalog.entities.Role;
import com.waly.walyCatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);
}
