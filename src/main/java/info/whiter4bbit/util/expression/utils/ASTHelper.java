package info.whiter4bbit.util.expression.utils;

import info.whiter4bbit.util.DataTypes;
import info.whiter4bbit.util.expression.ast.*;
import info.whiter4bbit.util.expression.ast.visitor.DefaultVisitor;
import java.util.*;
import static info.whiter4bbit.util.expression.ExpressionConstants.*;

/**
 * ASTHelpers
 * Date: 31.03.2010
 */
public class ASTHelper {

    private static class DataTypesVisitor extends DefaultVisitor {
        @Override
        public DataTypes visit(AST ast) {
            return DataTypes.NONE;
        }

        private List<String> stringOperations = Arrays.asList(OP_CONCAT);

        private List<String> numberOperations = Arrays.asList(OP_PLUS, OP_MINUS, OP_DIV, OP_MUL, OP_POW);

        private List<String> logicOperations = Arrays.asList(OP_LT, OP_GT, OP_LE, OP_GE, OP_EQ, OP_NEQ, OP_AND, OP_OR);

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
    }

    private static DataTypesVisitor visitor = new DataTypesVisitor();

    public static DataTypes getDataType(AST ast){
        return (DataTypes)ast.visit(visitor);
    }

}
