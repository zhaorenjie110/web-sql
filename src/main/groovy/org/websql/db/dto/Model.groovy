package org.websql.db.dto

import groovy.transform.CompileStatic

@CompileStatic
class Model {
    public String msg
    public Boolean status
    public Map data = new HashMap<String,Object>()

    String getMsg() {
        return msg
    }

    void setMsg(String msg) {
        this.msg = msg
    }

    Boolean getStatus() {
        return status
    }

    Model setStatus(Boolean status) {
        this.status = status
        return this;
    }

    Map getData() {
        return data
    }

    void setData(Map data) {
        this.data = data
    }

    Model put(String key,Object o){
        this.data.put(key,o)
        return this
    }
}
