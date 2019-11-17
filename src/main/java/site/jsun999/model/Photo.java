package site.jsun999.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import site.jsun999.plugin.CreateTime;
import site.jsun999.plugin.UpdateTime;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name="t_photo")
public class Photo {
    @Id
    private Integer id;

    @NotEmpty(message = "图片不能为空")
    private String imgUrl;

    private String description;

    private Byte status;

    @CreateTime
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @UpdateTime
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
