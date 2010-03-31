/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression.ast;

import info.whiter4bbit.util.DataTypes;
import info.whiter4bbit.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author whiter4bbit
 */
public class BinOP extends AST{

    private String operation;

    public BinOP(String operation, AST ast1, AST ast2) {
        this.operation = operation;
        List<AST> child = new ArrayList<AST>();
        child.add(ast1);
        child.add(ast2);
        this.setChild(child);
    }

    public AST left(){
    	return getChild().get(0);
    }
    
    public AST right(){
    	return getChild().get(1);
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitBinOP(this);
    }

    private List<String> binaryOperators = Arrays.asList("<",">","<=",">=","!=","==", "&&", "||");

    private List<String> stringOperators = Arrays.asList("&");

    public DataTypes getType() {
    	DataTypes type = null;
        if(binaryOperators.contains(getOperation())){
            return DataTypes.BOOLEAN;
        }
        if(stringOperators.contains(getOperation())){
            return DataTypes.STRING;
        }
    	if(left() instanceof NumberAST){
    		type = ((NumberAST)left()).getType();
    	}
    	if(right() instanceof NumberAST){
    		type = ((NumberAST)right()).getType();
    	}
    	if(left() instanceof BinOP){
    		type = ((BinOP)left()).getType();
    	}
    	if(right() instanceof BinOP){
    		type = ((BinOP)right()).getType();
    	}
    	return type;
    }

    @Override
    public String toString() {
        return "BinOP: "+operation+" ("+ StringUtils.join(getChild(),",")+")";
    }

    public static void main(String[] args) {
        System.out.println("Inferencing:"+(2.+1));
    }
}
