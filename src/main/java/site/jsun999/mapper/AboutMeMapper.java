package site.jsun999.mapper;

import site.jsun999.model.AboutMe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AboutMeMapper extends BaseMapper<AboutMe> {

    /**
     * 通过状态获取
     * @param  status
     * @return
     */
    AboutMe getByStatus(@Param("status") Integer status);
}
