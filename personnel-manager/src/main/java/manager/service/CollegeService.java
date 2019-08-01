package manager.service;

import manager.mapper.CollegeMapper;
import manager.pojo.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Description:    部门服务层管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 15:01
* @UpdateUser:
* @UpdateDate:     2019/8/1 15:01
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;

    public List<College> findByList(){
        return collegeMapper.selectAll();
    }
}
