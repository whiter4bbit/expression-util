package info.whiter4bbit.util.expression.utils;

import info.whiter4bbit.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * Function
 * Date: 29.03.2010
 */
public abstract class EvaluationFunction {

    public abstract Object handle(List<? extends Object> parameters);

    @SuppressWarnings("unchecked")
    public Collection<? extends Class<?>> acceptedTypes(int num){
        return CollectionUtils.set(Double.class, Long.class);
    }
}
