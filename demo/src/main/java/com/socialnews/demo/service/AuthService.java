package com.socialnews.demo.service;

import com.socialnews.demo.dto.AuthenticationResponse;
import com.socialnews.demo.dto.LoginRequestDto;
import com.socialnews.demo.dto.RegisterRequestDto;
import com.socialnews.demo.exceptions.SocialNewsException;
import com.socialnews.demo.model.NotificationEmail;
import com.socialnews.demo.model.User;
import com.socialnews.demo.model.VerificationToken;
import com.socialnews.demo.repository.UserRepository;
import com.socialnews.demo.repository.VerificationTokenRepository;
import com.socialnews.demo.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setEmail(registerRequestDto.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false); // Set as true when user is authenticated

        userRepository.save(user);

        String token = generateVerificationToken(user);

        // TODO fix broken mail logic
//        mailService.sendMail(new NotificationEmail("Please Activate your new Account",
//                user.getEmail(), "Thank you for signing up to Spring Social News, " +
//                "please click on the below url to activate your account : " +
//                "http://localhost:8080/api/auth/accountVerification/" + token));

        verifyAccount(token);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SocialNewsException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SocialNewsException(("User not found with username" +
                username)));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new AuthenticationResponse(token, loginRequestDto.getUsername());
    }

    @Transactional(readOnly = true)
    User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
}

