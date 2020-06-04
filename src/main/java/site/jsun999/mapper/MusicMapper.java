package site.jsun999.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.jsun999.common.vo.MusicVo;
import site.jsun999.model.Music;

import java.util.List;

/**
 * @author jsun999
 */
@Mapper
public interface MusicMapper extends BaseMapper<Music>{

    List<MusicVo> selectByAlbum(String album);
}