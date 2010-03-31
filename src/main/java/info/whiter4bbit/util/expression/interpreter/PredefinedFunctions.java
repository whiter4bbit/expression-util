package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.expression.utils.EvalutionFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PredefinedFunctions
 * Date: 29.03.2010
 */
public class PredefinedFunctions {

    public static String UNARY_MINUS = "unary_minus";
    
    private static Map<String, EvalutionFunction> functions = new HashMap<String, EvalutionFunction>();

    static {
        functions.put(UNARY_MINUS, new EvalutionFunction(){
            @Override
            public Object handle(List<? extends Object> parameters) {
                Object value = parameters.get(0);
                if(value instanceof Double){
                    return -(Double)value;
                }
                if(value instanceof Long){
                    return -(Long)value;
                }
                return null;
            }
        });
    }

    public static void contribute(Map funcs){
        funcs.putAll(functions);
    }

}
