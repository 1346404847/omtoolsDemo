package com.topjoy.omtools.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * API 统一返回状态码
 * Created by cxz on 18/7/12
 */

public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(1, "操作成功"),

    /* 增删改查 100001-100009*/
    DATA_INSERT_SUCCESS(1,"添加数据成功"),
    DATA_UPDATE_SUCCESS(1,"更新数据成功"),
    DATA_DELETE_SUCCESS(1,"删除数据成功"),
    DATA_INSERT_FAILURE(100002,"添加数据失败"),
    DATA_UPDATE_FAILURE(100004,"更新数据失败"),
    DATA_DELETE_FAILURE(100006,"删除数据失败"),
    DATA_UPLOAD_EMPTY(100007,"上传不能为空"),
    DATA_UPLOAD_PATH(100008,"创建目录失败"),
    DATA_UPDATE_FAIL_REPEAT(100009,"更新数据失败,数据重复"),
    DATA_INSERT_FAIL_REPEAT(100010,"添加数据失败,数据重复"),
    DATA_INSERT_FAIL_USERROLE(100011,"添加用户权限失败"),
    DATA_SEARCH_FAIL_EMPTY(100012,"找不到该数据"),
    DATA_BE_INTERRELATED(100013,"数据关联,无法删除"),

    /* 参数错误：200001-299999 */
    PARAM_IS_INVALID(200001, "参数无效"),
    PARAM_IS_BLANK(200002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(200003, "参数类型错误"),
    PARAM_NOT_COMPLETE(200004, "参数缺失"),
    PARAM_NOT_NULL_EMPTY(200005, "参数值不能为NULL或者空值"),
    PARAM_NOT_NULL_USER(200006, "员工是必选项"),
    PARAM_PRODUCT_NOT_RANGE(200006, "产品名字名字长度必须在2和10之间"),
    PARAM_ALIAS_NOT_RANGE(200007, "产品缩略图地址长度必须在2和1000之间"),
    PARAM_VERSION_NOT_NULL_EMPTY(200008, "版本参数不能为空"),

    /* 用户错误：300001-399999*/
    USER_NOT_LOGGED_IN(300001, "用户未登录"),
    USER_LOGIN_ERROR(300002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(300003, "账号已被禁用"),
    USER_NOT_EXIST(300004, "用户不存在"),
    USER_HAS_EXISTED(300005, "用户已存在"),

    /* 业务错误：400001-499999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(400001, "某业务出现问题"),

    /* 系统错误：500001-599999 */
    SYSTEM_INNER_ERROR(500001, "系统繁忙，请稍后重试"),

    /* 数据错误：600001-6999999 */
    RESTFUL_DATA_NONE(600001, "数据未找到"),
    DATA_IS_WRONG(600002, "数据有误"),
    DATA_ALREADY_EXISTED(600003, "数据已存在"),

    /* 接口错误：700001-799999 */
    INTERFACE_INNER_INVOKE_ERROR(700001, "内部系统接口调用异常"),
    INTERFACE_OUTER_INVOKE_ERROR(700002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(700003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(700004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(700005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(700006, "接口负载过高"),
    INTERFACE_HEADER_DATA(700007, "接口调用失败"),

    /* 权限错误：800001-899999 */
    PERMISSION_NO_ACCESS(800001, "无访问权限"),

    /* 图片上传：1001-1999 */
    IMAGE_UPLOAD_ERROR(1001, "图片上传失败"),
    NO_IMAGE_UPLOAD_TYPE(1002, "上传的文件不是图片类型，请重新上传"),
    IMAGE_UPLOAD_FAILURE(1003, "上传失败，请选择要上传的图片"),

    /* 图片上传：2001-2999 */
    IMAGE_UPLOAD_SUCCESS(2001, "图片上传成功");


    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    /**
     * 返回信息
     * @return NAME
     */
    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    /**
     * 返回信息
     * @return CODE
     */
    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

    /**
     * 校验重复的code值
     * @return
     */
    public static void main(String[] args) {
        ResultCode[] ApiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<Integer>();
        for (ResultCode ApiResultCode : ApiResultCodes) {
            if (codeList.contains(ApiResultCode.code)) {
                System.out.println(ApiResultCode.code);
            } else {
                codeList.add(ApiResultCode.code());
            }
        }
    }
}