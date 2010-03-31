/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression;

/**
 *
 * @author whiter4bbit
 */
public class ExpressionLexer {

    public static final int INTEGER = 0;
    
    public static final int DOUBLE = 1;
    
    public static final int STRING = 2;

    public static final int DELIMETER = 3;

    public static final int VARIABLE = 4;                                                  

    public static final int IDENTIFIER = 5;

    public static final int DOT = 6;

    public static final int EOF = 7;

    public static final int ERROR = -1;
    
    private String expression;

    private int pos;

    public ExpressionLexer(String expression) {
        this.expression = expression;
        pos = -1;
    }

    private boolean hasNext() {
        return expression.length()-pos>1;
    }

    private char current(){
        return this.expression.charAt(pos);
    }

    private char next(){
        return this.expression.charAt(++pos);
    }

    private char prev(){
        return this.expression.charAt(--pos);
    }

    private Token parseNumber(){
        char curr = current();
        String val = curr+"";
        while(true){
        	if(!hasNext()){
        		break;
        	}
        	if(!Character.isDigit(curr=next())){
        		prev();
        		break;
        	}
        	val+=curr;
        }
        if(hasNext() && curr=='.'){
        	val+=".";
        	if(hasNext()){
        		next();
        	}
        	while(true){
        		if(!hasNext()){
        			break;
        		}
        		if(!Character.isDigit(curr=next())){
        			prev();
        			break;
        		}
        		val+=curr;
        	}
        	if(hasNext() && ( curr=='e' || curr=='E' )){
        		val+="E";
        		next();
        		boolean countedMinus = false;
        		boolean hasDigit = false;
        		while(true){
        			if(!hasNext()){
        				break;
        			}
        			curr=next();
        			if(curr=='-' && !countedMinus&& !hasDigit){
        				val+=curr;
        				countedMinus=true;
        			} else 
        			if(Character.isDigit(curr)){
        				val+=curr;
        				hasDigit = true;
        			} else {
        				prev();
        				break;
        			}
        		}
        	}
        	return new Token(val, DOUBLE);
        }else{
        	return new Token(val, INTEGER);
        }
    }

    private Token parseVariable(){
        char curr;
        if(hasNext() && next()=='{'){
            String variable = "";
            while(hasNext() && (curr = next())!='}'){            	
                variable+=curr;
            }
            if(current()=='}'){
                return new Token(variable, VARIABLE);
            }
        }
        return new Token("", ERROR);
    }
    
    private Token parseString(){
    	String literal = "";
    	char curr;
    	while(true){
    		if(!hasNext()){
    			break;
    		}
    		if((curr=next())=='\''){
    			break;
    		}
    		literal+=curr;
    	}
    	return new Token(literal, STRING);
    }

    private Token parseIdent(){
        char curr = current();
        String ident = curr+"";
        while(hasNext() ){
            curr = next();
            if(!Character.isLetter(curr) && !Character.isDigit(curr)) break;
            ident+=curr;
        }
        prev();
        return new Token(ident, IDENTIFIER);
    }

    private Token checkNext(char curr, char next) {
        boolean hasNext = hasNext();
        if (hasNext && next() == next) {
            return new Token(String.valueOf(curr)+String.valueOf(next), DELIMETER);
        } else {
            prev();
            return new Token(String.valueOf(curr)+"", DELIMETER);
        }
    }

    public Token lex(){
        if(!hasNext()){
        	return new Token("", EOF);
        }
        char c = next();
        if(Character.isDigit(c)){
            return parseNumber();
        }
        if(Character.isLetter(c)){
            return parseIdent();
        }
        if(c=='\''){
        	return parseString();
        }
        if(c=='$'){
            return parseVariable();
        }
        if(c=='*' || c=='/' || c=='+' || c=='-' || c=='(' || c==')'
        	|| c==',' || c=='^'){
            return new Token(c+"", DELIMETER);
        }
        if(c=='!'){
            return checkNext('!', '=');
        }
        if(c=='='){
            return checkNext('=', '=');
        }
        if(c=='<'){
            return checkNext('<', '=');
        }
        if(c=='>'){
            return checkNext('>', '=');
        }
        if(c=='&'){
            return checkNext('&', '&');
        }
        if(c=='|'){
            return checkNext('|', '|');
        }
        return new Token("", ERROR);
    }

    public static void main(String[] args){
        String expr = "!(2<3+2)&&!(2+1<20)&&!hasErrors()";
        
        ExpressionLexer lexer = new ExpressionLexer(expr);
        Token tok = null;
        
        while((tok=lexer.lex()).getType()!=EOF){
            System.out.println(tok);
        }
    }
    
}
