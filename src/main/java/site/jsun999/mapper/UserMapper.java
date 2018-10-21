package site.jsun999.mapper;

import site.jsun999.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
