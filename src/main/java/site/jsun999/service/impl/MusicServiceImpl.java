package site.jsun999.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.jsun999.common.utils.CacheUtil;
import site.jsun999.common.vo.MusicVo;
import site.jsun999.mapper.BaseMapper;
import site.jsun999.mapper.MusicMapper;
import site.jsun999.model.Music;
import site.jsun999.service.MusicService;
import site.jsun999.web.exception.GlobalException;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MusicServiceImpl extends BaseServiceImpl<Music> implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public BaseMapper<Music> getBaseMapper() {
        return this.musicMapper;
    }


    @Override
    public int getMusicCount() throws GlobalException {
        return this.musicMapper.selectCount(null);
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public void save(Music music) throws GlobalException {
        this.musicMapper.insert(music);
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public void update(Music music) throws GlobalException {
        this.musicMapper.updateByPrimaryKeySelective(music);
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public void delete(Integer id) throws GlobalException {
        this.musicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Music> getPyPage(Integer pageNum, Integer pageSize) throws GlobalException {
        PageHelper.startPage(pageNum,pageSize);
        return this.musicMapper.selectAll();
    }

    @Override
    public List<MusicVo> getList(String album) {
        return this.musicMapper.selectByAlbum(album);
    }
}
