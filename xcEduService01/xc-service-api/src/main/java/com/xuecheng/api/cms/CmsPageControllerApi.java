package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    //查询页面数据集合
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);


    //添加页面
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);

    //根据页面id查询出页面的数据
    @ApiOperation("根据页面id查询出页面的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面ID",required=true,paramType="path",dataType="String")
    })
    public CmsPage findPageById(String id);

    //修改页面信息
    @ApiOperation("修改页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面ID",required=true,paramType="path",dataType="String"),
            @ApiImplicitParam(name="cmsPage",value = "页面",required=true,paramType="body",dataType="CmsPage")
    })
    public CmsPageResult edit(String id,CmsPage cmsPage);


    //删除指定页面
    @ApiOperation("删除指定页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面ID",required=true,paramType="path",dataType="String")
    })
    public ResponseResult delete(String id);



}
