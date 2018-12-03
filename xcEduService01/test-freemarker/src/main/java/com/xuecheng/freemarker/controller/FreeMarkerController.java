package com.xuecheng.freemarker.controller;

import com.xuecheng.freemarker.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller()
@RequestMapping("/freemarker")
public class FreeMarkerController {

    @Autowired
    RestTemplate restTemplate1;

    @RequestMapping("/test1")
    public String test01(Map<String, Object> map) {

        map.put("name", "小辉");

        List<Student> stus = new ArrayList<>();

        stus.add(new Student("aaa", 1, new Date(), 1f));
        stus.add(new Student("bbb", 2, new Date(), 1f));
        stus.add(new Student("ccc", 3, new Date(), 1f));
        stus.add(new Student("ddd", 4, new Date(), 1f));

        map.put("stus", stus);

        HashMap<String, String> strMap = new HashMap<> ();
        strMap.put("stu1", "111");
        strMap.put("stu2", "222");

        map.put("strMap",strMap);

        return "test1";
    }

    @RequestMapping("/banner")
    public String testBanner(Map<String, Object> map){

        ResponseEntity<Map> forEntity = restTemplate1.getForEntity("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);

        Map body = forEntity.getBody();

        map.putAll(body);

        return "index_banner";
    }

}
