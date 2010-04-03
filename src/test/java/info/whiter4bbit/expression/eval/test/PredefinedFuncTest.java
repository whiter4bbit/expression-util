package info.whiter4bbit.expression.eval.test;

import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.eval.ExpressionEval;
import info.whiter4bbit.expression.interpreter.StandardEvaluationVisitor;
import junit.framework.TestCase;

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
		
		eval.setVariable("v", -100.);
		val = eval.eval("abs(${v})");
		assertEquals(100., val);
		
		val = eval.eval("round(112.6)");
		assertEquals(113L, val);
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
	
}
