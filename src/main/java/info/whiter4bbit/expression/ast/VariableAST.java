/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.expression.ast;

import info.whiter4bbit.expression.ast.visitor.Visitor;

/**
 *
 * @author whiter4bbit
 */
public class VariableAST extends AST{

    private String name;

    public VariableAST(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public String toString() {
        return "Variable "+name;
    }
    
}
