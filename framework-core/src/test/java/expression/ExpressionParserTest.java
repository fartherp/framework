/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/6/15
 */
public class ExpressionParserTest {

    @Test
    public void testOne() {
        //创建SpEL表达式的解析器
        ExpressionParser parser = new SpelExpressionParser();
        // -- AND 与运算 --
        //false
        boolean falseValue4 = parser.parseExpression("true and false").getValue(Boolean.class);
        assertFalse(falseValue4);
        // -- OR 或运算--
        //true
        boolean trueValue5 = parser.parseExpression("true or false").getValue(Boolean.class);
        assertTrue(trueValue5);
        //false
        boolean falseValue5 = parser.parseExpression("!true").getValue(Boolean.class);
        assertFalse(falseValue5);
    }

    @Test
    public void testTwo() {
        //创建SpEL表达式的解析器
        ExpressionParser parser = new SpelExpressionParser();
        User user = new User(9527, "周星驰");
        //解析表达式需要的上下文，解析时有一个默认的上下文
        EvaluationContext ctx = new StandardEvaluationContext();
        //在上下文中设置变量，变量名为user，内容为user对象
        ctx.setVariable("user", user);
        //从用户对象中获得id并+1900，获得解析后的值在ctx上下文中
        //11427
        int id = parser.parseExpression("#user.getId() + 1900").getValue(ctx, Integer.class);
        Assert.assertEquals(id, 11427);
        //true
        boolean flag = parser.parseExpression("#user.getId() == 9527").getValue(ctx, Boolean.class);
        assertTrue(flag);
    }
}
