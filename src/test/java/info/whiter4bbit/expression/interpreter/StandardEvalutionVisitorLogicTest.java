package info.whiter4bbit.expression.interpreter;

import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.ConstantAST;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.ast.UnaryOpAST;
import info.whiter4bbit.expression.interpreter.StandardEvaluationVisitor;
import junit.framework.TestCase;

/**
 * EvalutionVisitorTest
 * Date: 31.03.2010
 */
public class StandardEvalutionVisitorLogicTest extends TestCase{

    private StandardEvaluationVisitor visitor = new StandardEvaluationVisitor();

    public void testVisitBinOPLogic() throws Exception{
        BinOP op = new BinOP("&&", new ConstantAST("true"), new ConstantAST("false"));
        assertEquals(Boolean.FALSE, visitor.visitBinOP(op));

        op = new BinOP("||", new ConstantAST("true"), new ConstantAST("false"));
        assertEquals(Boolean.TRUE, visitor.visitBinOP(op));

        op = new BinOP("<", new NumberAST("3"), new NumberAST("4"));
        assertEquals(Boolean.TRUE, visitor.visitBinOP(op));

        op = new BinOP(">", new NumberAST("4"), new NumberAST("3"));
        assertEquals(Boolean.TRUE, visitor.visitBinOP(op));

        op = new BinOP("!=", new NumberAST("4"), new NumberAST("3"));
        assertEquals(Boolean.TRUE, visitor.visitBinOP(op));
    }
    
    public void testVisitConstant() throws Exception{
        assertEquals(Boolean.TRUE, visitor.visitConstant(new ConstantAST("true")));
        assertEquals(Boolean.FALSE, visitor.visitConstant(new ConstantAST("false")));
    }

    public void testUnaryOP() throws Exception{
        assertEquals(Boolean.TRUE, visitor.visitUnaryOp(new UnaryOpAST("!", new ConstantAST("false"))));

        BinOP op = new BinOP("||", new BinOP("&&", new ConstantAST("true"), new ConstantAST("false")),
                        new ConstantAST("true"));
        
        assertEquals(Boolean.FALSE, visitor.visitUnaryOp(new UnaryOpAST("!", op)));
    }
}
