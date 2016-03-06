package com.paleo.blog.cache.tools.spel;

import java.util.Map;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.paleo.blog.cache.tools.ConcurrentLRUHashMap;

/**
 * https://github.com/qiujiayu/AutoLoadCache/blob/160e1806f34bfcccf404b5445991f5a13a828291/src/main/java/com/jarvis/cache/CacheUtil.java
 * @author Paleo
 *
 */
public class SpELUtils {
	
	private static final Map<String, Expression> expCache = new ConcurrentLRUHashMap<String, Expression>();
	
	private static final ExpressionParser parser = new SpelExpressionParser();
	/**
	 * 参数key
	 * SpEL中通过#args访问
	 */
	private static final String ARGS = "args";
	/**
	 * 返回值key
	 * SpEL中通过#retVal访问
	 */
    private static final String RET_VAL = "retVal";
    
    
	 /**
     * 将Spring EL 表达式转换期望的值
     * @param keySpEL 生成缓存Key的Spring el表达式
     * @param arguments 参数
     * @param valueType 值类型
     * @return T Value 返回值
     * @param <T> 泛型
     */
    public static <T> T getElValue(String keySpEL, Object[] arguments, Class<T> valueType) {
        return getElValue(keySpEL, arguments, null, valueType);
    }

    /**
     * 将Spring EL 表达式转换期望的值
     * @param keySpEL 生成缓存Key的Spring el表达式
     * @param arguments 参数
     * @param valueType 值类型
     * @param retVal 结果值
     * @return T value 返回值
     * @param <T> 泛型
     */
    public static <T> T getElValue(String keySpEL, Object[] arguments, Object retVal, Class<T> valueType) {
        EvaluationContext context=new StandardEvaluationContext();
        context.setVariable(ARGS, arguments);
        context.setVariable(RET_VAL, retVal);
        Expression exp = expCache.get(keySpEL);
        if(exp==null){
        	exp = parser.parseExpression(keySpEL);
        	expCache.put(keySpEL, exp);
        }
        return exp.getValue(context, valueType);
    }
}
