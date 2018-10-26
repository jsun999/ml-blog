package site.jsun999.service;


import site.jsun999.model.Cover;
import site.jsun999.web.exception.GlobalException;

import java.util.List;

public interface CoverService extends BaseService<Cover> {


    /**
     * 通过分类ID获取文章列表
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    List<Cover> getPyCategoryId(Integer categoryId, Integer pageNum, Integer pageSize, String title) throws GlobalException;

    /**
     * 获取封面列表
     * @param status 状态
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Cover> getListPyPage(Integer status, Integer pageNum, Integer pageSize) throws GlobalException;

    /**
     * 通过分类获取文章列表
     * @param categoryName
     * @return
     */
    List<Cover> queryByCategory(String categoryName, Integer pageNum, Integer pageSize) throws GlobalException;

    /**
     * 通过 文章URL 获取文章内容
     * @param postUrl
     * @return
     * @throws GlobalException
     */
    Cover getByCoverUrl(String postUrl) throws GlobalException;

    /**
     * 获取上一篇文章
     * @param id
     * @return
     */
    Cover getPreviousInfo(Integer id) throws GlobalException;

    /**
     * 获取下一篇文章
     * @param id
     * @return
     */
    Cover getNextInfo(Integer id) throws GlobalException;

    /**
     * 发表文章数
     * @param status 1:显示 0：隐藏  null:全部
     * @return
     * @throws GlobalException
     */
    int getCoverCount(Integer status) throws GlobalException;

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(String ids) throws GlobalException;


}
