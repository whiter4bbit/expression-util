package info.whiter4bbit.expression.interpreter;

import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.ast.NumberAST;
import junit.framework.TestCase;

import static info.whiter4bbit.expression.ExpressionConstants.*;

/**
 * StandardEvaluationVisitorStringTest
 * Date: 05.04.2010
 */
public class StandardEvaluationVisitorStringTest extends TestCase{

    private StandardEvaluationVisitor evaluationVisitor;

    private LiteralAST[] literals = new LiteralAST[]{
            new LiteralAST("homer"),
            new LiteralAST("marge"),
            new LiteralAST("bart"),
            new LiteralAST("liza")
    };

    @Override
    protected void setUp() throws Exception {
        evaluationVisitor = new StandardEvaluationVisitor();
    }

    public void testConcat() throws Exception {
        BinOP concat = new BinOP(OP_CONCAT, literals[0], literals[1]);
        Object val = evaluationVisitor.visitBinOP(concat);
        assertEquals(literals[0].getLiteral()+literals[1].getLiteral(), val);
    }

    public void testEquals() throws Exception {
        BinOP equals = new BinOP(OP_EQ, literals[0], literals[1]);
        Object val = evaluationVisitor.visitBinOP(equals);
        assertEquals(Boolean.FALSE, val);

        equals = new BinOP(OP_EQ, literals[0], literals[0]);
        val = evaluationVisitor.visitBinOP(equals);
        assertEquals(Boolean.TRUE, val);
    }

    public void testNotEquals() throws Exception {
        BinOP equals = new BinOP(OP_NEQ, literals[0], literals[1]);
        Object val = evaluationVisitor.visitBinOP(equals);
        assertEquals(Boolean.TRUE, val);

        equals = new BinOP(OP_NEQ, literals[0], literals[0]);
        val = evaluationVisitor.visitBinOP(equals);
        assertEquals(Boolean.FALSE, val);
    }

    public void testEqualsWithCast() throws Exception{
        BinOP eq = new BinOP(OP_EQ, new NumberAST("1"), new LiteralAST("1"));
        Object val = evaluationVisitor.visitBinOP(eq);
        assertEquals(Boolean.FALSE, val);
    }

}
