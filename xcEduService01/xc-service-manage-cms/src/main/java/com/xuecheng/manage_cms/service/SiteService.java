package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面站点业务层
 */
@Service
public class SiteService {

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    public List<CmsSite> findSites() {

        List<CmsSite> all = cmsSiteRepository.findAll();

        return all;
    }


}
