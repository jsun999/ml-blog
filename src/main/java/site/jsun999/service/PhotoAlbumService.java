package site.jsun999.service;


import site.jsun999.model.Photo;
import site.jsun999.web.exception.GlobalException;

import java.util.List;

public interface PhotoAlbumService extends BaseService<Photo> {


    /**
     * 获取相册列表
     * @param status 状态
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Photo> getListPyPage(Byte status, Integer pageNum, Integer pageSize) throws GlobalException;

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
