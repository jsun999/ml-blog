package site.jsun999.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * t_music
 * @author 
 */
@Table(name="t_music")
@Data
public class Music implements Serializable {
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String type;

    private String link;

    private String songid;

    private String name;

    private String author;

    private String music;

    private String pic;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private static final long serialVersionUID = 1L;
}