package info.whiter4bbit.expression.ast.visitor;

import info.whiter4bbit.expression.ast.AST;
import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.ConstantAST;
import info.whiter4bbit.expression.ast.FuncCallAST;
import info.whiter4bbit.expression.ast.FuncParamAST;
import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.ast.UnaryOpAST;
import info.whiter4bbit.expression.ast.VariableAST;

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
