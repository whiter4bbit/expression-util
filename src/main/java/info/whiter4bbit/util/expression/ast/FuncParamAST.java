package info.whiter4bbit.util.expression.ast;

/**
 * FuncParam
 * Date: 29.03.2010
 */
public class FuncParamAST extends AST{

    private AST expression;

    public FuncParamAST(AST expression) {
        this.expression = expression;
    }

    public AST getExpression() {
        return expression;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitFuncParam(this);
    }

    @Override
    public String toString() {
        return "Param:["+expression+"]";
    }
}
