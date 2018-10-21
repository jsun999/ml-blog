package site.jsun999.service;

import site.jsun999.model.User;
import site.jsun999.web.exception.GlobalException;

public interface UserService extends BaseService<User>{

    User findByUsername(String username) throws GlobalException;

    /**
     * 修改密码
     * @param username 用户名
     * @param oldpwd   旧密码
     * @param newpwd   新密码
     */
    void updatePwd(String username, String oldpwd, String newpwd) throws GlobalException;
}
