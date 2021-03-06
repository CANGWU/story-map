package cn.edu.nju.story.map.constants;

import lombok.Getter;

import java.util.Objects;

/**
 * ErrorCode
 *
 * @author xuan
 * @date 2018/8/2
 */
@Getter
public enum  ErrorCode {
    /**
     * 出错
     */
    ERROR(-1, "出错"),
    /**
     * 正常
     */
    OK(0, "正常"),
    /**
     * 参数错误
     */
    BAD_REQUEST(400, "参数错误"),
    /**
     * 未登录
     */
    UNAUTHORIZED(401,"未登录"),
    /**
     * 操作没有权限
     */

    FORBIDDEN(403,"没有操作权限"),
    /**
     * 服务器出错
     */
    SERVER_ERROR(500, "服务器出错"),
    /**
     * 普通异常
     */
    CATCH_EXCEPTION(2500, "普通异常"),
    /**
     * 数据库异常
     */
    DATA_BASE_EXCEPTION(2600, "数据库异常"),
    /**
     * 搜索异常
     */
    SEARCH_EXCEPTION(2700, "搜索异常"),
    /**
     * 空指针
     */
    NP_EXCEPTION(2501, "空指针"),
    /**
     * 类型强制转换异常
     */
    CLASS_CAST_EXCEPTION(2502, "类型强制转换异常"),
    /**
     * 传递非法参数异常
     */
    ILLEGAL_ARGUMENT_EXCEPTION(2503, "传递非法参数异常"),
    /**
     * 算术运算异常
     */
    ARITHMETIC_EXCEPTION(2504, "算术运算异常"),
    /**
     * 数组存储对象不兼容异常
     */
    ARRAY_STORE_EXCEPTION(2505, "数组存储对象不兼容异常"),
    /**
     * 下标越界异常
     */
    INDEX_OUT_OF_BOUNDS_EXCEPTION(2506, "下标越界异常"),
    /**
     * 安全异常
     */
    SECURITY_EXCEPTION(2507, "安全异常"),
    /**
     * 不支持的操作异常
     */
    UNSUPPORTED_OPERATION_EXCEPTION(2508, "不支持的操作异常"),

    /**
     * 邮箱已注册
     */
    EMAIL_IS_REGISTERED(1000, "邮箱已注册"),

    /**
     * 邀请码无效
     */
    INVALID_INVITATION_CODE(1001, "邀请码无效"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(1002, "用户不存在"),


    /**
     * 用户名或者密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(1003, "用户名或者密码错误"),

    /**
     * 项目标识已存在
     */
    SIGN_IS_EXISTED(1004, "项目标识已存在"),

    /**
     * 项目不存在
     */
    PROJECT_NOT_EXISTED(1005, "项目不存在"),
    /**
     * 邀请不存在
     */
    INVALID_INVITATION(1006, "邀请码不存在"),
    /**
     * 卡片不存在
     */
    CARD_NOT_EXISTED(1007, "卡片不存在"),

    /**
     * epic不存在
     */
    EPIC_NOT_EXISTED(1008, "epic不存在"),

    /**
     * epic下存在feature
     */
    EPIC_CAN_NOT_DELETED(1009, "epic下存在feature，不可直接删除"),

    /**
     * 挂载节点不存在
     */
    FATHER_NOT_EXISTED(1010, "挂载节点不存在"),

    /**
     * group不存在
     */
    GROUP_NOT_EXISTED(1011, "group不存在"),

    /**
     * group下存在card
     */
    GROUP_CAN_NOT_DELETED(1012, "group下存在card，不可直接删除"),

    /**
     * 特性不存在
     */
    FEATURE_NOT_EXISTED(1013, "feature不存在"),

    /**
     * feature存在card
     */
    FEATURE_CAN_NOT_DELETED(1014, "feature下存在card，不可直接删除");


    private Integer code;
    private String description;

    private ErrorCode(Integer code, String description){
        this.code = code;
        this.description = description;
    }


    public static ErrorCode getInstance(Integer code){

        for(ErrorCode errorCode : ErrorCode.values()){

            if(Objects.equals(errorCode.code, code)){
                return errorCode;
            }

        }
        return ERROR;

    }

}
