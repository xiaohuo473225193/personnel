package manager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import manager.bo.SelectOptionData;
import manager.mapper.CollegeMapper;
import manager.pojo.*;
import manager.vo.CollegeUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Code;
import util.MD5Util;
import util.PException;
import util.PageResult;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    @Autowired
    private BaseService baseService;

    @Autowired
    private UserService userService;
    @Autowired
    private CommonCertificateService commonCertificateService;
    @Autowired
    private EntryCertificateService entryCertificateService;
    @Autowired
    private StageCertificateService stageCertificateService;

    public List<College> findByList(){
        return collegeMapper.selectAll();
    }

    public PageResult<CollegeUser> findCollegeUserListByCid(Long cid, SelectOptionData data,int rows,int size){
        PageHelper.startPage(rows,size);
        //根据cid找到user集合
        Page<User> users = (Page<User>)userService.findByCid(cid,data);
        List<User> userList = users.getResult();

        List<CollegeUser> collegeUsers = new ArrayList<CollegeUser>();
        for (User user : userList) {
            CollegeUser collegeUser = userPackaging(user);
            collegeUsers.add(collegeUser);
        }

        return new PageResult<>(users.getTotal(),collegeUsers);
    }

    public CollegeUser userPackaging(User user){
        CollegeUser collegeUser = new CollegeUser();

        collegeUser.setCollegeUserId(user.getUid());
        collegeUser.setCollegeUserName(user.getName());
        collegeUser.setJobNumber(user.getJobNumber());
        collegeUser.setIdentityCard(user.getIdentityCard());
        collegeUser.setBirthday(user.getBirthday());
        collegeUser.setTelephone(user.getTelephone());
        collegeUser.setStartTime(user.getStartTime());
        collegeUser.setGraduateTime(user.getGraduateTime());
        collegeUser.setEndTime(user.getEndTime());
        collegeUser.setAddress(user.getAddress());

        College college = collegeMapper.selectByPrimaryKey(user.getCid());
        collegeUser.setCollege(college.getName());

        collegeUser.setPosition(user.getPosition());//全部职位
        if("1".equals(user.getSex())){
            collegeUser.setSex("男");
        }
        if("2".equals(user.getSex())){
            collegeUser.setSex("女");
        }
        Base typeBase = baseService.findByBid(user.getTypeId());//人员类型
        iftrue(typeBase);
        collegeUser.setType(typeBase.getItemValue());
        Base educationBase = baseService.findByBid(user.getEducationId());//最高学历
        iftrue(educationBase);
        collegeUser.setEducation(educationBase.getItemValue());
        Base JobTitleBase = baseService.findByBid(user.getJobTitle());//最高职称
        iftrue(JobTitleBase);
        collegeUser.setJobTitle(JobTitleBase.getItemValue());
        Base degreeBase = baseService.findByBid(user.getDegreeId());//最高学位
        iftrue(degreeBase);
        collegeUser.setDegree(degreeBase.getItemValue());

        CommonCertificate commonCertificate = commonCertificateService.findByUser(user);
        if(commonCertificate == null){
            throw  new PException(Code.USER_NOT_EXIST,"CommonCertificate 不存在");
        }
        collegeUser.setCommonComplete(commonCertificate.getComplete());

        EntryCertificate entryCertificate = entryCertificateService.findByUser(user);
        if(entryCertificate == null){
            throw  new PException(Code.USER_NOT_EXIST,"entryCertificate 不存在");
        }
        collegeUser.setEntryComplete(entryCertificate.getComplete());

        StageCertificate stageCertificate = stageCertificateService.findByUser(user);
        if(stageCertificate == null){
            throw  new PException(Code.USER_NOT_EXIST,"stageCertificate 不存在");
        }
        collegeUser.setStageComplete(stageCertificate.getComplete());

        return  collegeUser;
    }

    private void iftrue(Base base){
        if(base == null){
            throw  new PException(Code.USER_NOT_EXIST,"字典值不存在");
        }
    }

    public void addCollege(College college){
        if(collegeMapper.selectByPrimaryKey(college) != null){
            throw new PException(Code.COLLEGE_EXIST,"部门信息已存在");
        }
        college.setStatus("1");
        collegeMapper.insert(college);
    }

    public void deleteCollege(Long cid){
        College college = collegeMapper.selectByPrimaryKey(cid);
        if(college == null){
            throw new PException(Code.COLLEGE_NOT_EXIST,"部门信息不存在");
        }
        college.setStatus("0");
        collegeMapper.updateByPrimaryKey(college);
    }

    public void updateCollege(College college){

        if(collegeMapper.selectByPrimaryKey(college) == null){
            throw new PException(Code.COLLEGE_NOT_EXIST,"部门信息不存在");
        }
        collegeMapper.updateByPrimaryKey(college);
    }

    public List<College> findCollege(){
        return collegeMapper.selectAll();
    }



    //public void export(String fileName,List<CollegeUser> list,String path){
    public HSSFWorkbook export(Long cid, String author, SelectOptionData data){
        if(author.equals("1")){
            throw new PException(Code.AUTHOR_ERROR,"权限不足");
        }
        HSSFWorkbook wb = new HSSFWorkbook();//创建一个excel文件
        HSSFCellStyle styleTitle = wb.createCellStyle(); // 标题样式
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font ztFont = wb.createFont();
        ztFont.setItalic(false); // 设置字体为斜体字
        ztFont.setColor(Font.COLOR_NORMAL); // 设置字体颜色
        ztFont.setFontHeightInPoints((short) 18); // 将字体大小设置为18px
        ztFont.setFontName("宋体"); // 将“宋体”字体应用到当前单元格上
        styleTitle.setFont(ztFont);

        HSSFSheet sheet=wb.createSheet("信息表");//创建一个工作薄
        //合并单元格 四个参数分别是：起始行，结束行，起始列，结束列 (单个单元格)
        /*CellRangeAddress rangeAddress = new CellRangeAddress(0, 1, 0,15 );
        sheet.addMergedRegion(rangeAddress);*/

       sheet.setColumnWidth((short)2, 20* 280);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
        sheet.setColumnWidth((short)3, 20* 200);
        sheet.setColumnWidth((short)5, 20* 200);
        sheet.setColumnWidth((short)6, 20* 210);
        sheet.setColumnWidth((short)7, 20* 210);
        sheet.setColumnWidth((short)8, 20* 250);
        sheet.setColumnWidth((short)9, 20* 280);
        sheet.setColumnWidth((short)10, 20* 210);
        sheet.setColumnWidth((short)11, 20* 210);
        sheet.setColumnWidth((short)12, 20* 300);
        sheet.setColumnWidth((short)13, 20* 200);
        sheet.setColumnWidth((short)14, 20* 200);
        sheet.setColumnWidth((short)15, 20* 200);
        sheet.setDefaultRowHeight((short)320);    // ---->有得时候你想设置统一单元格的高度，就用这个方法
        /*HSSFRow row1 = sheet.createRow(0);   //--->创建一行
        HSSFCell cell1 = row1.createCell((short)0);   //--->创建一个单元格
        cell1.setCellStyle(styleTitle);
        cell1.setCellValue("人员信息");//表头*/

        HSSFRow row3= sheet.createRow(0);   ////创建第二列 标题
        String[] S = {"工号","真实姓名","身份证号","出生日期","性别","联系方式","最高学历","最高职称","所属部门",
                "全部职位","人员类型","最高学位","家庭住址","毕业时间","入职时间","离职日期"};
        for(int i = 0;i<S.length;i++){
            row3.createCell(i).setCellValue(S[i]);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<User> list = userService.findByCidOrSelectOptionData(cid, data);

        for (int i = 0; i <list .size(); i++) {
            CollegeUser collegeUser = userPackaging(list.get(i));
            HSSFRow rows= sheet.createRow(i + 1);//创建第二列 标题
            rows.createCell((short)0).setCellValue(collegeUser.getJobNumber());
            rows.createCell((short)1).setCellValue(collegeUser.getCollegeUserName());
            rows.createCell((short)2).setCellValue(collegeUser.getIdentityCard());
            rows.createCell((short)3).setCellValue(simpleDateFormat.format(collegeUser.getBirthday()));
            rows.createCell((short)4).setCellValue(collegeUser.getSex());
            rows.createCell((short)5).setCellValue(collegeUser.getTelephone());
            rows.createCell((short)6).setCellValue(collegeUser.getEducation());
            rows.createCell((short)7).setCellValue(collegeUser.getJobTitle());
            rows.createCell((short)8).setCellValue(collegeUser.getCollege());
            rows.createCell((short)9).setCellValue(collegeUser.getPosition());
            rows.createCell((short)10).setCellValue(collegeUser.getType());
            rows.createCell((short)11).setCellValue(collegeUser.getDegree());
            rows.createCell((short)12).setCellValue(collegeUser.getAddress());
            rows.createCell((short)13).setCellValue(collegeUser.getGraduateTime());
            rows.createCell((short)14).setCellValue(simpleDateFormat.format(collegeUser.getStartTime()));
            if(collegeUser.getEndTime() != null){
                rows.createCell((short)15).setCellValue(simpleDateFormat.format(collegeUser.getEndTime()));
            }
        }
        return wb;
    }


    public College findByCid(Long cid) {
        College college = collegeMapper.selectByPrimaryKey(cid);
        if(college == null){
            throw new PException(Code.COLLEGE_NOT_EXIST,"该部门不存在");
        }
        return college;
    }
    //对不同的版本excel提供不同的实现类，但实现方式方法相同
    /**
     *  大致思路：
     *      首先根据工号查询，如果存在则该行记录为更新
     *      如果工号不存在，则进行插入
     *      已某种约定好的形式进行，具体参照模板
     * */
    public void importData(Workbook wb) {
        Sheet sheet = wb.getSheetAt(0);
        Row rows = null; //每一行记录
        int rowNum = sheet.getLastRowNum();
        //标记 true为更新，false为添加, 默认为添加
        boolean flag = false;
        User user = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //跳过第一行，也就是标题,固定格式
        for (int i = 1; i <= rowNum; i++) {
            rows = sheet.getRow(i);
            if(rows != null){
                user = new User();
                if(rows.getCell(0) == null){
                    throw new PException(Code.JOBNUMER_NOT_EXIST,"工号不存在");
                }
                String jobNumber = rows.getCell(0).getStringCellValue();//工号
                User findUser = userService.findByJobNumber(jobNumber);
                if(findUser != null){
                    //需要更新操作
                    flag = true;
                    user.setUid(findUser.getUid());
                }
                user.setJobNumber(jobNumber);
                if(findUser == null){
                    String password = MD5Util.md5(jobNumber, jobNumber);
                    user.setPassword(password);
                }

                String name = rows.getCell(1).getStringCellValue();//真实姓名
                if(isNotBlank(name)){
                    throw new PException(Code.NAME_NOT_EXIST,"工号不存在");
                }
                user.setName(name);

                String idCard = rows.getCell(2).getStringCellValue();//身份证号
                if(isNotBlank(idCard)){
                    throw new PException(Code.IDCART_NOT_EXIST,"身份证号不存在");
                }
                user.setIdentityCard(idCard);

                user.setBirthday(getDate(3,rows.getCell(3).getCellTypeEnum(),rows));
                if(rows.getCell(4).getStringCellValue().equals("男")){
                    user.setSex("1");
                }else{
                    user.setSex("2");
                }

                user.setTelephone(rows.getCell(5).getStringCellValue());

                String education = rows.getCell(6).getStringCellValue();//最高学历
                Base baseEducation = baseService.findByValue(education);
                user.setEducationId(baseEducation.getBid());

                String jobTitle = rows.getCell(7).getStringCellValue();//最高学历
                Base baseJob = baseService.findByValue(jobTitle);
                user.setEducationId(baseJob.getBid());

                String collegeName = rows.getCell(8).getStringCellValue();//所属部门
                College college = findByCollegeName(collegeName);
                user.setCid(college.getCid());

                user.setPosition(rows.getCell(9).getStringCellValue());//全部职位

                String type = rows.getCell(10).getStringCellValue();//人员类型
                Base baseType = baseService.findByValue(type);
                user.setTypeId(baseType.getBid());

                String degree = rows.getCell(11).getStringCellValue();//最高学位
                Base baseDegree = baseService.findByDegree(degree);
                user.setDegreeId(baseDegree.getBid());

                user.setAddress(rows.getCell(12).getStringCellValue());
                //毕业时间
                if(rows.getCell(13) != null){
                    if(rows.getCell(13).getCellTypeEnum() == CellType.NUMERIC){
                        Date date = rows.getCell(13).getDateCellValue();
                        user.setGraduateTime(format.format(date));
                    }else if(rows.getCell(13).getCellTypeEnum() == CellType.STRING){
                        String date = rows.getCell(13).getStringCellValue();
                        user.setGraduateTime(date);
                    }
                }
                //入职时间
                user.setStartTime(getDate(14,rows.getCell(14).getCellTypeEnum(),rows));
                //离职时间
                if(rows.getCell(15) != null){
                    user.setEndTime(getDate(15,rows.getCell(15).getCellTypeEnum(),rows));
                }

                //是添加还是更新
                if(flag){//更新
                    userService.updateByUser(user);
                }else{//添加
                    user.setStatus("1");
                    user.setEntryStatusId(30L);//入职状态，默认
                    user.setComplete("0");
                    commonCertificateService.addCommonCertificate(user);
                    entryCertificateService.addEntryCertificate(user);
                    stageCertificateService.addStageCertificate(user);
                    userService.addUser(user,"1");
                }
                flag = false;
            }
        }
    }

    private Date getDate(int row, CellType type, Row rows){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(type == CellType.NUMERIC){
            Date date = rows.getCell(row).getDateCellValue();//日期格式
            return date;
        }else if(type == CellType.STRING){//字符串
            String birthday = rows.getCell(row).getStringCellValue();
            try {
                Date date = format.parse(birthday);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private College findByCollegeName(String collegeName) {
        College college = new College();
        college.setName(collegeName);

        College selectOne = collegeMapper.selectOne(college);
        if(selectOne == null){
            throw new PException(Code.COLLEGE_NOT_EXIST,"部门不存在");
        }
        return selectOne;
    }

    public boolean isNotBlank(String str){
        return StringUtils.isBlank(str);
    }


}
