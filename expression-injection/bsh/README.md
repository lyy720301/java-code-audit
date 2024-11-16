# 基于BeanShell表达式注入的反序列化漏洞

## 什么是BeanShell？

BeanShell是一门脚本语言，可以直接执行Java代码片段，其解释器使用Java语言编写，可以方便的与Java应用进行交互。

## 漏洞分析

version < 2.0b6

https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2016-2510

BeanShell可以生成代理对象，在Java中调用该代理对象的方法最终执行的是BeanShell脚本中定义的方法。  
因此，只需要在BeanShell脚本中定义包含恶意代码的方法，就可以利用漏洞进行攻击。  
可以通过反序列化触发特定方法实现漏洞利用。PriorityQueue对象在反序列化时会执行以下链路：

`readObject() -> heapify() -> siftDown() -> siftDownUsingComparator() -> compare()`

那么我们可以通过对PriorityQueue对象中的comparator属性进行代理，使得应用在反序列化时调用compare调用预先定义好的恶意方法。  
具体poc构造见[BeanShellPOC.java](src/main/java/org/example/BeanShellPOC.java)  
大体思路是通过反射获取XThis对象的invocationHandler属性，通过该handler创建Comparator代理对象，然后将该代理对象通过反射赋值给PriorityQueue对象，这样在PriorityQueue反序列化时就能执行预先定义好的compare方法。






## 调用链
```text
at java.lang.ProcessBuilder.start(ProcessBuilder.java:1007)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at bsh.Reflect.invokeMethod(Reflect.java:134)
	at bsh.Reflect.invokeObjectMethod(Reflect.java:80)
	at bsh.BSHPrimarySuffix.doName(BSHPrimarySuffix.java:176)
	at bsh.BSHPrimarySuffix.doSuffix(BSHPrimarySuffix.java:120)
	at bsh.BSHPrimaryExpression.eval(BSHPrimaryExpression.java:80)
	at bsh.BSHPrimaryExpression.eval(BSHPrimaryExpression.java:47)
	at bsh.BSHBlock.evalBlock(BSHBlock.java:130)
	at bsh.BSHBlock.eval(BSHBlock.java:80)
	at bsh.BshMethod.invokeImpl(BshMethod.java:362)
	at bsh.BshMethod.invoke(BshMethod.java:258)
	at bsh.BshMethod.invoke(BshMethod.java:186)
	at bsh.This.invokeMethod(This.java:255)
	at bsh.This.invokeMethod(This.java:174)
	at bsh.XThis$Handler.invokeImpl(XThis.java:194)
	at bsh.XThis$Handler.invoke(XThis.java:131)
	at com.sun.proxy.$Proxy1.compare(Unknown Source)
	at java.util.PriorityQueue.siftDownUsingComparator(PriorityQueue.java:721)
	at java.util.PriorityQueue.siftDown(PriorityQueue.java:687)
	at java.util.PriorityQueue.heapify(PriorityQueue.java:736)
	at java.util.PriorityQueue.readObject(PriorityQueue.java:796)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1185)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2294)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2185)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1665)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:501)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:459)
	at org.example.Deserializer.deserialize(Deserializer.java:22)
	at org.example.BeanShellPOC.main(BeanShell.java:55)
```


