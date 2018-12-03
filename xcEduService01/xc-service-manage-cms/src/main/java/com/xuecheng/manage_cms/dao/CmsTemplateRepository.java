package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 页面模板持久层
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {


}
