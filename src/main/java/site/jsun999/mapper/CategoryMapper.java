package site.jsun999.mapper;

import site.jsun999.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 通过名称获取分类信息
     * @param categoryName
     * @return
     */
    Category getByName(String categoryName);

    /**
     * 获取包含文章数的分类列表
     * @return
     */
    List<Map<String,Object>> getCategoryList();
}
