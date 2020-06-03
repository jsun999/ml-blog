package site.jsun999.service.impl;

import site.jsun999.common.utils.CacheUtil;
import site.jsun999.mapper.BaseMapper;
import site.jsun999.mapper.CategoryMapper;
import site.jsun999.model.Category;
import site.jsun999.service.CategoryService;
import site.jsun999.web.exception.GlobalException;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.Random;

@CacheConfig(cacheNames = "categoryCache")
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    // 颜色数组
    private static final String[] COLORS = {"text-default","text-primary","text-success","text-info","text-warning","text-danger","text-purple","text-pink"};

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public BaseMapper<Category> getBaseMapper() {
        return this.categoryMapper;
    }

    @Override
    public Category getCategoryByName(String categoryName) throws GlobalException {
        return this.categoryMapper.getByName(categoryName);
    }

    @Override
    public int getCategoryCount() throws GlobalException {
        return this.categoryMapper.selectCount(null);
    }

    @Cacheable(key = "'categoryList'")
    @Override
    public List<Map<String,Object>> getCategoryList() {
        return this.categoryMapper.getCategoryList();
    }

    @Transactional
    @Override
    public void save(Category category) throws GlobalException {

        if (StringUtils.isEmpty(category.getImgUrl())) {
            category.setImgUrl("/portal/images/category_default.jpg");
        }

        if (StringUtils.isEmpty(category.getColor())) {
            category.setColor(COLORS[new Random().nextInt(COLORS.length)]);
        }

        if (StringUtils.isEmpty(category.getSort())) {
            category.setSort(99);
        }

        this.categoryMapper.insert(category);
        CacheUtil.deleteByName("categoryCache");
    }

    @Transactional
    @Override
    public void update(Category category) throws GlobalException {

        if (StringUtils.isEmpty(category.getImgUrl())) {
            category.setImgUrl("/portal/images/category_default.jpg");
        }

        if (StringUtils.isEmpty(category.getColor())) {
            category.setColor(COLORS[new Random().nextInt(COLORS.length)]);
        }

        if (StringUtils.isEmpty(category.getSort())) {
            category.setSort(99);
        }

        this.categoryMapper.updateByPrimaryKeySelective(category);
        CacheUtil.deleteByName("categoryCache");
    }

    @Transactional
    @Override
    public void delete(Integer id) throws GlobalException {
        this.categoryMapper.deleteByPrimaryKey(id);
        CacheUtil.deleteByName("categoryCache");
    }

    @Override
    public List<Category> getList(String type) {
        Category category = new Category();
        category.setType(type);
        return this.categoryMapper.select(category);
    }

    @Override
    public List<Category> getPyPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return this.getList(null);
    }
}
