package info.whiter4bbit.expression.utils;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.expression.ast.*;
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

    private static List<String> logicOperations = Arrays.asList(OP_LT, OP_GT, OP_LE, OP_GE, OP_AND, OP_OR, OP_EQ, OP_NEQ);

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
        public Object visitLiteral(LiteralAST literalAST) {
            return DataTypes.STRING;
        }

        @Override
        public DataTypes visitNumber(NumberAST number) {
            return number.getType();
        }

        @Override
        public Object visitUnaryOp(UnaryOpAST unaryOp) {
            if(unaryOp.getOp().equals(OP_UNARY_MINUS))
                return DataTypes.DOUBLE;
            if(unaryOp.getOp().equals(OP_UNARY_NEG))
                return DataTypes.BOOLEAN;
            return DataTypes.NONE;                    
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

    public static boolean isNumbericType(DataTypes type){
        return type==DataTypes.LONG || type==DataTypes.INTEGER || type==DataTypes.DOUBLE;
    }

    public static DataTypes getCommonType(Object...params) {
        assert params != null;
        DataTypes currentType = DataTypes.NONE;
        for (Object param : params) {
            if( param!=null ){
                DataTypes dataTypes = DataTypes.getByClass(param.getClass());

                if(currentType!=DataTypes.DOUBLE){
                    currentType = dataTypes;
                }
            }
        }
        return currentType;
    }

    public static DataTypes getDataType(AST ast, VariablesLoader vars){
    	visitor.setLoader(vars);
        return (DataTypes)ast.visit(visitor);
    }    


}
