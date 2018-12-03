package com.xuecheng.freemarker;

import com.xuecheng.freemarker.domain.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFreeMarke {

    //根据模板静态化页面
    @Test
    public void getTemplate() throws IOException, TemplateException {

        //定义配置类
        Configuration configuration= new Configuration(Configuration.getVersion());

        //获取classPath路径
        String path = this.getClass().getResource("/").getPath();
        //获取模板路径
        configuration.setDirectoryForTemplateLoading(new File(path+"/templates"));
        //定义模板文件
        Template template = configuration.getTemplate("test1.ftl");
        //获取数据
        Map<String, Object> map = this.getMap();

        //页面静态化
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        //定义流,将数据生成静态化页面输出到本地磁盘
        InputStream ips = IOUtils.toInputStream(html);
        FileOutputStream fops= new FileOutputStream(new File("F:/aaa/xcEduService01/test-freemarker/src/test/java/com/xuecheng/freemarker/test01.html"));

        IOUtils.copy(ips,fops);

        ips.close();
        fops.close();
    }

    //基于字符串模板生成静态页面内
    @Test
    public void getHtmlByStr() throws IOException, TemplateException {
        Configuration conf= new Configuration(Configuration.getVersion());

        //定义字符串模板
        String temp= "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Hello World!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Hello ${name}!\n" +
                "<br/>\n" +
                "<h1>遍历list集合</h1>\n" +
                "<table>     \n" +
                "    <tr>     \n" +
                "        <td>序号</td>              \n" +
                "        <td>姓名</td>         \n" +
                "        <td>年龄</td>\n" +
                "        <td>生日</td>        \n" +
                "        <td>钱包</td>     \n" +
                "    </tr>\n" +
                "    <#list stus as stu>\n" +
                "        <tr>\n" +
                "            <td>${stu_index+1}</td>\n" +
                "            <td>${stu.name}</td>\n" +
                "            <td>${stu.age}</td>\n" +
                "            <td>${stu.birthday?date}</td>\n" +
                "            <td>${stu.money}</td>\n" +
                "\n" +
                "        </tr>\n" +
                "    </#list>\n" +
                "</table>\n" +
                "\n" +
                "<br>\n" +
                "<h1>遍历map集合</h1>\n" +
                "<table>\n" +
                "    <#list strMap?keys as k>\n" +
                "        <td>${k}--${strMap[k]}</td><br>\n" +
                "    </#list>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        //将字符串转为模板
        StringTemplateLoader stl= new StringTemplateLoader();
        stl.putTemplate("test1",temp);

        //在配置中定义模板加载器
        conf.setTemplateLoader(stl);
        //加载模板
        Template test1 = conf.getTemplate("test1", "utf-8");

        //获取数据
        Map<String, Object> map = this.getMap();

        //生成静态页面
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(test1, map);


    }



    public Map<String, Object> getMap(){

        Map<String, Object> map=new HashMap<>();
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

        return map;

    }
}
