package site.jsun999.mapper;

import site.jsun999.model.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<Log> {
    void deleteAll();
}
