package site.jsun999.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.jsun999.common.utils.CacheUtil;
import site.jsun999.mapper.BaseMapper;
import site.jsun999.mapper.PhotoMapper;
import site.jsun999.model.Category;
import site.jsun999.model.Photo;
import site.jsun999.service.CategoryService;
import site.jsun999.service.PhotoAlbumService;
import site.jsun999.web.exception.GlobalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CacheConfig(cacheNames = "photoCache")
@Service
public class PhotoAlbumServiceImpl extends BaseServiceImpl<Photo> implements PhotoAlbumService {

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Photo> getPyCategoryId(Integer categoryId, Integer pageNum, Integer pageSize, String title) throws GlobalException {
        return null;
    }

    @Override
    public List<Photo> getListPyPage(Integer status, Integer pageNum, Integer pageSize) throws GlobalException {
        return null;
    }

    @Override
    public List<Photo> queryByCategory(String categoryName, Integer pageNum, Integer pageSize) throws GlobalException {
        return null;
    }

    @Override
    public Photo getPreviousInfo(Integer id) throws GlobalException {
        return null;
    }

    @Override
    public Photo getNextInfo(Integer id) throws GlobalException {
        return null;
    }

    @Override
    public int getPhotoCount(Integer status) throws GlobalException {
        return 0;
    }

    @Override
    public void deleteBatch(String ids) throws GlobalException {

    }

    @Override
    public BaseMapper<Photo> getBaseMapper() {
        return null;
    }
}
