package info.whiter4bbit.util.expression.eval.test;

import junit.framework.TestCase;
import info.whiter4bbit.util.expression.eval.ExpressionEval;

public class ExpressionEvalTest extends TestCase{

	public void testCalc() throws Exception{
		ExpressionEval eval = new ExpressionEval();

		long startTime = System.currentTimeMillis();
		Object val = eval.eval("2+2*2");
		assertEquals(6L, val);
		
		val = eval.eval("(100./2)==50.");
		assertEquals(Boolean.TRUE, val);
		
		val = eval.eval("10*10-1000*300+10/2+300/10-200*500/1234-4444444*2222222*1111");
		assertEquals(-10972837311904994L, val);
		System.out.println(System.currentTimeMillis()-startTime);
	}
	
}
