package util;

/**
 * @author xiaohuo
 * @data 2019/7/15 12:31
 * @description
 */
public class Code {
    public static final int SUCCESS = 200;
    /***********     参数异常的操作3开头    **********/
    public static final int PARAM_ERROR = 3404;
    /***********     不合法的操作4开头    **********/
    public static final int ID_NOT_EXIST = 4404; //ID不存在
    /***********     用户信息相关的状态码为5开头    **********/
    public static final int USER_NOT_EXIST = 5404; //用户名不存
    public static final int USER_EXIST = 5405; //用户名不存在
    public static final int USER_NOT_FILE = 5406;//用户不拥有该文件
    public static final int USER_EXIST_FILE = 5407;//该文件用户已存在
}
