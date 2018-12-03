package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="cms页面静态化接口",description = "cms页面静态化接口，配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {


    //查询页面数据模型
    @ApiOperation("查询页面数据模型")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "模型Id",required=true,paramType="path",dataType="String"),
    })
    public CmsConfig getModel(String id);

}
