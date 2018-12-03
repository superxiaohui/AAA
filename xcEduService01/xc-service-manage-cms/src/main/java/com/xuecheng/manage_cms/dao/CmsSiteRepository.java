package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 页面站点持久层
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {


}
