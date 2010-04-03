package info.whiter4bbit.expression.eval.test;

import junit.framework.TestCase;
import info.whiter4bbit.expression.eval.ExpressionEval;

public class ExpressionEvalTest extends TestCase{

	public void testCalc() throws Exception{
		ExpressionEval eval = new ExpressionEval();

		Object val = eval.eval("2+2*2");
		assertEquals(6L, val);
		
		val = eval.eval("(100./2)==50.");
		assertEquals(Boolean.TRUE, val);
		
		val = eval.eval("10*10-1000*300+10/2+300/10-200*500/1234-4444444*2222222*1111");
		assertEquals(-10972837311904994L, val);
	}
	
	public void testVars() throws Exception{
		ExpressionEval eval = new ExpressionEval();
		eval.setVariable("expr", 100.);
		eval.setVariable("expr2", 200.);
		
		Object val = eval.eval("${expr}+${expr2}");
		assertEquals(300., val);
		
		eval.setVariable("expr", "mama");
		eval.setVariable("expr2", "myla");
		eval.setVariable("expr3", "ramu");
		
		val = eval.eval("${expr}&${expr2}&${expr3}");
		assertEquals("mamamylaramu", val);
	}
	
}
