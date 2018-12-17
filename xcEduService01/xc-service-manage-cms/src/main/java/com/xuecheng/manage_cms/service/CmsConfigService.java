package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 页面模型数据业务层
 */
@Service
public class CmsConfigService {

    @Autowired
    CmsConfigRepository configRepository;


    /**
     * 获取页面数据模型
     * @param id
     * @return
     */
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> byId = configRepository.findById(id);

        //判断值不为空
        if (byId.isPresent()){
            return byId.get();
        }
        else ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        return null;
    }

}
