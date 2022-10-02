package vn.ptit.service.user;

import java.util.List;

public interface IGetUserService {
    List<GetUserService.Output> getList();
    GetUserService.Output getByUsername(String username);
    GetUserService.Output getByEmail(String email);
    GetUserService.Output getByMobile(String mobile);
}
