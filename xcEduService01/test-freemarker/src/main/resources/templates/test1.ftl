<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<br/>
<h1>遍历list集合</h1>
<table>     
    <tr>     
        <td>序号</td>              
        <td>姓名</td>         
        <td>年龄</td>
        <td>生日</td>        
        <td>钱包</td>     
    </tr>
    <#list stus as stu>
        <tr>
            <td>${stu_index+1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.birthday?date}</td>
            <td>${stu.money}</td>

        </tr>
    </#list>
</table>

<br>
<h1>遍历map集合</h1>
<table>
    <#list strMap?keys as k>
        <td>${k}--${strMap[k]}</td><br>
    </#list>
</table>


</body>
</html>