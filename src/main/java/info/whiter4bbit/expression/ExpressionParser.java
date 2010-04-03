package info.whiter4bbit.expression;

import info.whiter4bbit.common.util.DataTypes;
import info.whiter4bbit.common.util.Tuple;
import info.whiter4bbit.expression.ast.AST;
import info.whiter4bbit.expression.ast.BinOP;
import info.whiter4bbit.expression.ast.ConstantAST;
import info.whiter4bbit.expression.ast.FuncCallAST;
import info.whiter4bbit.expression.ast.FuncParamAST;
import info.whiter4bbit.expression.ast.LiteralAST;
import info.whiter4bbit.expression.ast.NumberAST;
import info.whiter4bbit.expression.ast.UnaryOpAST;
import info.whiter4bbit.expression.ast.VariableAST;

import java.util.*;

import static info.whiter4bbit.common.util.Tuple.*;

/**
 * expression = sub_expr
 * number = integer | double
 * simple_expr = number | literal | variable | func_call | '(' ~ sub_expr ~ ')'
 * unary_op = "-" | "!" | "^"
 * bin_op = "<" | ">" | "<=" | ">=" | "+" | "-" | "*" | "/" | "&&" | "||" | "^"
 * sub_expr = simple_expr  ~ (bin_op ~ sub_expr) | prefix_expr
 * prefix_expr = '(' ~ sub_expr ~ ')'
 * params = fact | fact~','~params
 *
 * @author whiter4bbit
 */
public class ExpressionParser {

    private ExpressionLexer lexer;

    public ExpressionParser(ExpressionLexer lexer) {
        this.lexer = lexer;
    }

    private Stack<Token> forward = new Stack<Token>();

    public Token nextToken() {
        if (forward.isEmpty()) {
            return lexer.lex();
        } else {
            return forward.pop();
        }
    }

    public Token lookForward() {
        if (forward.isEmpty()) {
            Token forw = lexer.lex();
            forward.push(forw);
            return forw;
        } else {
            return forward.peek();
        }
    }

    private void expect(String what) throws ParserException {
        Token next = nextToken();
        if (next == null || next.getType() == ExpressionLexer.EOF) {
            throw new ParserException("Expected " + what + " but EOF");
        }
        if (!what.equals(next.getVal())) {
            throw new ParserException("Expected " + what + " but found " + next.getVal());
        }
    }

    private List<String> binOps = Arrays.asList("<", ">", "<=", ">=", "+", "-", "==", "!=", "*", "/", "&&", "||", "^", "&");

    private List<String> unaryOps = Arrays.asList("-", "!");

    private List<String> constants = Arrays.asList("true", "false");

    private Map<String, Tuple<Byte, Byte>> priorities = new HashMap<String, Tuple<Byte, Byte>>();

    private final byte UNARY_PRIORITY = 8;

    //Operators precedence
    {
        priorities.put("&&", tuple((byte) 1, (byte) 1));
        priorities.put("||", tuple((byte) 1, (byte) 1));

        priorities.put("<", tuple((byte) 2, (byte) 2));
        priorities.put(">", tuple((byte) 2, (byte) 2));
        priorities.put("<=", tuple((byte) 2, (byte) 2));
        priorities.put(">=", tuple((byte) 2, (byte) 2));
        priorities.put("==", tuple((byte) 2, (byte) 2));
        priorities.put("!=", tuple((byte) 2, (byte) 2));

        priorities.put("+", tuple((byte) 3, (byte) 3));
        priorities.put("-", tuple((byte) 3, (byte) 3));

        priorities.put("*", tuple((byte) 4, (byte) 4));
        priorities.put("/", tuple((byte) 4, (byte) 4));
        priorities.put("%", tuple((byte) 4, (byte) 4));

        priorities.put("&", tuple((byte) 6, (byte) 5));

        priorities.put("^", tuple((byte) 8, (byte) 7));
    }

    public AST parse() throws ParserException {
        return parseExpression();
    }

    private AST parseExpression() throws ParserException {
        return parseSubExpr(parseSimpleExpr(), (byte) 0);
    }

