package info.whiter4bbit.util.expression.utils;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.utils.ASTHelper;
import junit.framework.TestCase;

/**
 * ASTHelperTest
 * Date: 31.03.2010
 */
public class ASTHelperTest extends TestCase{

    public void testGetDataType(){
        BinOP binOP = new BinOP("+", new NumberAST("12"), new NumberAST("22.0", DataTypes.DOUBLE));
        
        assertEquals(DataTypes.DOUBLE, ASTHelper.getDataType(binOP));

        binOP = new BinOP("<", new BinOP("-", new NumberAST("12"), new NumberAST("22")), new NumberAST("23"));
        
        assertEquals(DataTypes.BOOLEAN, ASTHelper.getDataType(binOP));

        binOP = new BinOP("&", new BinOP("&", new LiteralAST("mama"), new LiteralAST("myla")), new LiteralAST("ramy"));

        assertEquals(DataTypes.STRING, ASTHelper.getDataType(binOP));
    }

}
