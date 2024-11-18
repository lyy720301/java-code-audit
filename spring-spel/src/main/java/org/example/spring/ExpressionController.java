package org.example.spring;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpressionController {
    public static void main(String[] args) {
        System.out.println(Print.class.getName());
    }

    static class Print {
        public static void print() {
            System.out.println();
            System.out.println("i am print.");
        }

    }

    /**
     * 将poc返回的值传进去
     *
     * @param spel T(java.lang.Runtime).getRuntime().exec('calc')
     * @return
     */
    @RequestMapping("/spel")
    public String spel(@RequestParam(name = "spel") String spel) throws InterruptedException {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(spel);
        expression.getValue();
        return "success";
    }

    /**
     * T(org.example.spring.ExpressionController$Print).print()
     *
     * @param spel
     * @return
     * @throws InterruptedException 使用 SimpleEvaluationContext 会限制对Java类的访问
     *                              报错：org.springframework.expression.spel.SpelEvaluationException: EL1005E: Type cannot be found 'org.example.spring.ExpressionController$Print'
     */
    @RequestMapping("/spel2")
    public String spel2(@RequestParam(name = "spel") String spel) {
        /*
        默认下无法访问Java类
         */
        SimpleEvaluationContext simpleEvaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(spel);
        expression.getValue(simpleEvaluationContext);
        return "success";
    }
}
