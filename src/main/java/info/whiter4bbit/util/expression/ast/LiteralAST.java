package info.whiter4bbit.util.expression.ast;

import info.whiter4bbit.util.expression.ast.visitor.Visitor;

/**
 * LiteralAST
 * Date: 31.03.2010
 */
public class LiteralAST extends AST{

    private String literal;

    public LiteralAST(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public String toString() {
        return "Literal: "+literal;
    }
}
