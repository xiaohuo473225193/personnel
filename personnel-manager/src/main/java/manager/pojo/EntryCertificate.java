package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author xiaohuo
 * @data 2019/7/15 11:24
 * @description 入职证书表
 */
@Data
@Table(name = "entry_certificate")
public class EntryCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String complete;
    private String entryNoticeImage;
    private String entryAgreeImage;
    private String secrecyImage;
    private String hireImage;
}
