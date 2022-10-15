package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

@Service
public class EditProfileUserService {
    private final IUserRepository userRepository;

    public EditProfileUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public void editProfile(EditProfileInput input){
        User user = userRepository.getById(input.id);
        if(user == null){
            throw new DataNotFoundException("User not found");
        }
        if(userRepository.getByMobile(input.mobile) != null && !input.mobile.equals(user.getMobile())){
            throw new InvalidRequestException("Mobile existed!");
        }
        user.update(input.fullName, input.address, input.mobile, input.sex, input.dateOfBirth,
                user.getPosition(), input.avatar);
        userRepository.save(user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class EditProfileInput {
        public Long id;
        @JsonProperty("full_name")
        private String fullName;
        private String address;
        private String mobile;
        private Boolean sex;
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
        private String avatar;
    }
}
