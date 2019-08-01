package manager.controller;

import manager.pojo.EntryCertificate;
import manager.service.EntryCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Result;

import java.util.List;

/**
 * @author xiaohuo
 * @data 2019/7/15 13:13
 * @description
 */
@RestController
@RequestMapping("/entry")
public class EntryCertificateController {
    @Autowired
    private EntryCertificateService entryCertificateService;
    @GetMapping("/list")
    public Result<List<EntryCertificate>> findByList(){
        List<EntryCertificate> entryCertificateList = entryCertificateService.findByList();
        return new Result(entryCertificateList);
    }
}
