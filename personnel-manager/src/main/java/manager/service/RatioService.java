package manager.service;

import manager.mapper.RatioMapper;
import manager.pojo.EducationRatio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatioService {
    @Autowired
    private RatioMapper ratioMapper;

    public List<Map<String,Object>> analyzeEducationRatio() {
        List<EducationRatio> educationRatios = ratioMapper.selectAll();
        List<Map<String,Object>> results = new ArrayList<>();
        if(!CollectionUtils.isEmpty(educationRatios)){
            EducationRatio ratio = educationRatios.get(0);
            Map<String,Object> juniorhighMap = new HashMap<>();
            juniorhighMap.put("name","初中");
            juniorhighMap.put("value",ratio.getJuniorhighSchoolSum());
            results.add(juniorhighMap);

            Map<String,Object> technicalMap = new HashMap<>();
            technicalMap.put("name","中专");
            technicalMap.put("value",ratio.getTechnicalSecondarySchoolSum());
            results.add(technicalMap);

            Map<String,Object> seniorsighMap = new HashMap<>();
            seniorsighMap.put("name","高中");
            seniorsighMap.put("value",ratio.getSeniorsighSchoolSum());
            results.add(seniorsighMap);

            Map<String,Object> juniorMap = new HashMap<>();
            juniorMap.put("name","大学专科");
            juniorMap.put("value",ratio.getJuniorCollegeSum());
            results.add(juniorMap);

            Map<String,Object> bachelorMap = new HashMap<>();
            bachelorMap.put("name","大学本科");
            bachelorMap.put("value",ratio.getBachelorDegreeSum());
            results.add(bachelorMap);

            Map<String,Object> masterMap = new HashMap<>();
            masterMap.put("name","硕士");
            masterMap.put("value",ratio.getMasterSum());
            results.add(masterMap);

            Map<String,Object> doctorMap = new HashMap<>();
            doctorMap.put("name","博士");
            doctorMap.put("value",ratio.getDoctorSum());
            results.add(doctorMap);

            Map<String,Object> postdoctorMap = new HashMap<>();
            postdoctorMap.put("name","博士后");
            postdoctorMap.put("value",ratio.getPostdoctorSum());
            results.add(postdoctorMap);
        }
        return results;
    }

}
