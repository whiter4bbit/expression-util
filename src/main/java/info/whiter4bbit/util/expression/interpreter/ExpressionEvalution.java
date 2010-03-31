/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.CollectionUtils;
import info.whiter4bbit.util.expression.ExpressionLexer;
import info.whiter4bbit.util.expression.ExpressionParser;
import info.whiter4bbit.util.expression.ParserException;
import info.whiter4bbit.util.expression.ast.AST;
import info.whiter4bbit.util.expression.ast.visitor.Visitor;
import info.whiter4bbit.util.expression.utils.EvalutionFunction;

import java.util.*;

/**
 *
 * @author whiter4bbit
 */
public class ExpressionEvalution {

    private ExpressionParser parser;

    protected ExpressionEvalution(ExpressionParser parser) {
        this.parser = parser;
    }

    protected ExpressionEvalution(String expression) {
        this.parser =  new ExpressionParser(new ExpressionLexer(expression));
    }

    private Map<String, EvalutionFunction> functions = new HashMap<String, EvalutionFunction>();

    {
        functions.put("fact", new EvalutionFunction(){
            
            private Long f(Long n){
                if(n<=1) return 1L;
                return f(n-1)*n;
            }

            @Override
            public Object handle(List<? extends Object> parameters) {
                Long val = (Long)parameters.get(0);
                return f(val);
            }

            @Override
            public Collection<? extends Class> acceptedTypes(int num) {
                return CollectionUtils.set(Long.class);
            }
        });

        functions.put("abs", new EvalutionFunction(){
            @Override
            public Object handle(List<? extends Object> parameters) {
                return Math.abs((Double)PrimitiveUtils.genericNumberCast(parameters.get(0), "double"));
            }
        });

    }

    public Object evalute(Map<String, Object> context) throws ParserException{
        if(context==null){
            throw new IllegalArgumentException("Context can't be null");
        }
        Visitor visitor = new EvalutionVisitor(context, functions);
        AST ast = parser.parse();
        return ast.visit(visitor);
    }

    public Object evalute() throws ParserException{
        return evalute(new HashMap<String, Object>());
    }

    public Set<String> getVariables() throws ParserException{
        VariablesVisitor variablesVisitor = new VariablesVisitor();
        AST ast = parser.parse();
        ast.visit(variablesVisitor);
        return variablesVisitor.getVariables();
    }

    public static Object evalute(String expression) throws ParserException {
        return new ExpressionEvalution(expression).evalute();
    }

    public static Object evalute(String expression, Map<String, Object> context) 
    	throws ParserException {
        return new ExpressionEvalution(expression).evalute(context);
    }

    public static Set<String> getVariables(String expression) throws ParserException{
        return new ExpressionEvalution(expression).getVariables();
    }

    public static void main(String[] args) throws Exception {
        Object val1 = ExpressionEvalution.evalute("fact(123.)+123.");
        
        System.out.println(val1);
        System.out.println(val1.getClass());
    }
    
}
