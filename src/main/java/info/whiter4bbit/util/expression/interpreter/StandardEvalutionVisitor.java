package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.DataTypes;
import info.whiter4bbit.util.expression.ast.*;
import info.whiter4bbit.util.expression.utils.EvalutionFunction;
import info.whiter4bbit.util.expression.utils.EvalutionHelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StandardEvalutionVisitor
 * Date: 31.03.2010
 */
public class StandardEvalutionVisitor extends Visitor{

    private Map<String, Object> variables = new HashMap<String, Object>();

    private Map<String, EvalutionFunction> functions = new HashMap<String, EvalutionFunction>();

    public StandardEvalutionVisitor() {

    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public void setFunctions(Map<String, EvalutionFunction> functions) {
        this.functions = functions;
    }

    private boolean isNumbericType(DataTypes type){
        return type==DataTypes.LONG || type==DataTypes.INTEGER || type==DataTypes.DOUBLE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object visitBinOP(BinOP binOP) {
        if(binOP.getType()==DataTypes.BOOLEAN){
            String operation = binOP.getOperation();
            if (!operation.equals("&&") && !operation.equals("||")) {
                Comparable r = (Comparable) binOP.getChild().get(0).visit(this);
                Comparable l = (Comparable) binOP.getChild().get(1).visit(this);
                int v = r.compareTo(l);
                if ("<".equals(operation)) return v < 0;
                if (">".equals(operation)) return v > 0;
                if ("<=".equals(operation)) return v <= 0;
                if (">=".equals(operation)) return v >= 0;
                if ("!=".equals(operation)) return v != 0;
                if ("==".equals(operation)) return v == 0;
            } else {
                Boolean r = (Boolean) binOP.getChild().get(0).visit(this);
                Boolean l = (Boolean) binOP.getChild().get(1).visit(this);
                if(operation.equals("&&")){
                    return r && l;
                }
                if(operation.equals("||")){
                    return r || l;
                }
            }
        }
        if(isNumbericType(binOP.getType())){
            AST p1 = (AST)binOP.getChild().toArray()[0];
            AST p2 = (AST)binOP.getChild().toArray()[1];

            return PrimitiveUtils.genericNumberOp( binOP.getOperation(),
                                                   p1.visit(this),
                                                   p2.visit(this),
                                                   binOP.getType().getTypeName() );
        }
        if(binOP.getType()==DataTypes.STRING){
            if("&".equals(binOP.getOperation())){
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
        return variables.get(variableAST.getName());
    }

    @Override
    public Object visitFunction(FuncCallAST funcCall) {
        List<Object> paramsValues = new ArrayList<Object>();
        for(FuncParamAST paramAST : funcCall.getParams()){
            paramsValues.add(paramAST.visit(this));
        }
        EvalutionFunction function = functions.get(funcCall.getFuncName());
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
    public Object visitUnaryOp(UnaryOpAST unaryOp) {
        if("-".equals(unaryOp.getOp())){
            Object val = unaryOp.getExpression().visit(this);
            DataTypes type = null;
            if(val.getClass()==Long.class){
                type = DataTypes.LONG;
            }
            if(val.getClass()==Double.class){
                type = DataTypes.DOUBLE;
            }
            return PrimitiveUtils.genericNumberOp("-", 0L, val, type);
        }
        if("!".equals(unaryOp.getOp())){
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
        constants.put("true", Boolean.TRUE);
        constants.put("false", Boolean.FALSE);
    }

    @Override
    public Object visitConstant(ConstantAST constantAST) {
        return constants.get(constantAST.getName());
    }
}
