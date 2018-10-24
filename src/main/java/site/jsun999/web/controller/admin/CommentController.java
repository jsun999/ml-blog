package site.jsun999.web.controller.admin;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.jsun999.aspect.SysLog;
import site.jsun999.common.constant.PageConstant;
import site.jsun999.common.utils.IPUtil;
import site.jsun999.common.vo.Result;
import site.jsun999.model.Comment;
import site.jsun999.service.CommentService;
import site.jsun999.web.exception.GlobalException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 留言相关
 */
@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/list/{pageNum}/{delStatus}")
    public Result list(@PathVariable("pageNum") Integer pageNum, @PathVariable("delStatus") Integer delStatus) {

        try {
            List<Comment> list = this.commentService.getListPyPage(delStatus,null,pageNum, PageConstant.PAGE_SIZE);
            return Result.success(new PageInfo<>(list,10));
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }

    }

    @GetMapping("/get/{id}")
    public Result get(@PathVariable Integer id) {
        try {
            Comment comment = this.commentService.getById(id);
            return Result.success(comment);
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }

    @SysLog("回复留言")
    @PostMapping("/save")
    public Result save(Comment comment, HttpServletRequest request) {
        try {
            comment.setIp(IPUtil.getIpAddr(request));
            String city = IPUtil.getCity(comment.getIp());
            comment.setIpAddr(city == null ? "未知" : city);
            this.commentService.reply(comment);
            return Result.success();
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }

    @SysLog("删除留言")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            this.commentService.deleteComment(id);
            return Result.success();
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }
}
