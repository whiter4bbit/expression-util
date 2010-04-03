package info.whiter4bbit.util.expression.utils;

import info.whiter4bbit.util.expression.interpreter.EvalutionVisitorException;

import java.util.ArrayList;
import java.util.List;

/**
 * EvalutionHelpers
 * Date: 29.03.2010
 */
public class EvalutionHelpers {

    public static Object callFunction(String functionName,
                                      EvaluationFunction function,
                                      List<Object> paramsValues){
        int i=0;
        List<Object> filteredVals = new ArrayList<Object>();

        for(Object val : paramsValues){
            Class<?> type = val.getClass();
            
            if(function.acceptedTypes(i).isEmpty()){
                throw new EvalutionVisitorException("While loading function "+functionName+" implementation:"+function
                        +" : acceptedTypes list can't be empty");
            }
            
            if(!function.acceptedTypes(i).contains(type)){
                Class<?> c = function.acceptedTypes(i).iterator().next();
                try{
                    Object castedVal = PrimitiveUtils.genericNumberCast(val, c);
                    filteredVals.add(castedVal);
                }catch(Exception e){
                    throw new EvalutionVisitorException("Can't cast "+val+" of type "+type+" to required "+c+" when evaluting function "+functionName);
                }
            } else {
                filteredVals.add(val);
            }
            i++;
        }
        return function.handle(filteredVals);
    }

}
