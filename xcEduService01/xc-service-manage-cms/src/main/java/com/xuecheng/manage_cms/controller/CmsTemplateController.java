package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/temp")
public class CmsTemplateController {

    @Autowired
    TemplateService templateService;

    @GetMapping("/getTemp")
    public List<CmsTemplate> findTemps(){
        return templateService.findTemp();
    }



}
