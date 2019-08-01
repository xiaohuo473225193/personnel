package manager.service;

import manager.mapper.CollegeMapper;
import manager.pojo.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaohuo
 * @data 2019/7/15 20:00
 * @description
 */
@Service
public class CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;
    /**
     * @return 查询所有的部门
     */
    public List<College> findByList(){
        return collegeMapper.selectAll();
    }
}
