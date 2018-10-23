package site.jsun999.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.jsun999.component.CommonMap;
import site.jsun999.component.MailService;
import site.jsun999.mapper.BaseMapper;
import site.jsun999.mapper.CommentMapper;
import site.jsun999.model.Comment;
import site.jsun999.service.CommentService;
import site.jsun999.web.exception.GlobalException;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private CommonMap commonMap;

    @Override
    public BaseMapper<Comment> getBaseMapper() {
        return commentMapper;
    }

    @Override
    public int getCommentCount() {
        return this.commentMapper.selectCount(null);
    }

    @Override
    public void deleteComment(Integer id) throws GlobalException {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setDelStatus(1);
        this.commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public List<Comment> getListPyPage(Integer delStatus,Integer type, Integer pageNum, Integer pageSize) throws GlobalException{
        if (pageNum < 1) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,pageSize);
        return this.commentMapper.getList(delStatus,type);
    }

    @Override
    public Integer getTotalCount(int delStatus) {
        return this.commentMapper.getTotalCount(delStatus);
    }

    @Override
    public void reply(Comment comment) throws GlobalException {
        comment.setNickname(commonMap.get("author").toString());
        comment.setEmail(commonMap.get("email").toString());
        comment.setStatus(0);
        comment.setDelStatus(0);
        comment.setType(2);
        this.commentMapper.insert(comment);

        // 发送邮件
        Comment gb = this.commentMapper.selectByPrimaryKey(comment.getCommentId());
        if (gb != null && !StringUtil.isEmpty(gb.getEmail())) {
            try {
                this.mailService.sendEmail(gb.getEmail(),comment.getNickname() + "的留言回复【"+commonMap.get("blogName").toString()+"】",comment.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Comment> getByPostId(Integer postId,int pageNum,int pageSize) throws GlobalException {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = this.commentMapper.getByPostId(postId);
        return list;
    }

    @Override
    public void save(Comment comment) throws GlobalException {

        Comment tmp = this.commentMapper.getByNickname(comment.getNickname());
        if (tmp != null) {
            throw new GlobalException(400,"昵称重复,请重新换一个昵称");
        }

        comment.setStatus(0);
        comment.setDelStatus(0);
        comment.setType(comment.getCommentId() == null ? 1 : 2);
        this.commentMapper.insert(comment);

        // 发送邮件给博主
        try {
            if (!StringUtils.isEmpty(this.commonMap.get("email"))) {
                this.mailService.sendEmail(this.commonMap.get("email").toString(),comment.getNickname() + "的留言",comment.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (comment.getCommentId() != null) {
            // 发送邮件给被回复者
            Comment gb = this.commentMapper.selectByPrimaryKey(comment.getCommentId());
            if (gb != null && !StringUtil.isEmpty(gb.getEmail())) {
                try {
                    this.mailService.sendEmail(gb.getEmail(),comment.getNickname() + "的留言回复【"+commonMap.get("blogName").toString()+"】",comment.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
