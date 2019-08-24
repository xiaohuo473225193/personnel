package manager.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xiaohuo
 * @data 2019/7/15 11:24
 * @description 新闻表
 */
@Data
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long uid;
    private String status;
    private Date createTime;
    private Date updateTime;
    @Transient
    private String authorName;
}
