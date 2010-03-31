package info.whiter4bbit.util.expression.ast.visitor;

import info.whiter4bbit.util.expression.ast.*;

/**
 * DefaultVisitor
 * Date: 31.03.2010
 */
public abstract class DefaultVisitor extends Visitor{

    public abstract Object visit(AST ast);

    @Override
    public Object visitBinOP(BinOP binOP) {
        return visit(binOP);
    }

    @Override
    public Object visitNumber(NumberAST number) {
        return visit(number);
    }

    @Override
    public Object visitVariable(VariableAST variableAST) {
        return visit(variableAST);
    }

    @Override
    public Object visitFunction(FuncCallAST funcCall) {
        return visit(funcCall);
    }

    @Override
    public Object visitFuncParam(FuncParamAST funcParam) {
        return visit(funcParam);
    }

    @Override
    public Object visitUnaryOp(UnaryOpAST unaryOp) {
        return visit(unaryOp);
    }

    @Override
    public Object visitLiteral(LiteralAST literalAST) {
        return visit(literalAST);
    }

    @Override
    public Object visitConstant(ConstantAST constantAST) {
        return visit(constantAST);
    }
}
