package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);

    }

    /**
     * 查询站点列表
     */
    @Test
    public void findSites(){
        List<CmsSite> all = cmsSiteRepository.findAll();

        for (CmsSite cmsSite : all) {
            System.out.println(cmsSite);
        }
    }

    @Test
    public void testFindAllByExample(){

        int page=0;
        int size=5;
        Pageable pageable = PageRequest.of(page,size);

        CmsPage cmsPage = new CmsPage();

        //设置要查询的页面信息
        cmsPage.setPageName("index");

        //条件查询匹配器
        ExampleMatcher exampleMatcher= ExampleMatcher.matching();
        //.contains()是包含匹配
        exampleMatcher = exampleMatcher.withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        //查询条件封装
        Example<CmsPage> example= Example.of(cmsPage,exampleMatcher);

        //进行查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        for (CmsPage cmsPage1 : all) {
            System.out.println(cmsPage1);
        }

    }



    //分页查询
    @Test
    public void testFindPage(){
        //分页参数
        int page = 1;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //修改
    @Test
    public void testUpdate() {
        //查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5b4b1d8bf73c6623b03f8cec");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            //设置要修改值
            cmsPage.setPageAliase("test01");
            //修改
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }

    }

    //根据页面名称查询
    @Test
    public void testfindByPageName(){
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面");
        System.out.println(cmsPage);
    }

    //根据唯一主键查询
    @Test
    public void testfindByPageNameAndSiteIdAndPageWebPath(){
        CmsPage cmsPage = cmsPageRepository.
                findByPageNameAndSiteIdAndPageWebPath("index.html","5a751fab6abb5044e0d19ea1","/index.html");
        System.out.println(cmsPage);
    }


}
