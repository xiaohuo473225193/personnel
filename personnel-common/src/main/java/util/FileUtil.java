package util;

import java.io.File;

/**
* @Description:    文件的工具类
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/6 12:24
* @UpdateUser:
* @UpdateDate:     2019/8/6 12:24
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class FileUtil {
    /**
     *@author      473225193    yuanyou
     * @param       targetFile
     * @return      void
     * @exception
     * @date        2019/8/6 12:25
     * @description 删除文件夹下的所有文件，不包括文件夹自身
     */
    public static void deleteDirectoryOfAllFile(File targetFile){
        File[] files = targetFile.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                deleteDirectoryOfAllFile(file);
                //删除空的文件夹
                file.delete();
            }else{
                file.delete();
            }
        }
    }
    /**
     *@author      473225193    yuanyou
     * @param       targetFile
     * @return      void
     * @exception
     * @date        2019/8/6 12:25
     * @description 删除文件夹下的所有文件，包括文件夹自身
     */
    public static void deleteDirectoryAndAllFile(File targetFile){
        File[] files = targetFile.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                deleteDirectoryOfAllFile(file);
                //删除空的文件夹
                file.delete();
            }else{
                file.delete();
            }
        }
        targetFile.delete();
    }

    public static void main(String[] args) {
        deleteDirectoryAndAllFile(new File("E:/log"));
    }
}
