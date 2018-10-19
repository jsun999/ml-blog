package site.jsun999.service.impl;

import site.jsun999.mapper.BaseMapper;
import site.jsun999.mapper.UserMapper;
import site.jsun999.model.User;
import site.jsun999.service.UserService;
import site.jsun999.web.exception.GlobalException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) throws GlobalException {
        return this.userMapper.getByUsername(username);
    }

    @Override
    public void updatePwd(String username, String oldpwd, String newpwd) throws GlobalException {

        User user = this.findByUsername(username);
        if (user == null) {
            throw new GlobalException(403,"用户名不存在");
        }

        if(!user.getPassword().equals(DigestUtils.md5Hex(oldpwd))) {
            throw new GlobalException(403,"旧密码不正确");
        }

        User tmp = new User();
        tmp.setId(user.getId());
        tmp.setPassword(DigestUtils.md5Hex(newpwd));

        this.userMapper.updateByPrimaryKeySelective(tmp);
    }


    @Override
    public BaseMapper<User> getBaseMapper() {
        return this.userMapper;
    }
}
