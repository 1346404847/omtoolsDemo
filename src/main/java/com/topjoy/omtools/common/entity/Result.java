package com.topjoy.omtools.common.entity;

import com.topjoy.omtools.common.util.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * API 统一返回状态码
 * Created by cxz on 18/7/12
 */

@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;

    private String msg;

    private Object data;

    public Result() {}

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回成功状态码
     * 无数据
     * @return SUCCESS
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 返回成功状态码
     * @return SUCCESS
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功状态码
     * @return SUCCESS
     */
    public static Result imgSuccess(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.IMAGE_UPLOAD_SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 返回失败状态码
     * @return FAILURE
     */
    public static Result imgFailure(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.IMAGE_UPLOAD_ERROR);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功状态码
     * 不是图片类型
     * @return FAILURE
     */
    public static Result noImageType(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.NO_IMAGE_UPLOAD_TYPE);
        result.setData(data);
        return result;
    }

    /**
     * 添加成功状态码
     * 无数据
     * @return INSERT
     */
    public static Result insertSuccess() {
        Result result = new Result();
        result.setResultCode(ResultCode.DATA_INSERT_SUCCESS);
        return result;
    }

    /**
     * 更新成功状态码
     * 无数据
     * @return UPDATE
     */
    public static Result updateSuccess() {
        Result result = new Result();
        result.setResultCode(ResultCode.DATA_UPDATE_SUCCESS);
        return result;
    }

    /**
     * 删除成功状态码
     * 无数据
     * @return DELETE
     */
    public static Result deleteSuccess() {
        Result result = new Result();
        result.setResultCode(ResultCode.DATA_DELETE_SUCCESS);
        return result;
    }

    /**
     * 返回失败状态码
     * 无数据
     * @return FAILURE
     */
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 返回失败状态码
     * @return FAILURE
     */
    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    /**
     * 返回失败状态码
     * @return FAIL
     */
    public static Result fail(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    /**
     * 添加失败状态码
     * 无数据
     * @return INSERT
     */
    public static Result insertFailure() {
        Result result = new Result();
        result.setResultCode(ResultCode.DATA_INSERT_FAILURE);
        return result;
    }

    /**
     * 更新失败状态码
     * 无数据
     * @return UPDATE
     */
    public static Result updateFailure() {
        Result result = new Result();
        result.setResultCode(ResultCode.DATA_UPDATE_FAILURE);
        return result;
    }

    /**
     * 删除失败状态码
     * 无数据
     * @return DELETE
     */
    public static Result deleteFailure() {
        Result result = new Result();
        result.setResultCode(ResultCode.DATA_DELETE_FAILURE);
        return result;
    }

    /**
     * 返回数据
     * @return data
     */
    private void setData(Object data) {
        this.data = data;
    }

    /**
     * 返回值
     * @return code
     * @return message
     */
    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg  = code.message();
    }

}