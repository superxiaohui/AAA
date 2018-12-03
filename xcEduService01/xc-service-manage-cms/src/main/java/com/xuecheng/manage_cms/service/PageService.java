package com.xuecheng.manage_cms.service;


import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageService {

    //注入dao
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository templateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    //页面查询方法
    /**
     * 页面查询方法
     * @param page             页码，从1开始记数
     * @param size             每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        //进行非空判断,避免空指针异常
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        //自定义查询条件匹配器--包含查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher = exampleMatcher.withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        //创建页面查询参数对象
        CmsPage cmsPage = new CmsPage();
        /*判断方法传入参数,并给查询参数赋值*/
        //1.设置站点id
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //2.设置模板id
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //3.设置页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //4.设置页面名称
        if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        //5.设置页面类型
        if (StringUtils.isNotEmpty(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
        }



        //封装查询条件
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        //分页参数
        if (page <= 0) {
            page = 1;
        }
        //页码从0开始，但是从1计数
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        //调用dao的方法进行条件分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        /*封装查询结果*/
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);//查询状态--结果集
        return queryResponseResult;
    }

    //新增页面
    /**
     * 添加一个页面
     *
     * @param cmsPage 要添加的页面
     * @return 执行
     */
    public CmsPageResult add(CmsPage cmsPage) {
        //先进行页面唯一性校验，如果存在就更新，不存在就添加

        CmsPage byCms = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        //如果为空,就存储
        if (byCms == null) {
            //设置id为空,让数据库自动生成,防止冲突
            cmsPage.setPageId(null);
            CmsPage save = cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, save);
        }if (byCms!=null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        return new CmsPageResult(CommonCode.FAIL, null);
    }

    //查询页面
    public CmsPage findPageById(String id) {

        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId != null) {
            return byId.get();
        }
        return null;
    }

    //修改页面
    public CmsPageResult update(String id, CmsPage cmspage) {

        //先根据id查询数据.如果要修改的页面存在,就执行更新
        CmsPage one = this.findPageById(id);
        if (one != null) {
            //更新模板id
            one.setTemplateId(cmspage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmspage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmspage.getPageAliase());
            //更新页面名称
            one.setPageName(cmspage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmspage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmspage.getPagePhysicalPath());

            //更新数据URl
            one.setDataUrl(cmspage.getDataUrl());

            CmsPage save = cmsPageRepository.save(one);
            //如果执行更新操作成功
            if (save != null) {
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        //如果存储失败,或者id不存在--返回错误信息
        CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.FAIL, null);
        return cmsPageResult;

    }

    //删除页面
    public ResponseResult delete(String id){
        CmsPage pageById = this.findPageById(id);

        //如果页面存在
        if (pageById!=null){
            cmsPageRepository.delete(pageById);
            //返回状态信息，成功
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

//-------------------------------------------------------
    //获取模板数据
    public Map getModelByPageId(String pageId){

        //获取dataUrl
        CmsPage pageById = this.findPageById(pageId);
        if (pageById==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String dataUrl = pageById.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //获取模板数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }


    //获取页面模板
    public String getTemplateById(String pageId){
        CmsPage pageById = this.findPageById(pageId);
        if (pageById==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //获取模板id
        String templateId = pageById.getTemplateId();
        if (StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //获取模板数据
        Optional<CmsTemplate> templateById = templateRepository.findById(templateId);
        if (templateById.isPresent()){
            CmsTemplate cmsTemplate = templateById.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //取出模板文件内容
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource--用来获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            try {
                //将模板转换为字符串并--返回数据--
                String template = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return template;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        return null;
    }

    //页面静态化
    public String generateHtml(String template,Map model){
        try {
        //生成配置类
        Configuration configuration =new Configuration(Configuration.getVersion());
        //构建模板加载器
        StringTemplateLoader strTempLoader = new StringTemplateLoader();
        strTempLoader.putTemplate("template",template);
        //配置模板加载器
        configuration.setTemplateLoader(strTempLoader);
        //获取模板
        Template templateByConf = configuration.getTemplate("template");

        //生成静态页面
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(templateByConf, model);

        //返回页面
        return html;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }


    //封装方法
    public String getHtml(String pageId){
        //1.获取数据模型
        Map modelByPageId = this.getModelByPageId(pageId);

        if (modelByPageId==null){
            //根据页面的数据url获取不到数据
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //2.获取模板数据
        String templateById = this.getTemplateById(pageId);
        if (StringUtils.isEmpty(templateById)){
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //3.整合数据,生成静态页面
        String html = this.generateHtml(templateById, modelByPageId);

        //4.返回静态页面
        return html;
    }

}
