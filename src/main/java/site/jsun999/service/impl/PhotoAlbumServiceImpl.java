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
    public BaseMapper<Photo> getBaseMapper() {
        return this.photoMapper;
    }

    @Transactional
    @Override
    public void save(Photo photo) throws GlobalException {

        Category category = this.categoryService.getById(photo.getCategoryId());
        if (category == null) {
            throw new GlobalException(400, "该分类不存在");
        }

        if(StringUtils.isEmpty(photo.getImgUrl())) {
            photo.setImgUrl("material-" + (new Random().nextInt(30) + 1) + ".jpg");
        }
        photo.setCategoryName(category.getName());
        this.photoMapper.insert(photo);
        // 清理缓存
        CacheUtil.deleteAll();
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        this.photoMapper.deleteByPrimaryKey(id);

        // 清理缓存
        CacheUtil.deleteAll();
    }


    @Override
    public List<Photo> getPyCategoryId(Integer categoryId, Integer pageNum, Integer pageSize, String title) throws GlobalException {
        PageHelper.startPage(pageNum, pageSize);
        List<Photo> list = this.photoMapper.queryPhotoByCategoryId(categoryId, null, title);
        return list;
    }

    @Cacheable(key = "'page:' + #pageNum")
    @Override
    public List<Photo> getListPyPage(Integer status, Integer pageNum, Integer pageSize) throws GlobalException {
        PageHelper.startPage(pageNum, pageSize);
        return this.photoMapper.getList(status);
    }

    @Cacheable(key = "'Photo:list' + #categoryName + ':' + #pageNum")
    @Override
    public List<Photo> queryByCategory(String categoryName, Integer pageNum, Integer pageSize) throws GlobalException {
        Category category = this.categoryService.getCategoryByName(categoryName);

        if (category == null) {
            return null;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Photo> list = this.photoMapper.queryPhotoByCategoryId(category.getId(), 1, null);

        return list;
    }

    @Cacheable(key = "'previousInfo:' + #id")
    @Override
    public Photo getPreviousInfo(Integer id) throws GlobalException {
        return this.photoMapper.getPreviousInfo(id);
    }

    @Cacheable(key = "'nextInfo:' + #id")
    @Override
    public Photo getNextInfo(Integer id) throws GlobalException {
        return this.photoMapper.getNextInfo(id);
    }

    @Override
    public int getPhotoCount(Integer status) throws GlobalException {
        Photo photo = null;
        if (status != null) {
            photo = new Photo();
            photo.setStatus(status);
        }
        return this.photoMapper.selectCount(photo);
    }


    @Override
    public void deleteBatch(String ids) throws GlobalException {
        String[] idArr = ids.split(",");
        List<Integer> idList = new ArrayList<>(idArr.length);
        for (String id : idArr) {
            idList.add(Integer.parseInt(id));
        }
        // 批量删除
        this.photoMapper.deleteBatch(idList);
        // 清理缓存
        CacheUtil.deleteAll();
    }

}
