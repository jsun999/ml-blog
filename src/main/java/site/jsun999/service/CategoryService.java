package site.jsun999.service;

import site.jsun999.model.Category;
import site.jsun999.web.exception.GlobalException;

import java.util.List;
import java.util.Map;

public interface CategoryService extends BaseService<Category> {

    /**
     * 通过名称获取分类信息
     * @param categoryName
     * @return
     */
    Category getCategoryByName(String categoryName) throws GlobalException;

    /**
     * 分类个数
     * @return
     */
    int getCategoryCount() throws GlobalException;

    /**
     * 获取包含文章数的分类列表
     * @return
     */
    List<Map<String,Object>> getCategoryList();

    List<Category> getList(String type);

    List<Category> getPyPage(Integer pageNum, Integer pageSize);
}
