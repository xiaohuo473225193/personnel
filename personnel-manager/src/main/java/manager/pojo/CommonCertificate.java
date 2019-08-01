package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author xiaohuo
 * @data 2019/7/15 11:24
 * @description 常规证书表
 */
@Data
@Table(name = "common_certificate")
public class CommonCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String complete;
    private String employImage;//
    private String identityImage;
    private String degreeImage;
    private String educationImage;
    private String postImage;
    private String backgroundImage;
}
