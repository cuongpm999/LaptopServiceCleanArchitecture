package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.QueryFilter;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserService implements IGetUserService {
    private final IUserRepository userRepository;

    public GetUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Output> getList(Integer page, Integer limit, String sort) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        return userRepository.findAll(filter).stream().map(Output::createOutput).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public Output getByUsername(String username) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new DataNotFoundException("User not found");
        }
        return Output.createOutput(user);
    }

    @SneakyThrows
    @Override
    public Output getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new DataNotFoundException("User not found");
        }
        return Output.createOutput(user);
    }

    @SneakyThrows
    @Override
    public Output getById(long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new DataNotFoundException("User not found");
        }
        return Output.createOutput(user);
    }

    @Override
    public List<Output> search(Integer page, Integer limit, String sort, String searchText) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        return userRepository.search(filter, searchText).stream().map(Output::createOutput).collect(Collectors.toList());
    }
}
