package com.xuecheng.manage_cms.service;


import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面模板业务层
 */
@Service
public class TemplateService {

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    public List<CmsTemplate> findTemp() {

        List<CmsTemplate> all = cmsTemplateRepository.findAll();

        return all;
    }

}
