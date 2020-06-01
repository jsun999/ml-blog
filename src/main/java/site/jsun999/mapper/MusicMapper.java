package site.jsun999.mapper;

import site.jsun999.model.Music;

public interface MusicMapper {

    int insert(Music record);

    int insertSelective(Music record);

}