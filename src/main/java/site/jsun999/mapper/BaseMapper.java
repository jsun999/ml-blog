package site.jsun999.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T>{
}
