package site.jsun999.service;


import site.jsun999.model.Comment;
import site.jsun999.web.exception.GlobalException;

import java.util.List;

public interface CommentService extends BaseService<Comment>{

    /**
     * 留言个数
     * @return
     */
    int getCommentCount() throws GlobalException;

    /**
     *  软删除
     * @param id
     */
    void deleteComment(Integer id) throws GlobalException;

    /**
     * 获取留言列表
     * @param delStatus 1:删除 0：未删除
     * @param type      1:留言 2：回复
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Comment> getListPyPage(Integer delStatus, Integer type, Integer pageNum, Integer pageSize) throws GlobalException;

    /**
     *  留言总数
     * @param delStatus
     * @return
     */
    Integer getTotalCount(int delStatus) throws GlobalException;

    /**
     * 管理员回复留言
     * @param Comment
     */
    void reply(Comment Comment) throws GlobalException;

    /**
     * 根据文章Id查询留言
     * @param postId
     * @return
     * @throws GlobalException
     */
    List<Comment> getByPostId(Integer postId,int pageNum,int pageSize) throws GlobalException;
}
