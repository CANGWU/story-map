package cn.edu.nju.story.map.constants;

/**
 * ErrorCode
 *
 * @author xuan
 * @date 2018/8/2
 */
public class ErrorCode {
    /**
     * 出错
     */
    public static final int ERROR = -1;
    /**
     * 正常
     */
    public static final int OK = 0;
    /**
     * 参数错误
     */
    public static final int BAD_REQUEST = 400;
    /**
     * 未登录
     */
    public static final int UNAUTHORIZED = 401;
    /**
     * 操作没有权限
     */

    public static final int FORBIDDEN = 403;
    /**
     * 服务器出错
     */
    public static final int SERVER_ERROR = 500;
    /**
     * 普通异常
     */
    public static final int CATCH_EXCEPTION = 2500;
    /**
     * 数据库异常
     */
    public static final int DATA_BASE_EXCEPTION = 2600;
    /**
     * 搜索异常
     */
    public static final int SEARCH_EXCEPTION = 2700;
    /**
     * 空指针
     */
    public static final int NP_EXCEPTION = 2501;
    /**
     * 类型强制转换异常
     */

    public static final int CLASS_CAST_EXCEPTION = 2502;
    /**
     * 传递非法参数异常
     */
    public static final int ILLEGAL_ARGUMENT_EXCEPTION = 2503;
    /**
     * 算术运算异常
     */
    public static final int ARITHMETIC_EXCEPTION = 2504;
    /**
     * 数组存储对象不兼容异常
     */
    public static final int ARRAY_STORE_EXCEPTION = 2505;
    /**
     * 下标越界异常
     */
    public static final int INDEX_OUT_OF_BOUNDS_EXCEPTION = 2506;
    /**
     * 安全异常
     */
    public static final int SECURITY_EXCEPTION = 2507;
    /**
     * 不支持的操作异常
     */
    public static final int UNSUPPORTED_OPERATION_EXCEPTION = 2508;


}
