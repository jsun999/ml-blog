package site.jsun999.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jsun999.common.utils.CacheUtil;
import site.jsun999.component.AsyncService;
import site.jsun999.component.LuceneService;
import site.jsun999.mapper.BaseMapper;
import site.jsun999.mapper.CoverMapper;
import site.jsun999.model.Category;
import site.jsun999.model.Cover;
import site.jsun999.service.CategoryService;
import site.jsun999.service.CoverService;
import site.jsun999.web.exception.GlobalException;

import java.util.*;

@CacheConfig(cacheNames = "coverCache")
@Service
public class CoverServiceImpl extends BaseServiceImpl<Cover> implements CoverService {

    @Autowired
    private CoverMapper coverMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LuceneService luceneService;

    @Autowired
    private AsyncService asyncService;

    @Override
    public BaseMapper<Cover> getBaseMapper() {
        return this.coverMapper;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        this.coverMapper.deleteByPrimaryKey(id);

        // 删除索引
        this.luceneService.delete(id);
        // 清理缓存
        CacheUtil.deleteAll();
    }


    @Override
    public List<Cover> getPyCategoryId(Integer categoryId, Integer pageNum, Integer pageSize, String title) throws GlobalException {
        PageHelper.startPage(pageNum, pageSize);
        List<Cover> list = this.coverMapper.queryCoverByCategoryId(categoryId, null, title);
        return list;
    }

    @Cacheable(key = "'page:' + #pageNum")
    @Override
    public List<Cover> getListPyPage(Integer status, Integer pageNum, Integer pageSize) throws GlobalException {
        PageHelper.startPage(pageNum, pageSize);
        return this.coverMapper.getList(status);
    }

    @Cacheable(key = "'cover:list' + #categoryName + ':' + #pageNum")
    @Override
    public List<Cover> queryByCategory(String categoryName, Integer pageNum, Integer pageSize) throws GlobalException {
        Category category = this.categoryService.getCategoryByName(categoryName);

        if (category == null) {
            return null;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Cover> list = this.coverMapper.queryCoverByCategoryId(category.getId(), 1, null);

        return list;
    }

    @Cacheable(key = "'coverContent:' + #coverUrl")
    @Override
    public Cover getByCoverUrl(String coverUrl) throws GlobalException {
        return this.coverMapper.getByCoverUrl(coverUrl);
    }

    @Cacheable(key = "'previousInfo:' + #id")
    @Override
    public Cover getPreviousInfo(Integer id) throws GlobalException {
        return this.coverMapper.getPreviousInfo(id);
    }

    @Cacheable(key = "'nextInfo:' + #id")
    @Override
    public Cover getNextInfo(Integer id) throws GlobalException {
        return this.coverMapper.getNextInfo(id);
    }

    @Override
    public int getCoverCount(Integer status) throws GlobalException {
        Cover cover = null;
        if (status != null) {
            cover = new Cover();
            cover.setStatus(status);
        }
        return this.coverMapper.selectCount(cover);
    }


    @Override
    public void deleteBatch(String ids) throws GlobalException {
        String[] idArr = ids.split(",");
        List<Integer> idList = new ArrayList<>(idArr.length);
        for (String id : idArr) {
            idList.add(Integer.parseInt(id));
            // 删除索引
            this.luceneService.delete(Integer.parseInt(id));
        }
        // 批量删除
        this.coverMapper.deleteBatch(idList);
        // 清理缓存
        CacheUtil.deleteAll();
    }

}
