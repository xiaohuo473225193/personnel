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
    public static final int USER_NOT_EXIST = 5404; //用户不存在

    public static final int USER_EXIST = 5405; //用户已存在
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
