package info.whiter4bbit.expression.interpreter;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.common.util.lazy.F0;
import info.whiter4bbit.common.util.lazy.LazyValue;
import info.whiter4bbit.expression.ast.AST;
import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.ConstantAST;
import info.whiter4bbit.expression.ast.FuncCallAST;
import info.whiter4bbit.expression.ast.FuncParamAST;
import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.ast.UnaryOpAST;
import info.whiter4bbit.expression.ast.VariableAST;
import info.whiter4bbit.expression.ast.visitor.Visitor;
import info.whiter4bbit.expression.utils.ASTHelper;
import info.whiter4bbit.expression.utils.EvaluationFunction;
import info.whiter4bbit.expression.utils.EvalutionHelpers;
import info.whiter4bbit.expression.utils.PrimitiveUtils;
import info.whiter4bbit.expression.utils.VariablesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static info.whiter4bbit.expression.ExpressionConstants.*;

/**
 * StandardEvalutionVisitor
 * Date: 31.03.2010
 */
public class StandardEvaluationVisitor extends Visitor {

    private Map<String, Object> variables = new HashMap<String, Object>();

    private Map<String, EvaluationFunction> functions = new HashMap<String, EvaluationFunction>();
    
    private VariablesLoader variablesLoader;

    public StandardEvaluationVisitor() {
    	variablesLoader = new VariablesLoader(){
    		@Override
    		public Object load(String name) {
    			return variables.get(name);
    		}
    	};
    }
    
    public void setVariablesLoader(VariablesLoader variablesLoader) {
		this.variablesLoader = variablesLoader;
	}

    public void setVariables(Map<String, Object> variables) {
        this.variables.putAll(variables);
    }

    public void setFunctions(Map<String, EvaluationFunction> functions) {
        this.functions.putAll(functions);
    }

    private boolean isNumbericType(DataTypes type){
        return type==DataTypes.LONG || type==DataTypes.INTEGER || type==DataTypes.DOUBLE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object visitBinOP(BinOP binOP) {
        DataTypes dataTypes = ASTHelper.getDataType(binOP, variablesLoader);
        
        if(dataTypes==DataTypes.BOOLEAN){
            String operation = binOP.getOperation();
            if (!operation.equals(OP_AND) && !operation.equals(OP_OR)) {
                Comparable r = (Comparable) binOP.getChild().get(0).visit(this);
                Comparable l = (Comparable) binOP.getChild().get(1).visit(this);
                int v = r.compareTo(l);
                if (OP_LT.equals(operation))  return v < 0;
                if (OP_GT.equals(operation))  return v > 0;
                if (OP_LE.equals(operation))  return v <= 0;
                if (OP_GE.equals(operation))  return v >= 0;
                if (OP_NEQ.equals(operation)) return v != 0;
                if (OP_EQ.equals(operation))  return v == 0;
            } else {
                Boolean r = (Boolean) binOP.getChild().get(0).visit(this);
                Boolean l = (Boolean) binOP.getChild().get(1).visit(this);
                if(operation.equals(OP_AND)){
                    return r && l;
                }
                if(operation.equals(OP_OR)){
                    return r || l;
                }
            }
        }
        if(isNumbericType(dataTypes)){
            AST p1 = (AST)binOP.getChild().toArray()[0];
            AST p2 = (AST)binOP.getChild().toArray()[1];
            
            return PrimitiveUtils.genericNumberOp( binOP.getOperation(), p1.visit(this), p2.visit(this), dataTypes );
        }
        if(dataTypes==DataTypes.STRING){
            if(OP_CONCAT.equals(binOP.getOperation())){
                String l = (String) binOP.getChild().get(0).visit(this);
                String r = (String) binOP.getChild().get(1).visit(this);
                return l+r;
            }
        }
        return null;
    }

    @Override
    public Object visitNumber(NumberAST number) {
        return PrimitiveUtils.getFromString(number.getValue(), number.getType());
    }

    @Override
    public Object visitVariable(VariableAST variableAST) {
    	return variablesLoader.load(variableAST.getName());
    }

	@Override
	public Object visitFunction(FuncCallAST funcCall) {
		EvaluationFunction function = functions.get(funcCall.getFuncName());
		if (function == null) {
			throw new EvalutionVisitorException("Can't find function with name " + funcCall.getFuncName());
		}
		function.setVariablesLoader(variablesLoader);
		if (!function.lazyArguments()) {
			List<Object> paramsValues = new ArrayList<Object>();
			for (FuncParamAST paramAST : funcCall.getParams()) {
				paramsValues.add(paramAST.visit(this));
			}
			return EvalutionHelpers.callFunction(funcCall.getFuncName(), function, paramsValues);
		} else {
			List<LazyValue<?>> values = new ArrayList<LazyValue<?>>();
			for(final FuncParamAST paramAST : funcCall.getParams()){
				values.add(LazyValue.create(new F0<Object>() {
					@Override
					public Object f() {
						return paramAST.visit(StandardEvaluationVisitor.this);
					}
				}));
			}
			function.setLazyValues(values);
			return function.handle(Arrays.asList());
		}
	}

    @Override
    public Object visitFuncParam(FuncParamAST funcParam) {
        return funcParam.getExpression().visit(this);
    }

    @Override
    public Object visitUnaryOp(UnaryOpAST unaryOp) {
        if(OP_UNARY_MINUS.equals(unaryOp.getOp())){
            AST express = unaryOp.getExpression();
            DataTypes type = ASTHelper.getDataType(express);
            return PrimitiveUtils.genericNumberOp(OP_UNARY_MINUS, 0L, express.visit(this), type);
        }
        if(OP_UNARY_NEG.equals(unaryOp.getOp())){
            return !(Boolean)unaryOp.getExpression().visit(this);
        }
        return null;
    }

    @Override
    public Object visitLiteral(LiteralAST literalAST) {
        return literalAST.getLiteral();
    }

    private static Map<String, Object> constants = new HashMap<String, Object>();

    static {
        constants.put(CONST_TRUE, Boolean.TRUE);
        constants.put(CONST_FALSE, Boolean.FALSE);
    }

    @Override
    public Object visitConstant(ConstantAST constantAST) {
        return constants.get(constantAST.getName());
    }
}
