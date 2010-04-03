package info.whiter4bbit.common.util.lazy;

/**
 * FuncTools
 * Date: 10.03.2010
 */
public class FuncTools {

    public static<A, B extends A> F0<A> forValue(final B a){
        return new F0<A>(){
            @Override
            public A f() {
                return a;
            }
        };
    }

}
