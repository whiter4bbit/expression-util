package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.expression.utils.EvaluationFunction;
import info.whiter4bbit.util.expression.utils.PrimitiveUtils;

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
    
    static class Fact extends EvaluationFunction{
    	public long fact(long n){
    		if(n<=1) return 1;
    		return fact(n-1)*n;
    	}
    	@Override
    	public Object handle(List<? extends Object> parameters) {
    		Object arg = parameters.get(0);
    		Long val = (Long)PrimitiveUtils.genericNumberCast(arg, Long.class);
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
    	public Collection<? extends Class> acceptedTypes(int num) {
    		return Arrays.asList(Long.class, Double.class);
    	}
    }
   
    private static Map<String, EvaluationFunction> functions = new HashMap<String, EvaluationFunction>(){{
    	put(FACT, new Fact());
    	put(ABS, new Abs());
    	put(ROUND, new Round());
    }};
    
    public static Map<String, EvaluationFunction> getFunctions() {
		return functions;
	}

    public static void contribute(Map<String, EvaluationFunction> funcs){
        funcs.putAll(functions);
    }

}
