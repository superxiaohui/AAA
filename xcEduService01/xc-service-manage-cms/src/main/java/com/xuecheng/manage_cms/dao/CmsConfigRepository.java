package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 页面模型数据持久层
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
