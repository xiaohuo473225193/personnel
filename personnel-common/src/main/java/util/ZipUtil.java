package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
* @Description:    对文件或文件夹进行zip压缩
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/5 20:09
* @UpdateUser:
* @UpdateDate:     2019/8/5 20:09
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class  ZipUtil{
    /**
     * 完成的结果文件--输出的压缩文件
     */
    private File targetFile;
    public ZipUtil() {}
    public ZipUtil(File target) {
        this.targetFile = target;
        if (targetFile.exists()){
            targetFile.delete();
        }
    }
    /**
     *@author      473225193    yuanyou
     * @param srcFile 要压缩文件的对象
    * @param targetFile 压缩后文件输出的位置
     * @return      void
     * @exception
     * @date        2019/8/5 20:29
     * @description
     */
    public static void zip(File srcFile,File targetFile){
        //文件压缩完成输出的路径
        ZipUtil zipUtil = new ZipUtil(targetFile);
        //开始压缩
        zipUtil.zipFiles(srcFile);
    }

    /**
     * 具体的压缩文件
     * @param srcfile
     */
    public void zipFiles(File srcfile) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(targetFile));
            if(srcfile.isFile()){//如果目标目录是个文件，进行文件压缩(单个文件)
                zipFile(srcfile, out, "");
            } else{//如果目标是个文件目录，则对目录下的文件进行压缩(多个文件)
                File[] list = srcfile.listFiles();
                for (int i = 0; i < list.length; i++) {
                    compress(list[i], out, "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 如果目标是单个文件则对单个文件进行压缩，如果目标是文件夹则压缩文件夹里的文件
     * @param file
     * @param out
     * @param basedir
     */
    private void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            this.zipDirectory(file, out, basedir);
        } else {
            this.zipFile(file, out, basedir);
        }
    }
    /**
     * 压缩单个文件
     * @param srcfile
     */
    public void zipFile(File srcfile, ZipOutputStream out, String basedir) {
        if (!srcfile.exists())
            return;
        byte[] buf = new byte[1024];
        FileInputStream in = null;
        try {
            int len;
            in = new FileInputStream(srcfile);
            out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.closeEntry();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩文件夹
     * @param dir
     * @param out
     * @param basedir
     */
    public void zipDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    public static void main(String[] args) {
        //要压缩的对象
        File f = new File("E:\\image\\item\\img");
        //文件压缩完成输出的路径
        File tar = new File("E:/image/111","image.zip");
        if(!tar.exists()){
            System.out.println("1该文件不存在");
            tar.mkdir();
        }
        if(!tar.exists()){
            System.out.println("2该文件不存在");
        }
        ZipUtil zipUtil = new ZipUtil(tar);
        //开始压缩
        zipUtil.zipFiles(f);
    }

}
