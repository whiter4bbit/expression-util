package info.whiter4bbit.util.expression.eval;

import java.util.Map;

import info.whiter4bbit.util.CollectionUtils;
import info.whiter4bbit.util.expression.ExpressionLexer;
import info.whiter4bbit.util.expression.ExpressionParser;
import info.whiter4bbit.util.expression.ParserException;
import info.whiter4bbit.util.expression.ast.AST;
import info.whiter4bbit.util.expression.interpreter.PredefinedFunctions;
import info.whiter4bbit.util.expression.interpreter.StandardEvaluationVisitor;
import info.whiter4bbit.util.expression.utils.EvaluationFunction;

public class ExpressionEval {

	private StandardEvaluationVisitor evaluationVisitor;
	
	public ExpressionEval() {
		this.evaluationVisitor = new StandardEvaluationVisitor();
	}
	
	public void registerPredefined(){
		setFunctions(PredefinedFunctions.getFunctions());
	}

	public void setEvaluationVisitor(StandardEvaluationVisitor evaluationVisitor) {
		this.evaluationVisitor = evaluationVisitor;
	}
	
	public void setFunctions(Map<String, EvaluationFunction> funcs){
		evaluationVisitor.setFunctions(funcs);
	}
	
	public void setFunction(String name, EvaluationFunction func){
		evaluationVisitor.setFunctions(CollectionUtils.singletonMap(name, func));
	}
	
	public void setVariable(String name, Object value){
		evaluationVisitor.setVariables(CollectionUtils.singletonMap(name, value));
	}
	
	public void setVariables(Map<String, Object> vars){
		evaluationVisitor.setVariables(vars);
	}
	
	public Object eval(String expr) throws ParserException{
		ExpressionParser parser = new ExpressionParser(new ExpressionLexer(expr));
		AST ast = parser.parse();
		return ast.visit(evaluationVisitor);
	}
	
}
