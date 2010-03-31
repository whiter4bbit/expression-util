package info.whiter4bbit.util.lazy;

/**
 * LazyValue
 * Date: 25.02.2010
 */
public abstract class LazyValue<A> {

    public abstract F0<A> value();
    
    private A b = null;
    
    public A load(){
        if(b==null){
            b = value().f();
        }
        return b;
    }

    public static<A> LazyValue<A> create(final F0<A> f){
        return new LazyValue<A>() {
            @Override
            public F0<A> value() {
                return f;
            }
        };
    }
    
}
