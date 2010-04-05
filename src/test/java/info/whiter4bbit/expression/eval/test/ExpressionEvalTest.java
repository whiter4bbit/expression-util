package info.whiter4bbit.expression.eval.test;

import junit.framework.TestCase;
import info.whiter4bbit.expression.eval.ExpressionEval;
import info.whiter4bbit.expression.utils.VariablesLoader;

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

    public void testBigExpressions() throws Exception {
        String expr = "-2906635.0+5.290056676E10+5634780.0+-2.53213521329638E14+5.3233887366E11+3.579947602E9+4.813973E7+5.32477E7+-2.930267E7+2.0519451E7+4.230184E7+5.326856761E9+3.5745688441E10+5.307618416E9";
        ExpressionEval eval = new ExpressionEval();
        Object val = eval.eval(expr);
        assertEquals(-252578184143802.0, val);
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
	
	public void testVariablesLoader() throws Exception{
		ExpressionEval eval = new ExpressionEval();
		eval.setVariablesLoader(new VariablesLoader(){
			@Override
			public Object load(String name) {
				return 1L;
			}
		});
		Object v = eval.eval("${var0}+${var}");
		assertEquals(2L, v);
	}
	
	public void testString() throws Exception{
		ExpressionEval eval = new ExpressionEval();
		eval.registerPredefined();
		String res = (String)eval.eval("if(true,'all ok!','fail:(')&'<- look at result");
		
		assertEquals("all ok!<- look at result", res);
	}
	
}
