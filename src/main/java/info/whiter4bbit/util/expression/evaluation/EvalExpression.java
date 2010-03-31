package info.whiter4bbit.util.expression.evaluation;

import info.whiter4bbit.util.expression.ExpressionLexer;
import info.whiter4bbit.util.expression.ExpressionParser;
import info.whiter4bbit.util.expression.ParserException;
import info.whiter4bbit.util.expression.ast.AST;
import info.whiter4bbit.util.expression.ast.visitor.Visitor;

/**
 * EvalExpression
 * Date: 29.03.2010
 */
public class EvalExpression {

    private AST ast;

    private Visitor visitor;

    public EvalExpression(String expression, Visitor visitor) {
        ExpressionParser parser = new ExpressionParser(new ExpressionLexer(expression));
        this.visitor = visitor;
        try {
            this.ast = parser.parse();
        } catch (ParserException e) {
            throw new RuntimeException("When parsing expression "+expression+" :"+e);
        }
    }

    public Object evalute(){
        return ast.visit(visitor);
    }

}
