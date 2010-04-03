package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.expression.ast.*;
import info.whiter4bbit.util.expression.ast.visitor.Visitor;
import info.whiter4bbit.util.expression.utils.EvaluationFunction;
import info.whiter4bbit.util.expression.utils.EvalutionHelpers;
import info.whiter4bbit.util.expression.utils.PrimitiveUtils;

import java.util.*;


/**
 *
 * @author whiter4bbit
 */
@Deprecated
public class EvalutionVisitor extends Visitor {

    private Map<String, EvaluationFunction> functions = new HashMap<String, EvaluationFunction>();

    private Map<String, Object> context;

    public EvalutionVisitor(Map<String, Object> context, Map<String, EvaluationFunction> functions){
        assert (context!=null && functions!=null);

        this.context = context;
        this.functions = functions;
        PredefinedFunctions.contribute(functions);
    }

    public EvalutionVisitor(Map<String, Object> context) {
        this.context = context;
        PredefinedFunctions.contribute(functions);
    }

    public EvalutionVisitor() {
        this(new HashMap<String, Object>());
        PredefinedFunctions.contribute(functions);
    }

    public Object visitBinOP(BinOP binOP){
        AST p1 = (AST)binOP.getChild().toArray()[0];
        AST p2 = (AST)binOP.getChild().toArray()[1];

        return PrimitiveUtils.genericNumberOp( binOP.getOperation(), p1.visit(this), p2.visit(this), binOP.getType().getTypeName());
    }

    public Object visitNumber(NumberAST number){
        return PrimitiveUtils.getFromString(number.getValue(), number.getType());
    }

    public Object visitVariable(VariableAST variableAST){
        return context.get(variableAST.getName());
    }

    @Override
    public Object visitFunction(FuncCallAST funcCall) {
        List<Object> paramsValues = new ArrayList<Object>();
        for(FuncParamAST paramAST : funcCall.getParams()){
            paramsValues.add(paramAST.visit(this));
        }
        EvaluationFunction function = functions.get(funcCall.getFuncName());
        if(function ==null){
            throw new EvalutionVisitorException("Can't find function with name "+funcCall.getFuncName());
        }
        return EvalutionHelpers.callFunction(funcCall.getFuncName(), function, paramsValues);
    }

    @Override
    public Object visitFuncParam(FuncParamAST funcParam) {
        return funcParam.getExpression().visit(this);
    }

    @Override
    public Object visitLiteral(LiteralAST literalAST) {
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOpAST unaryOp) {
        Object singl = unaryOp.getExpression().visit(this);

        return functions.get("-").handle(Arrays.asList(singl));
    }
}
