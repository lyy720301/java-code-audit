package org.example;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {
    public static void main(String[] args) {
        // 第一步 创建解析器：SpEL使用ExpressionParser接口表示解析器，提供SpelExpressionParser默认实现
        ExpressionParser parser = new SpelExpressionParser();
        // 第二步 解析表达式：使用ExpressionParser的parseExpression来解析相应的表达式为Expression对象，这里调用concat方法进行拼接
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        // 第三步 构造上下文：准备比如变量定义等等表达式需要的上下文数据
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");
        // 第四步 求值：通过Expression接口的getValue方法根据上下文获得表达式值
        System.out.println(expression.getValue(context));

        // test1
        Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
        System.out.println(result1); // class java.lang.String

        // test2
        String expression2 = "T(java.lang.Runtime).getRuntime().exec('calc')";
        Process value = parser.parseExpression(expression2).
                getValue(Process.class);
        System.out.println(value);
    }
}
