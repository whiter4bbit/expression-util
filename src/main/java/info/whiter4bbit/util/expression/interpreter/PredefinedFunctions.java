package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.expression.utils.EvaluationFunction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PredefinedFunctions
 * Date: 29.03.2010
 */
public class PredefinedFunctions {

    public static final String FACTORIAL = "fact";
    
    static class Fact extends EvaluationFunction{
    	@Override
    	public Object handle(List<? extends Object> parameters) {
    		return null;
    	}
    }
    
    private static Map<String, EvaluationFunction> functions = new HashMap<String, EvaluationFunction>(){{
    	put(FACTORIAL, new Fact());
    }};

    public static void contribute(Map<String, EvaluationFunction> funcs){
        funcs.putAll(functions);
    }

}
