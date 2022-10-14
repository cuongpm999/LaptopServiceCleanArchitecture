package vn.ptit.service.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

@Service
public class SignupUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupUserService(IUserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(CreateInput input){
        if(userRepository.getByEmail(input.email) != null){
            throw new InvalidRequestException("Email existed!");
        }
        if(userRepository.getByUsername(input.username) != null){
            throw new InvalidRequestException("Username existed!");
        }
        if(userRepository.getByMobile(input.mobile) != null){
            throw new InvalidRequestException("Mobile existed!");
        }
        User user = User.create(input.fullName, input.address, input.email, input.mobile, input.sex,
                input.dateOfBirth, input.username, input.password, "ROLE_USER", input.avatar);
        if(!user.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class CreateInput {
        @JsonAlias("full_name")
        private String fullName;
        private String address;
        private String email;
        private String mobile;
        private Boolean sex;
        @JsonAlias("date_of_birth")
        private String dateOfBirth;
        private String username;
        private String password;
        private String avatar;
    }
}
