## 基础

### 介绍

Spring表达式语言（简称SpEl）是一个支持查询和操作运行时对象导航图功能的强大的表达式语言. 它的语法类似于传统EL，但提供额外的功能，最出色的就是函数调用和简单字符串的模板函数。

### @Value注解中$与#区别

    #{…} 用于执行SpEl表达式，并将内容赋值给属性
    ${…} 主要用于加载外部属性文件中的值
    #{…} 和${…} 可以混合使用，但是必须#{}外面，${}在里面,#{ '${}' } ，注意单引号，注意不能反过来


### 漏洞利用条件
- 传入的表达式未过滤
- 表达式解析之后调用了getValue/setValue方法
- 使用`StandardEvaluationContext`（默认）作为上下文对象

### 修复方式
使用`SimpleEvaluationContext`替代`StandardEvaluationContext`。
    
    原理：使用SimpleEvaluationContext默认情况下无法访问Java类

## 参考链接
[JAVA代码审计之SpEL表达式](https://www.freebuf.com/articles/web/344140.html)