package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.User;

import java.util.Date;
import java.util.List;

public interface IGetUserService {
    List<Output> getList(Integer page, Integer limit, String sort);
    Output getByUsername(String username);
    Output getByEmail(String email);
    Output getById(long id);
    List<Output> search(Integer page, Integer limit, String sort, String searchText);

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    class Output {
        @JsonAlias("id")
        private Long id;
        @JsonProperty("full_name")
        @JsonAlias("fullName")
        private String fullName;
        @JsonAlias("address")
        private String address;
        @JsonAlias("email")
        private String email;
        @JsonAlias("mobile")
        private String mobile;
        @JsonAlias("sex")
        private Boolean sex;
        @JsonProperty("date_of_birth")
        @JsonAlias("dateOfBirth")
        private Date dateOfBirth;
        @JsonAlias("username")
        private String username;
        @JsonAlias("position")
        private String position;
        @JsonAlias("avatar")
        private String avatar;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        @JsonProperty("is_delete")
        @JsonAlias("isDelete")
        private Boolean isDelete;

        public static Output createOutput(User user) {
            try {
                return MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(user), Output.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
