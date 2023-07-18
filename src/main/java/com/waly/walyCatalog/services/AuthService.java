package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.PasswordRecoverRepository;
import com.waly.walyCatalog.Repositories.UserRepository;
import com.waly.walyCatalog.dto.EmailDTO;
import com.waly.walyCatalog.dto.NewPasswordDTO;
import com.waly.walyCatalog.entities.PasswordRecover;
import com.waly.walyCatalog.entities.User;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void createrecoverToken(EmailDTO body) {
        User user = userRepository.findByEmail(body.getEmail());
        if(user==null){
            throw new NotFoundException("Email não encontrado");
        }

        String uuid = UUID.randomUUID().toString();

        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(body.getEmail());
        entity.setToken(uuid);
        entity.setExpiration(Instant.now().plusSeconds((tokenMinutes*60)));
        entity = passwordRecoverRepository.save(entity);

        String message = "Acesse o link para definir uma nova senha\n\n"
                + recoverUri + uuid + "\n\ntoken validor por" + tokenMinutes;

        emailService.sendEmail(body.getEmail(), "Recuperação de senha", message);
    }

    @Transactional
    public void updatePassword(NewPasswordDTO body) {
        List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
        if (result.isEmpty()){
            throw new NotFoundException("token invalido");
        }

        User user = userRepository.findByEmail(result.get(0).getEmail());
        String password = passwordEncoder.encode(body.getPassword());
        user.setPassword(password);
        user = userRepository.save(user);
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }


}
