package vn.ptit.service.statistic;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.User;
import vn.ptit.repository.statistic.StatUserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatUserWithTotalMoneyService {
    private final StatUserRepository statUserRepository;

    public StatUserWithTotalMoneyService(StatUserRepository statUserRepository) {
        this.statUserRepository = statUserRepository;
    }
    public List<StatUserWithTotalMoneyService.Output> userWithTotalMoney() {
        return statUserRepository.userWithTotalMoney().stream().map(Output::createOutput).collect(Collectors.toList());
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
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
        @JsonProperty("total_money")
        @JsonAlias("totalMoney")
        private Double totalMoney;

        public static StatUserWithTotalMoneyService.Output createOutput(User user) {
            try {
                StatUserWithTotalMoneyService.Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(user), StatUserWithTotalMoneyService.Output.class);
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}