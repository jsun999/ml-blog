package site.jsun999.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.jsun999.model.Cover;
import site.jsun999.model.Photo;

import java.util.List;

@Mapper
public interface PhotoMapper extends BaseMapper<Photo>{


    List<Photo> getList(@Param("status")Byte status);
}
