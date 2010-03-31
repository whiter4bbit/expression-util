package info.whiter4bbit.util.expression.interpreter;

/**
 * EvalutionVisitorException
 * Date: 29.03.2010
 */
public class EvalutionVisitorException extends RuntimeException{

    public EvalutionVisitorException() {
    }

    public EvalutionVisitorException(String message) {
        super(message);
    }

    public EvalutionVisitorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvalutionVisitorException(Throwable cause) {
        super(cause);
    }
}
