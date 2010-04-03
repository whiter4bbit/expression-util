/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.expression.ast.visitor;

import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.ConstantAST;
import info.whiter4bbit.expression.ast.FuncCallAST;
import info.whiter4bbit.expression.ast.FuncParamAST;
import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.ast.UnaryOpAST;
import info.whiter4bbit.expression.ast.VariableAST;
/**
 *
 * @author whiter4bbit
 */
public abstract class Visitor {

    public Object visitBinOP(BinOP binOP) {return null;}

    public Object visitNumber(NumberAST number) {return null;}

    public Object visitVariable(VariableAST variableAST) {return null;}

    public Object visitFunction(FuncCallAST funcCall) {return null;}

    public Object visitFuncParam(FuncParamAST funcParam) {return null;}

    public Object visitUnaryOp(UnaryOpAST unaryOp) {return null;}

    public Object visitLiteral(LiteralAST literalAST) {return null;}

    public Object visitConstant(ConstantAST constantAST) {return null;}

}
