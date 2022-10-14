package vn.ptit.service.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;
import vn.ptit.security.JwtTokenProvider;
import vn.ptit.service.user.IGetUserService;
import vn.ptit.util.ULID;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoginUserService {
    private static final int LOGIN_NORMAL = 1;
    private static final int LOGIN_SOCIAL = 2;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final IUserRepository userRepository;
    private final ULID ulid = new ULID();
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public AuthOutput login(AuthInput input) {
        if(input.type == null){
            throw new InvalidRequestException("Require [type]");
        }
        if(input.type > 2 || input.type < 1){
            throw new InvalidRequestException("[type] must be 1 or 2");
        }
        if (input.type == LOGIN_NORMAL) {
            if(input.username == null){
                throw new InvalidRequestException("Require [username]");
            }
            if(input.password == null){
                throw new InvalidRequestException("Require [password]");
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    input.username, input.password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.generateToken(authentication);

            return AuthOutput.create(token, input.username);
        } else {
            if(input.email == null){
                throw new InvalidRequestException("Require [email]");
            }
            if(input.fullName == null){
                throw new InvalidRequestException("Require [fullName]");
            }
            if(input.avatar == null){
                throw new InvalidRequestException("Require [avatar]");
            }

            User user = userRepository.getByEmail(input.email);
            if (user == null) {
                String username = String.format("%s_%s", "user", ulid.nextULID());
                user = User.create(input.fullName, "N/A", input.email, "N/A", false, "2000-01-01", username, "N/A", "ROLE_USER", input.avatar);
                userRepository.save(user);
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getPosition()));
            UserDetails userDetail = new org.springframework.security.core.userdetails.User(user.getUsername(), "N/A", authorities);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                    null, userDetail.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.generateToken(authentication);

            return AuthOutput.create(token, user.getUsername());
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthInput {
        private String username;
        private String password;
        private Integer type;
        @JsonAlias("full_name")
        private String fullName;
        private String email;
        private String avatar;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthOutput {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType = "Bearer";
        private String username;

        public AuthOutput(String accessToken, String username) {
            this.accessToken = accessToken;
            this.username = username;
        }

        public static AuthOutput create(String accessToken, String username) {
            return new AuthOutput(accessToken, username);
        }
    }
}
