package info.whiter4bbit.expression.interpreter;

/**
 * EvalutionVisitorException
 * Date: 29.03.2010
 */
public class EvalutionVisitorException extends RuntimeException{

	private static final long serialVersionUID = 7960716340138988567L;

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
