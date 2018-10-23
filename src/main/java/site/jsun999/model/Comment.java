package site.jsun999.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import site.jsun999.plugin.CreateTime;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="t_comment")
public class Comment {

    @Id
    private Integer id;

    @NotNull
    private Integer postId;
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String homeUrl;

    private String imgUrl;

    @NotEmpty(message = "留言不能为空")
    private String content;

    private String ip;

    // ip 归属地
    private String ipAddr;

    // 1:已读 0：未读
    private Integer status;

    // 1：删除 0：未删除
    private Integer delStatus;

    // 1:留言 2：回复
    private Integer type;

    // 被回复者ID
    private Integer commentId;

    @CreateTime
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Transient
    private Comment comment;

    @Transient
    private List<Comment> replyList;
}
