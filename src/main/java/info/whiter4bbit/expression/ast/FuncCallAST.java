package info.whiter4bbit.expression.ast;

import info.whiter4bbit.common.util.StringUtils;
import info.whiter4bbit.expression.ast.visitor.Visitor;

import java.util.*;

/**
 * FuncCall
 * Date: 29.03.2010
 */
public class FuncCallAST extends AST{

    private String funcName;

    private List<FuncParamAST> params = new ArrayList<FuncParamAST>();

    public FuncCallAST(String funcName, List<FuncParamAST> params) {
        this.funcName = funcName;
        this.params = params;
    }

    public String getFuncName() {
        return funcName;
    }

    public List<FuncParamAST> getParams() {
        return params;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitFunction(this);
    }

    @Override
    public String toString() {
        return "FuncCall: "+funcName+" params:["+StringUtils.join(params, ",")+"]";
    }
}
