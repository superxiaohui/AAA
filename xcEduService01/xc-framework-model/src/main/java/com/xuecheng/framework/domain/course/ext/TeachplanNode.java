package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 自定义课程计划结点类
 */
@Data
@ToString
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;

}
