/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression.ast;

import info.whiter4bbit.util.DataTypes;

/**
 *
 * @author whiter4bbit
 */
public class NumberAST extends AST{

    private String value;
    
    private DataTypes dataType;

    public NumberAST(String value) {
        this(value, DataTypes.LONG);
    }
    
    public NumberAST(String value, DataTypes type){
    	this.value = value;
    	this.dataType = type;
    }

    public String getValue() {
        return value;
    }
    
    public DataTypes getType() {
		return dataType;
	}

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visitNumber(this);
    }

    @Override
    public String toString() {
        return "Number: "+value;
    }

}
