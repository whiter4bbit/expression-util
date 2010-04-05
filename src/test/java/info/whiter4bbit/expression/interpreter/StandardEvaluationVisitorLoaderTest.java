package info.whiter4bbit.expression.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.whiter4bbit.common.util.CollectionUtils;
import info.whiter4bbit.expression.ast.FuncCallAST;
import info.whiter4bbit.expression.ast.FuncParamAST;
import info.whiter4bbit.expression.utils.EvaluationFunction;
import info.whiter4bbit.expression.utils.VariablesLoader;
import junit.framework.TestCase;

public class StandardEvaluationVisitorLoaderTest extends TestCase{
	
	private StandardEvaluationVisitor visitor = new StandardEvaluationVisitor();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFuncVariable(){
		EvaluationFunction func = new EvaluationFunction() {
			@Override
			public Object handle(List<? extends Object> parameters) {
				Long value = 0L;
				for(int i=0;i<3;i++){
					value+=(Long)var("s"+i);
				}
				return value;
			}
		};
		
		visitor.setVariablesLoader(new VariablesLoader(){
			@Override
			public Object load(String name) {
				Map<String, Long> vals = new HashMap<String, Long>();
				vals.put("s0", 1L);
				vals.put("s1", 2L);
				vals.put("s2", 3L);
				return vals.get(name);
			}
		});
		visitor.setFunctions(CollectionUtils.singletonMap("sum", func));
		
		Object res = visitor.visitFunction(new FuncCallAST("sum", new ArrayList<FuncParamAST>()));
		
		assertEquals(6L, res);
	}
	
}
