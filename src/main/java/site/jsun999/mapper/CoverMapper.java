package site.jsun999.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.jsun999.model.Cover;

import java.util.List;

@Mapper
public interface CoverMapper extends BaseMapper<Cover> {


    /**
     * 通过分类ID获取文章列表
     * @param categoryId
     * @return
     */
    List<Cover> queryCoverByCategoryId(@Param("categoryId") Integer categoryId, @Param("status") Integer status, @Param("title") String title);

    /**
     * 获取文章列表
     * @param status 状态 1:发布 2：操作 null：全部
     * @return
     */
    List<Cover> getList(@Param("status") Integer status);

    /**
     * 通过文章 URL 获取文章内容
     * @param coverUrl
     * @return
     */
    Cover getByCoverUrl(String coverUrl);

    /**
     * 获取上一篇文章
     * @param id
     * @return
     */
    Cover getPreviousInfo(Integer id);

    /**
     * 获取下一篇文章
     * @param id
     * @return
     */
    Cover getNextInfo(Integer id);

    /**
     * 批量删除
     * @param idList
     */
    void deleteBatch(@Param("idList") List<Integer> idList);

    /**
     * 插入前检测
     * @param cover
     */
    void checkInsert(Cover cover);

}
