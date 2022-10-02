package vn.ptit.repository.user;

import vn.ptit.model.QueryFilter;
import vn.ptit.model.User;

import java.util.List;

public interface IUserRepository {
    void save(User user);
    User getById(long id);
    User getByUsername(String username);
    User getByEmail(String email);
    User getByMobile(String mobile);
    List<User> findAll(QueryFilter filter);
}
