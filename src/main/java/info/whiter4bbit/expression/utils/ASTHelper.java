package info.whiter4bbit.expression.utils;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.expression.ast.AST;
import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.ast.VariableAST;
import info.whiter4bbit.expression.ast.visitor.DefaultVisitor;
import java.util.*;

import static info.whiter4bbit.expression.ExpressionConstants.*;

/**
 * ASTHelpers
 * Date: 31.03.2010
 */
public class ASTHelper {

    private static List<String> stringOperations = Arrays.asList(OP_CONCAT);

    private static List<String> numberOperations = Arrays.asList(OP_PLUS, OP_MINUS, OP_DIV, OP_MUL, OP_POW);

    private static List<String> logicOperations = Arrays.asList(OP_LT, OP_GT, OP_LE, OP_GE, OP_EQ, OP_NEQ, OP_AND, OP_OR);
	
    private static class DataTypesVisitor extends DefaultVisitor {
    	
    	private VariablesLoader loader;
    	
    	public void setLoader(VariablesLoader loader) {
			this.loader = loader;
		}
    	
        @Override
        public DataTypes visit(AST ast) {
            return DataTypes.NONE;
        }

        @Override
        public DataTypes visitBinOP(BinOP binOP) {
            String op = binOP.getOperation();
            if(stringOperations.contains(op)){
                return DataTypes.STRING;
            }
            if(numberOperations.contains(op)){
                DataTypes typeL = (DataTypes)binOP.getChild().get(0).visit(this);
                DataTypes typeR = (DataTypes)binOP.getChild().get(1).visit(this);
                if(typeL==DataTypes.DOUBLE || typeR==DataTypes.DOUBLE)
                    return DataTypes.DOUBLE;
                if(typeL!=DataTypes.NONE){
                    return typeL;
                }
                if(typeR!=DataTypes.NONE){
                    return typeR;
                }
                return DataTypes.NONE;
            }
            if(logicOperations.contains(op)){
                return DataTypes.BOOLEAN;
            }
            return null;
        }

        @Override
        public DataTypes visitNumber(NumberAST number) {
            return number.getType();
        }
        
        @Override
        public DataTypes visitVariable(VariableAST variableAST) {
        	String name = variableAST.getName();
        	if(loader.load(name) instanceof Double) return DataTypes.DOUBLE;        		         	
        	if(loader.load(name) instanceof Long) return DataTypes.LONG;
        	if(loader.load(name) instanceof String) return DataTypes.STRING;
        	return DataTypes.NONE;
        }
    }

    private static DataTypesVisitor visitor = new DataTypesVisitor();

    public static DataTypes getDataType(AST ast){
        return (DataTypes)ast.visit(visitor);
    }
    
    public static DataTypes getDataType(AST ast, VariablesLoader vars){
    	visitor.setLoader(vars);
        return (DataTypes)ast.visit(visitor);
    }    


}