    private Token getBinOP(Token tok) {
        if (tok != null && tok.getType() != ExpressionLexer.EOF && binOps.contains(tok.getVal())) {
            return tok;
        }
        return null;
    }

    private Token getUnaryOp(Token tok){
        if(tok!=null && tok.getType() != ExpressionLexer.EOF && unaryOps.contains(tok.getVal())){
            return tok;
        }
        return null;
    }

    /**
     * sub_expr = (simple_expr | un_op sub_expr) ~ (bin_op ~ sub_expr) | prefix_expr
     * @param left left parameter
     * @param minPriority minimal accepted priority
     * @return sub_expr ast
     * @throws ParserException if something went wrong
     */
    private AST parseSubExpr(AST left, byte minPriority) throws ParserException {
        AST current = left;
        if(left==null){
            Token tok = getUnaryOp(lookForward());
            if(tok!=null){
                nextToken();
                AST rightExpr = parseSubExpr(parseSimpleExpr(), UNARY_PRIORITY);
                current = new UnaryOpAST(tok.getVal(), rightExpr);
            }
        }
        while (true) {
            Token tok = getBinOP(lookForward());
            if (tok != null && priorities.get(tok.getVal()).getA() > minPriority) {
                nextToken();
            } else {
                break;
            }
            AST right = parseSubExpr(parseSimpleExpr(), priorities.get(tok.getVal()).getB());
            current = new BinOP(tok.getVal(), current, right);
        }
        return current;
    }

    /**
     * simple_exp = integer | double | variable | literal | '(' ~ sub_expr ~ ')' | constant | func_call
     *
     * @return simple_expr ast
     * @throws ParserException if something went wrong
     */
    private AST parseSimpleExpr() throws ParserException {
        Token token = lookForward();
        if (token == null) {
            return null;
        }
        if (token.getType() == ExpressionLexer.INTEGER) {
            nextToken();
            return new NumberAST(token.getVal(), DataTypes.LONG);
        }
        if (token.getType() == ExpressionLexer.DOUBLE) {
            nextToken();
            return new NumberAST(token.getVal(), DataTypes.DOUBLE);
        }
        if (token.getType() == ExpressionLexer.STRING) {
            nextToken();
            return new LiteralAST(token.getVal());
        }
        if (token.getType() == ExpressionLexer.VARIABLE) {
            nextToken();
            return new VariableAST(token.getVal());
        }
        if (token.getType() == ExpressionLexer.IDENTIFIER) {
            if(constants.contains(token.getVal())){
                nextToken();
                return new ConstantAST(token.getVal());
            }
            return parseFuncCall();
        }
        if ("(".equals(token.getVal())) {
            nextToken();
            AST expr = parseExpression();
            expect(")");
            return expr;
        }
        return null;
    }


    /**
     * func_call = ident ~ (params){0,1}
     * params = param ~ (',' ~ param)*
     * param = expression
     *
     * @return funccall ast
     * @throws ParserException if something went wrong
     */
    private AST parseFuncCall() throws ParserException {
        Token ident = nextToken();
        String funcName = ident.getVal();
        Token nextTok = lookForward();
        if(nextTok!=null && nextTok.getVal().equals("(")){
            nextToken();
        } else {
            return null;
        }
        List<FuncParamAST> funcParams = new ArrayList<FuncParamAST>();
        FuncParamAST param = parseFuncParam();
        while (param != null) {
            funcParams.add(param);
            Token tok = lookForward();
            if (tok == null) {
                break;
            }
            if (")".equals(tok.getVal())){
                break;
            }
            expect(",");
            param = parseFuncParam();
        }
        expect(")");
        return new FuncCallAST(funcName, funcParams);
    }

    private FuncParamAST parseFuncParam() throws ParserException {
        AST expression = parseExpression();
        if(expression==null){
            return null;
        }
        return new FuncParamAST(expression);
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExpressionLexer lexer = new ExpressionLexer("if(true,'all ok!','fail:(')&'<- look at result");

        ExpressionParser parser = new ExpressionParser(lexer);
        AST ast = parser.parse();
        long end = System.currentTimeMillis();

        System.out.println(ast);
        System.out.println((end-start));
    }

}

