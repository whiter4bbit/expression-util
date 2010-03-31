/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression.ast;

import info.whiter4bbit.util.expression.ast.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author whiter4bbit
 */
public abstract class AST {

    private List<AST> child = new ArrayList<AST>();

    public AST() {

    }

    public List<AST> getChild(){
        return child;
    }

    public void setChild(List<AST> child){
        this.child = child;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public abstract Object visit(Visitor visitor);

}
