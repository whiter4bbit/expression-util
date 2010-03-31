/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.util.expression;

/**
 *
 * @author whiter4bbit
 */
public class ParserException extends Exception{

	private static final long serialVersionUID = -3221820291805131686L;

	public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException() {
    }

}
