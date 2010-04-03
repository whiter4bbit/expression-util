package info.whiter4bbit.util.expression.interpreter;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.interpreter.StandardEvaluationVisitor;
import junit.framework.TestCase;

/**
 * StandardEvalutionVisitorMathTest
 * Date: 31.03.2010
 */
public class StandardEvalutionVisitorArithmTest extends TestCase{

    private StandardEvaluationVisitor visitor = new StandardEvaluationVisitor();

    public void testVisitBinOP() throws Exception {
        BinOP binOP = new BinOP("+", new NumberAST("2"), new BinOP("*", new NumberAST("2"), new NumberAST("2"))); //2+2*2
        assertEquals(6L, visitor.visitBinOP(binOP));

        //2^2^3
        binOP = new BinOP("^", new NumberAST("2"), new BinOP("^", new NumberAST("2"), new NumberAST("3")));
        assertEquals(256L, visitor.visitBinOP(binOP));

        binOP = new BinOP("^", new NumberAST("2.0", DataTypes.DOUBLE), new BinOP("^", new NumberAST("2.0", DataTypes.DOUBLE), new NumberAST("3.0", DataTypes.DOUBLE)));
        assertEquals(256d, visitor.visitBinOP(binOP));        
    }

}
