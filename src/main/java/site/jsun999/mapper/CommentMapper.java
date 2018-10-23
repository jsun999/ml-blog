package site.jsun999.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.jsun999.model.Comment;
import site.jsun999.model.Guestbook;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 获取留言列表
     * @param delStatus
     * @param type
     * @return
     */
    List<Comment> getList(@Param("delStatus") Integer delStatus, @Param("type") Integer type);

    /**
     * 留言总数
     * @param delStatus
     * @return
     */
    Integer getTotalCount(int delStatus);

    Comment getByNickname(String nickname);

    List<Comment> getByPostId(int postId);
}
