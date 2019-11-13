package site.jsun999.service;


import site.jsun999.model.Photo;
import site.jsun999.web.exception.GlobalException;

import java.util.List;

public interface PhotoAlbumService extends BaseService<Photo> {


    /**
     * 通过分类ID获取Photo列表
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    List<Photo> getPyCategoryId(Integer categoryId, Integer pageNum, Integer pageSize, String title) throws GlobalException;

    /**
     * 获取封面列表
     * @param status 状态
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Photo> getListPyPage(Integer status, Integer pageNum, Integer pageSize) throws GlobalException;

    /**
     * 通过分类获取文章列表
     * @param categoryName
     * @return
     */
    List<Photo> queryByCategory(String categoryName, Integer pageNum, Integer pageSize) throws GlobalException;

    /**
     * 获取上一篇文章
     * @param id
     * @return
     */
    Photo getPreviousInfo(Integer id) throws GlobalException;

    /**
     * 获取下一篇文章
     * @param id
     * @return
     */
    Photo getNextInfo(Integer id) throws GlobalException;

    /**
     * 发表文章数
     * @param status 1:显示 0：隐藏  null:全部
     * @return
     * @throws GlobalException
     */
    int getPhotoCount(Integer status) throws GlobalException;

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(String ids) throws GlobalException;


}
