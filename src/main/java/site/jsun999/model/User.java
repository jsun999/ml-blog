package site.jsun999.model;

import site.jsun999.plugin.CreateTime;
import site.jsun999.plugin.UpdateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "t_user")
public class User {

    @Id
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    // 状态 1：正常 0：禁用
    private Integer status;

    @CreateTime
    private Date createTime;

    @UpdateTime
    private Date updateTime;
}
