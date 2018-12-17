package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * 查询结果封装类
 */
@Data
@ToString
public class QueryResponseResult extends ResponseResult {

    //查询结果数据封装
    QueryResult queryResult;

    //构造方法--状态+数据
    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

}
