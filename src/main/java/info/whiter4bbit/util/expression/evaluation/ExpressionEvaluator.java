package info.whiter4bbit.util.expression.evaluation;

import info.whiter4bbit.util.expression.interpreter.EvalutionVisitor;
import info.whiter4bbit.util.expression.utils.EvalutionFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * ExpressionEvaluator
 * Date: 29.03.2010
 */
public class ExpressionEvaluator {

    private Map<String, Object> context = new HashMap<String, Object>();

    private Map<String, EvalutionFunction> functions = new HashMap<String, EvalutionFunction>();

    private EvalutionVisitor evalutionVisitor = new EvalutionVisitor(context, functions);

    public ExpressionEvaluator() {

    }

    public void registerFunction(String name, EvalutionFunction function){
        assert(name!=null && function!=null);

        functions.put(name, function);
    }

    public void registerFunctions(Map<String, EvalutionFunction> functions){
        assert (functions!=null);

        functions.putAll(functions);
    }

    public void contributeContext(Map<String, Object> context){
        assert(context!=null);
        
        this.context.putAll(context);
    }

    public void contributeContext(String name, Object value){
        assert(name!=null && value!=null);

        context.put(name, value);
    }

    public EvalExpression createExpression(String expression){
        return new EvalExpression(expression, evalutionVisitor);
    }

    public static void main(String[] args) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.contributeContext("val", 2.);
        EvalExpression eval = evaluator.createExpression("(${val}+2)*2");

        System.out.println(eval.evalute());
        evaluator.contributeContext("val", 3.);
        System.out.println(eval.evalute());
        
    }

}
