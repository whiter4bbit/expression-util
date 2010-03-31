package info.whiter4bbit.util.expression.ast;

/**
 * ConstantAST
 * Date: 31.03.2010
 */
public class ConstantAST extends AST{

    private String name;

    public ConstantAST(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitConstant(this);
    }

    @Override
    public String toString() {
        return "Constant :"+name;
    }
}
