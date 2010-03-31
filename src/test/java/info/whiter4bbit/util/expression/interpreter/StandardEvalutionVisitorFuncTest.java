package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.expression.ast.ConstantAST;
import info.whiter4bbit.util.expression.ast.FuncCallAST;
import info.whiter4bbit.util.expression.ast.FuncParamAST;
import info.whiter4bbit.util.expression.ast.LiteralAST;
import info.whiter4bbit.util.expression.utils.EvalutionFunction;
import junit.framework.TestCase;

import java.util.*;

/**
 * StandardEvalutionVisitorFuncTest
 * Date: 31.03.2010
 */
public class StandardEvalutionVisitorFuncTest extends TestCase{

    private StandardEvalutionVisitor visitor = new StandardEvalutionVisitor();

    @Override
    protected void setUp() throws Exception {
        Map<String, EvalutionFunction> funcs = new HashMap<String, EvalutionFunction>();
        funcs.put("if", new EvalutionFunction(){
            @Override
            public Object handle(List<? extends Object> parameters) {
                Boolean cond = (Boolean) parameters.get(0);
                String ifTrue = (String) parameters.get(1);
                String ifFalse = (String) parameters.get(2);
                if(cond) return ifTrue; else return ifFalse;
            }

            @Override
            public Collection<? extends Class> acceptedTypes(int num) {
                if(num==0) return Arrays.asList(Boolean.class);
                if(num>0) return Arrays.asList(String.class);
                return Arrays.asList();
            }
        });

        visitor.setFunctions(funcs);
    }

    public void testFuncCall(){
        List<FuncParamAST> params = new ArrayList<FuncParamAST>();
        params.add(new FuncParamAST(new ConstantAST("true")));
        params.add(new FuncParamAST(new LiteralAST("it works!")));
        params.add(new FuncParamAST(new LiteralAST("it doesn't")));
        FuncCallAST funcCallAST = new FuncCallAST("if", params);

        assertEquals("it works!", visitor.visitFunction(funcCallAST));
    }

}
