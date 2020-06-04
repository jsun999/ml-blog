package site.jsun999.service;

import site.jsun999.common.vo.MusicVo;
import site.jsun999.model.Music;
import site.jsun999.web.exception.GlobalException;

import java.util.List;
import java.util.Map;

public interface MusicService extends BaseService<Music> {


    /**
     * 分类个数
     * @return
     */
    int getMusicCount() throws GlobalException;

    List<MusicVo> getList(String album);
}
