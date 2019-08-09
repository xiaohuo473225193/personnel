package util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
* @Description:    特定MD5加密 ， 2次盐加密
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/7 21:19
* @UpdateUser:
* @UpdateDate:     2019/8/7 21:19
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class MD5Util {
    public static String md5(String source,String salt){
        Md5Hash md5 = new Md5Hash(source,salt,2);
        String newPwd = md5.toString();
        return newPwd;
    }

    public static void main(String[] args) {
        System.out.println(md5("002751","002751"));
    }
}
