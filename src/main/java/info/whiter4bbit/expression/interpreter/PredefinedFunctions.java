package info.whiter4bbit.expression.interpreter;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.common.util.lazy.LazyValue;
import info.whiter4bbit.expression.utils.ASTHelper;
import info.whiter4bbit.expression.utils.EvaluationFunction;
import info.whiter4bbit.expression.utils.PrimitiveUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PredefinedFunctions
 * Date: 29.03.2010
 */
@SuppressWarnings("serial")
public class PredefinedFunctions {

    public static final String FACT = "fact";
    public static final String ABS = "abs";
    public static final String ROUND = "round";
    public static final String IF = "if";
    public static final String MIN = "min";
    public static final String MAX = "max";
    public static final String CEIL = "ceil";
    
    static class Fact extends EvaluationFunction{
    	public long fact(long n){
    		if(n<=1) return 1;
    		return fact(n-1)*n;
    	}
    	@Override
    	public Object handle(List<? extends Object> parameters) {
    		Object arg = parameters.get(0);
    		Long val = PrimitiveUtils.genericNumberCast(arg, Long.class);
    		return fact(val);
    	}
    	@Override
    	@SuppressWarnings("unchecked")
    	public Collection<? extends Class<?>> acceptedTypes(int num) {
    		return Arrays.asList(Long.class, Double.class);    		
    	}
    }
    
    static class Abs extends EvaluationFunction{
    	@Override
    	public Object handle(List<? extends Object> parameters) {
    		Object arg = parameters.get(0);
    		if(arg instanceof Double) return Math.abs((Double)arg);
    		if(arg instanceof Long) return Math.abs((Long)arg);
    		return null;
    	}
    	@Override
    	@SuppressWarnings("unchecked")
    	public Collection<? extends Class<?>> acceptedTypes(int num) {
    		return Arrays.asList(Long.class, Double.class);    		
    	}
    }
    
    static class Round extends EvaluationFunction{
    	@Override
    	public Object handle(List<? extends Object> parameters) {
    		Object arg = parameters.get(0);
    		if(arg instanceof Double) return Math.round((Double)arg);
    		if(arg instanceof Long) return Math.round((Long)arg);
    		return null;
    	}
    	@Override
    	@SuppressWarnings("unchecked")
    	public Collection<? extends Class<?>> acceptedTypes(int num) {
    		return Arrays.asList(Long.class, Double.class);
    	}
    }
    
    static class If extends EvaluationFunction{
    	@Override
    	public boolean lazyArguments() { 
    		return true;    		
    	}
    	@Override
    	public Object handle(List<? extends Object> parameters) {
    		LazyValue<?> condition = lazyValues().get(0);
    		if((Boolean)condition.load()){
    			return lazyValues().get(1).load();
    		} else {
    			return lazyValues().get(2).load();
    		}
    	}
    }

    static class Min extends EvaluationFunction{
        @Override
        @SuppressWarnings("unchecked")
        public Object handle(List<? extends Object> parameters) {
            Object param0 = parameters.get(0);
            Object param1 = parameters.get(1);
            DataTypes common = ASTHelper.getCommonType(param0, param1);
            Object param0Common = PrimitiveUtils.genericNumbetCast(param0, common);
            Object param1Common = PrimitiveUtils.genericNumbetCast(param1, common);
            if (((Comparable)param0Common).compareTo(param1Common) <= 0) {
                return param0Common;
            } else {
                return param1Common;
            }
        }
        @Override
        public Collection<? extends Class<?>> acceptedTypes(int num) {
            return Arrays.asList(Long.class, Double.class);
        }
    }

    static class Ceil extends EvaluationFunction {
        @Override
        public Object handle(List<? extends Object> parameters) {
            Object l = parameters.get(0);
            return Math.ceil(PrimitiveUtils.genericNumberCast(l, Double.class));
        }
        @Override
        public Collection<? extends Class<?>> acceptedTypes(int num) {
            return Arrays.asList(Long.class, Double.class);
        }
    }
    

    static class Max extends EvaluationFunction{
        @Override
        @SuppressWarnings("unchecked")
        public Object handle(List<? extends Object> parameters) {
            Object param0 = parameters.get(0);
            Object param1 = parameters.get(1);
            DataTypes common = ASTHelper.getCommonType(param0, param1);
            Object param0Common = PrimitiveUtils.genericNumbetCast(param0, common);
            Object param1Common = PrimitiveUtils.genericNumbetCast(param1, common);
            if (((Comparable)param0Common).compareTo(param1Common) >= 0) {
                return param0Common;
            } else {
                return param1Common;
            }
        }
        @Override
        public Collection<? extends Class<?>> acceptedTypes(int num) {
            return Arrays.asList(Long.class, Double.class);
        }
    }
   
    private static Map<String, EvaluationFunction> functions = new HashMap<String, EvaluationFunction>(){{
    	put(FACT, new Fact());
    	put(ABS, new Abs());
    	put(ROUND, new Round());
    	put(IF, new If());
        put(MIN, new Min());
        put(MAX, new Max());
        put(CEIL, new Ceil());
    }};
    
    public static Map<String, EvaluationFunction> getFunctions() {
		return functions;
	}

    public static void contribute(Map<String, EvaluationFunction> funcs){
        funcs.putAll(functions);
    }

}
