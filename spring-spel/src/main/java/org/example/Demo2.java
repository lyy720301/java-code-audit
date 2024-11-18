package org.example;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Demo2 {
    public static void main(String[] args) {
        //创建一个ExpressionParser对象，用于解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        //最简单的字符串表达式
        Expression exp = parser.parseExpression("'HelloWorld'");
        System.out.println("'HelloWorld'的结果： " + exp.getValue());
        //调用方法的表达式
        exp = parser.parseExpression("'HelloWorld'.concat('!')");
        System.out.println("'HelloWorld'.concat('!')的结果： "
                + exp.getValue());
        //调用对象的getter方法
        exp = parser.parseExpression("'HelloWorld'.bytes");
        System.out.println("'HelloWorld'.bytes的结果： "
                + exp.getValue());
        //访问对象的属性(d相当于HelloWorld.getBytes().length)
        exp = parser.parseExpression("'HelloWorld'.bytes.length");
        System.out.println("'HelloWorld'.bytes.length的结果："
                + exp.getValue());
        //使用构造器来创建对象
        exp = parser.parseExpression("new String('helloworld')"
                + ".toUpperCase()");
        System.out.println("new String('helloworld')"
                + ".toUpperCase()的结果是： "
                + exp.getValue(String.class));

        Person person = new Person(6 , "孙悟空12", new Date());
        exp = parser.parseExpression("name");
        //以指定对象作为root来计算表达式的值
        //相当于调用person.name表达式的值
        exp.setValue(person, "fc");        //将person 对象的 name属性设为 fc,没有这一句下面就会输出  孙悟空12
        System.out.println("以persn为root，name表达式的值是： "
                + exp.getValue(person , String.class));


        exp = parser.parseExpression("name=='fc'");
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setRootObject(person);
        //以指定Context来计算表达式的值
        System.out.println(exp.getValue(ctx , Boolean.class));//true  此时都是 fc

        List<Boolean> list = new ArrayList<Boolean>();
        list.add(true);
        EvaluationContext ctx2 = new StandardEvaluationContext();
        //将list设置成EvaluationContext的一个变量
        ctx2.setVariable("list" , list);
        ctx2.setVariable("p", person);
        ((StandardEvaluationContext) ctx2).setRootObject(person);
        //修饰list变量的第一个元素的值
        System.out.println(parser.parseExpression("#list[0]").getValue(ctx2));//true
        parser.parseExpression("#list[0]").setValue(ctx2 , "false");          //修改 现在变为false 下面输出也是false
        //list集合的第一个元素被改变
        System.out.println("list集合的第一个元素为： "
                + list.get(0));


        System.out.println(parser.parseExpression("#p.name").getValue(ctx2));//fc
        System.out.println(parser.parseExpression("name").getValue(ctx2));//fc
        System.out.println("更新一下name");
        parser.parseExpression("name").setValue(ctx2, "setname哈哈");
        System.out.println(parser.parseExpression("name").getValue(ctx2));// setname哈哈
        System.out.println(parser.parseExpression("#p.name").getValue(ctx2));// setname哈哈
    }

    static class Person {
        private int id;
        private String name;
        private Date birthdate;

        // 构造方法
        public Person(int id, String name, Date birthdate) {
            this.id = id;
            this.name = name;
            this.birthdate = birthdate;
        }

        // Getter 和 Setter 方法
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(Date birthdate) {
            this.birthdate = birthdate;
        }

        // 重写 toString 方法
        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", birthdate=" + birthdate +
                    '}';
        }
    }
}
