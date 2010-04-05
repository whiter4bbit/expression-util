package info.whiter4bbit.expression.eval.test;

import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.eval.ExpressionEval;
import info.whiter4bbit.expression.interpreter.StandardEvaluationVisitor;
import info.whiter4bbit.expression.utils.EvaluationFunction;
import junit.framework.TestCase;

import java.util.List;

public class PredefinedFuncTest extends TestCase{

	private ExpressionEval eval;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		eval = new ExpressionEval();
		eval.registerPredefined();
	}
	
	public void testFact() throws Exception{
		Object val = eval.eval("fact(6)");
		assertEquals(720L, val);
    }

    public void testAbs() throws Exception{
        eval.setVariable("v", -100.);
        Object val = eval.eval("abs(${v})");
        assertEquals(100., val);
    }

    public void testRound() throws Exception{
        Object val = eval.eval("round(112.6)");
        assertEquals(113L, val);
    }

    public void testCeil() throws Exception{
        
    }
	
	static class LazyTestVisitorMock extends StandardEvaluationVisitor{
		@Override
		public Object visitLiteral(LiteralAST literalAST) {
			assertTrue(!literalAST.getLiteral().equals("mama"));
			return super.visitLiteral(literalAST);
		}
	}
	
	public void testLazy() throws Exception{
		eval.setEvaluationVisitor(new LazyTestVisitorMock());
		eval.registerPredefined();
		
		Object val = eval.eval("if(12<3,'mama','papa')");
		assertEquals("papa", val);
	}

    public void testIf() throws Exception{
        String expr = "if(${-42940}!='1',1,0)";
        eval.setVariable("-42940", "20");
        Object val = eval.eval(expr);
        assertEquals(1L, val);

        expr = "if(${-1}!='1',if(round(${-2})>2,0,1),-1)";
        eval.setVariable("-1", "2");
        eval.setVariable("-2", 2.6);
        val = eval.eval(expr);
        assertEquals(0L, val);
    }

    public void testComplex() throws Exception {
        String expr = "if(${-1}!='1',if((${-2}+${-3}+${-4})<=(${-5}+${-6}),round(${-5}+${-6}-(${-2}+${-3}+${-4}),0),0),-100)";
        eval.setVariable("-1", "1");
        eval.setVariable("-2", 0d);
        eval.setVariable("-3", 0d);
        eval.setVariable("-4", 0d);
        eval.setVariable("-5", 0d);
        eval.setVariable("-6", 0d);

        Object total = eval.eval(expr);
        assertEquals(-100L, total);

        expr = "if(0==10,0.0,0)";
        total = eval.eval(expr);
        assertEquals(0L, total);
    }

    public void testMin() throws Exception{
        Object val = eval.eval("min(2,3.)");
        assertEquals(2., val);
    }

    public void testMax() throws Exception {
        Object val = eval.eval("max(2,3.)");
        assertEquals(3., val);
    }

    public void testCaseSenc() throws Exception{
        eval.setFunction("none", new EvaluationFunction(){
            @Override
            public Object handle(List<? extends Object> parameters) {
                return "hello!";
            }
        });
        Object val = eval.eval("NoNe()");
        assertEquals(val, "hello!");
    }

}
