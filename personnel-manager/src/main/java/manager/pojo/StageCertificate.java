package manager.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xiaohuo
 * @data 2019/7/15 12:10
 * @description 阶段性证书
 */
@Data
@Table(name = "stage_certificate")
public class StageCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String complete;
    private String honorImage;
    private String postEvaluateImage;
    private String applyImage;
    private String stageEvaluateImage;
    private String workSummaryImage;
    private String synthesizeEvaluteImage;
    private String assessImage;
    private String tarnsferApproverImage;
    private String changeApproverImage;
    private String appointImage;
    private String leaveReportImage;
    private String trainApplyImage;
    private String trainServiceImage;
    private String yearInterviewImage;
    private String awardDispositionImage;
}
