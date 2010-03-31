package info.whiter4bbit.util.expression.ast;

import info.whiter4bbit.util.DataTypes;

/**
 * UnaryOp
 * Date: 29.03.2010
 */
public class UnaryOpAST extends AST{

    private String op;

    private AST expression;

    public UnaryOpAST(String op, AST expression) {
        this.op = op;
        this.expression = expression;
    }

    public String getOp() {
        return op;
    }
    
    public AST getExpression() {
        return expression;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitUnaryOp(this);
    }

    @Override
    public String toString() {
        return "UnaryOp: '"+op+"' to ["+expression+"]";
    }
}
