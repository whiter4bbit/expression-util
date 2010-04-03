package info.whiter4bbit.common.util;

public class Tuple<A,B> {
	
	private final A a;
	
	private final B b;
	
	public Tuple(A a, B b){
		this.a = a;
		this.b = b;
	}
	
	public A getA() {
		return a;
	}
	
	public B getB() {
		return b;
	}

    public static<A,B> Tuple<A,B> tuple(A a, B b){
        return new Tuple<A,B>(a,b);
    }
}
