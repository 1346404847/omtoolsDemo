package com.topjoy.omtools.common.entity;
/**
 *
 * @Description 获取资产信息的请求实体类
 */
public class JsonRpcData {

    private String jsonrpc = "2.0" ;
    private String id = "1" ;
    private String method ;
    private String params ;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public JsonRpcData() {
        super();
    }

    public JsonRpcData(String method, String params) {
        super();
        this.method = method;
        this.params = params;
    }

    public JsonRpcData(String jsonrpc, String id, String method, String params) {
        super();
        this.jsonrpc = jsonrpc;
        this.id = id;
        this.method = method;
        this.params = params;
    }
}