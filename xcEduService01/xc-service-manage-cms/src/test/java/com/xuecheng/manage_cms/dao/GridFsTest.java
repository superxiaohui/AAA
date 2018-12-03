package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GridFsTest {

    @Autowired
    GridFsTemplate gridTsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 存储文件
     * @throws FileNotFoundException
     */
    @Test
    public void testStore() throws FileNotFoundException {

        //先用io获取到文件
        File file = new File("E:/XczxUi/xc-ui-pc-static-portal/include/404.vue");
        FileInputStream fis = new FileInputStream(file);

        //调用store存储文件为指定--返回文件的唯一在主键
        ObjectId storeId = gridTsTemplate.store(fis, "404.vue");

        //输出主键id
        System.out.println(storeId);
    }

    @Test
    public void queryFile() throws IOException {
        //根据id查询文件
        GridFSFile id = gridTsTemplate.findOne(Query.query(Criteria.where("_id").is("")));

        //打开一个下载流对象
        GridFSDownloadStream gds= gridFSBucket.openDownloadStream(id.getObjectId());
        //创建GridFsResource对象,,获取流
        GridFsResource gridFsResource =new GridFsResource(id,gds);

        //从流中获取数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");

        System.out.println(s);


    }
}
