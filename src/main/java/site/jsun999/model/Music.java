package site.jsun999.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.jsun999.plugin.CreateTime;
import site.jsun999.plugin.UpdateTime;

/**
 * t_music
 * @author 
 */
@Table(name="t_music")
@Data
public class Music implements Serializable {
    @Id
    private Integer id;

    private String type;

    @NotEmpty
    private String link;

    private String songid;

    private String name;

    private String author;

    private String music;

    private String pic;

    private String album;

    @CreateTime
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @UpdateTime
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    private static final long serialVersionUID = 1L;
}