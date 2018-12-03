package com.xuecheng.manage_cms.controller;


import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cms/site")
public class CmsSiteController {

    @Autowired
    SiteService siteService;

    /**
     * 从数据库中查询 站点列表
     * @return
     */
    @GetMapping("/getSite")
    public List<CmsSite> findSites(){
        return siteService.findSites();

    }




}







