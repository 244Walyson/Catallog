package com.waly.walyCatalog.Repositories;

import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
