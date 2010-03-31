/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.util.expression.ast.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author whiter4bbit
 */
public class VariablesVisitor extends Visitor{

    private Set<String> variables = Collections.synchronizedSet(new HashSet<String>());

    @Override
    public Object visitBinOP(BinOP binOP) {
        for(AST ast : binOP.getChild()){
            ast.visit(this);
        }
        return null;
    }

    @Override
    public Object visitNumber(NumberAST number) {
        return null;
    }

    @Override
    public Object visitVariable(VariableAST variableAST) {
        return variables.add(variableAST.getName());
    }

    public Set<String> getVariables() {
        return variables;
    }

    @Override
    public Object visitFunction(FuncCallAST funcCall) {
        return null;
    }

    @Override
    public Object visitFuncParam(FuncParamAST funcParam) {
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOpAST unaryOp) {
        return null;
    }

    @Override
    public Object visitLiteral(LiteralAST literalAST) {
        return null;
    }
}
