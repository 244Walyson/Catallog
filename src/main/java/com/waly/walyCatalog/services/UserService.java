package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.Repositories.RoleRepository;
import com.waly.walyCatalog.Repositories.UserRepository;
import com.waly.walyCatalog.dto.*;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Role;
import com.waly.walyCatalog.entities.User;
import com.waly.walyCatalog.projections.UserDetailsProjection;
import com.waly.walyCatalog.services.Exceptions.DatabaseException;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> result = repository.findAll(pageable);
        return result.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
      User dto = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(dto);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User user = new User();
        setDto(user, dto);

        user.getRoles().clear();
        Role role = roleRepository.findByAuthority("ROLE_OPERATOR");
        user.getRoles().add(role);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(UserUpdateDTO dto, Long id) {
        try{
            User prod = repository.getReferenceById(id);
            setDto(prod, dto);
            repository.save(prod);
            return new UserDTO(prod);
        }
        catch (EntityNotFoundException e){
            throw new NotFoundException("id not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new NotFoundException("resource not found");
        }
        try{
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("integrity violation");
        }
    }

    public User setDto(User user, UserDTO dto){
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()){
            Role role = roleRepository.getReferenceById(roleDto.getId());
            user.getRoles().add(role);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result){
           user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

}
