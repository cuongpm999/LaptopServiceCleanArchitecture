package vn.ptit.service.user;

import java.util.List;

public interface IGetUserService {
    List<GetUserService.Output> getList(Integer page, Integer limit);
    GetUserService.Output getByUsername(String username);
    GetUserService.Output getByEmail(String email);
    GetUserService.Output getById(long id);
}
