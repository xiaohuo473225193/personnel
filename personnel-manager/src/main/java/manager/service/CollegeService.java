package manager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import manager.bo.ExcelHender;
import manager.mapper.CollegeMapper;
import manager.pojo.*;
import manager.vo.CollegeUser;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Code;
import util.PException;
import util.PageResult;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static util.Code.USER_NOT_EXIST;

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

    public PageResult<CollegeUser> findCollegeUserListByCid(Long cid,int rows,int size){
        PageHelper.startPage(rows,size);
        //根据cid找到user集合
        Page<User> users = (Page<User>)userService.findByCid(cid);
        List<User> userList = users.getResult();

        List<CollegeUser> collegeUsers = new ArrayList<CollegeUser>();
        for (User user : userList) {
            CollegeUser collegeUser = userPackaging(user);
            collegeUsers.add(collegeUser);
        }

        return new PageResult<>(users.getTotal(),collegeUsers);
    }

    private CollegeUser userPackaging(User user){
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



    public void export(String fileName,List<CollegeUser> list,String path){
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
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 1, 0,15 );
        sheet.addMergedRegion(rangeAddress);

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
        HSSFRow row1 = sheet.createRow(0);   //--->创建一行
        HSSFCell cell1 = row1.createCell((short)0);   //--->创建一个单元格
        cell1.setCellStyle(styleTitle);
        cell1.setCellValue("人员信息");//表头

        HSSFRow row2= sheet.createRow(1);   ////创建第二列 标题
        String[] S = {"工号","真实姓名","身份证号","出生日期","性别","联系方式","最高学历","最高职称","所属部门",
                "全部职位","人员类型","最高学位","家庭住址","毕业时间","入职时间","离职日期"};
        for(int i = 0;i<S.length;i++){
            row2.createCell(i).setCellValue(S[i]);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i <list .size(); i++) {
            HSSFRow rows= sheet.createRow(1+i+1);//创建第二列 标题
            rows.createCell((short)0).setCellValue(list.get(i).getJobNumber());
            rows.createCell((short)1).setCellValue(list.get(i).getCollegeUserName());
            rows.createCell((short)2).setCellValue(list.get(i).getIdentityCard());
            rows.createCell((short)3).setCellValue(simpleDateFormat.format(list.get(i).getBirthday()));
            rows.createCell((short)4).setCellValue(list.get(i).getSex());
            rows.createCell((short)5).setCellValue(list.get(i).getTelephone());
            rows.createCell((short)6).setCellValue(list.get(i).getEducation());
            rows.createCell((short)7).setCellValue(list.get(i).getJobTitle());
            rows.createCell((short)8).setCellValue(list.get(i).getCollege());
            rows.createCell((short)9).setCellValue(list.get(i).getPosition());
            rows.createCell((short)10).setCellValue(list.get(i).getType());
            rows.createCell((short)11).setCellValue(list.get(i).getDegree());
            rows.createCell((short)12).setCellValue(list.get(i).getAddress());
            rows.createCell((short)13).setCellValue(simpleDateFormat.format(list.get(i).getGraduateTime()));
            rows.createCell((short)14).setCellValue(simpleDateFormat.format(list.get(i).getStartTime()));
            rows.createCell((short)15).setCellValue(simpleDateFormat.format(list.get(i).getEndTime()));
        }
             FileOutputStream fileOut = null;
               try{
                     fileOut = new FileOutputStream(path);
                     wb.write(fileOut);
                       //fileOut.close();
                       System.out.print("OK");
                }catch(Exception e){
                     e.printStackTrace();
                 }
               finally{
                      if(fileOut != null){
                             try {
                                      fileOut.close();
                                   } catch (IOException e) {
                                      // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                            }
                    }


    }

    public void input(FileInputStream inputStream){

        String str = "";
          try {
              POIFSFileSystem fs = new POIFSFileSystem(inputStream);
              HSSFWorkbook wb = new HSSFWorkbook(fs);
              HSSFSheet sheet = wb.getSheetAt(0);
              //int rowfirst=sheet.getFirstRowNum();
               int rowend=sheet.getLastRowNum();
                    for (int i = 2; i <=rowend; i++) {
                        HSSFRow row = sheet.getRow(i);
                        //System.out.println(row.get);
                        int colNum = row.getPhysicalNumberOfCells();//一行总列数
                      int j = 0;
                           while (j < colNum) {
                                 str += getCellFormatValue(row.getCell((short) j)).trim() + "-";
                                    j++;
                                 }
                                 System.out.println(str);
                                str="";
                           }
                  } catch (Exception e) {
                        // TODO: handle exception
                  }
    }

    private static String getCellFormatValue(HSSFCell cell) {
                String cellvalue = "";
                if (cell != null) {
                       // 判断当前Cell的Type
                        switch (cell.getCellType()) {
                                // 如果当前Cell的Type为NUMERIC
                               case HSSFCell.CELL_TYPE_NUMERIC:
                                    case HSSFCell.CELL_TYPE_FORMULA: {
                                         // 判断当前的cell是否为Date
                                         if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                                Date date = cell.getDateCellValue();
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                cellvalue = sdf.format(date);
                                            }
                                         // 如果是纯数字
                                         else {
                                                 // 取得当前Cell的数值
                                                 cellvalue = String.valueOf(cell.getNumericCellValue());
                                            }
                                        break;
                                    }
                                // 如果当前Cell的Type为STRIN
                                case HSSFCell.CELL_TYPE_STRING:
                                        // 取得当前的Cell字符串
                                        cellvalue = cell.getRichStringCellValue().getString();
                                        break;
                                 // 默认的Cell值
                                default:
                                         cellvalue = " ";
                                }
                    } else {
                        cellvalue = "";
                    }
                return cellvalue;
            }

}
