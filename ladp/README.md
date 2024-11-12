## LADP + JNDI 攻击

- 编译 Evil.java

  注意Evil.java不要写package XXX.XXX

- 开启python http.server服务，将编译后class文件发布到文件服务器中

- 开启服务端

- 启动客户端，成功执行恶意代码

注：在高版本JDK(8u191)以上攻击默认是无效的，因为限制了从远程加载类，具体配置名为`com.sun.jndi.ldap.object.trustURLCodebase`

### 高版本绕过方式
#### 1. JNDI + RMI
利用tomcat8中的org.apache.naming.factory.BeanFactory
#### 2. JNDI + LDAP

利用javaSerializedData属性