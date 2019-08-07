package util;

/**
 * @author xiaohuo
 * @data 2019/7/15 12:31
 * @description
 */
public class Code {
    public static final int SUCCESS = 200;
    /***********     参数异常的操作3开头    **********/
    public static final int PARAM_ERROR = 3404;//参数异常
    public static final int FILE_NOT_EXIST = 3405;//你所访问的文件不存在
    /***********     不合法的操作4开头    **********/
    public static final int ID_NOT_EXIST = 4404; //ID不存在
    public static final int FAIL_HANDLER = 4405; //失败操作
    /***********     用户信息相关的状态码为5开头    **********/
    public static final int USER_NOT_EXIST = 5404; //用户不存在

    public static final int USER_EXIST = 5405; //用户已存在
    public static final int USER_NOT_FILE = 5406;//用户不拥有该文件
    public static final int USER_EXIST_FILE = 5407;//该文件用户已存在
    /***********     新闻信息相关的状态码为6开头    **********/
    public static final int NEWS_EXIST = 6404; //新闻信息已存在
    public static final int NEWS_NOT_EXIST = 6405; //新闻信息已存在

    /***********     菜单信息相关的状态码为7开头    **********/
     public static final int MENU_EXIST = 7404; //新闻信息已存在
    public static final int MENU_NOT_EXIST = 7405; //新闻信息已存在

    /***********     菜单信息相关的状态码为8开头    **********/
    public static final int COLLEGE_EXIST = 8404; //新闻信息已存在
    public static final int COLLEGE_NOT_EXIST = 8405; //新闻信息已存在
}
