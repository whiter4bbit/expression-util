package info.whiter4bbit.expression.utils;

import info.whiter4bbit.common.util.CollectionUtils;
import info.whiter4bbit.common.util.lazy.LazyValue;

import java.util.Collection;
import java.util.List;

/**
 * Function
 * Date: 29.03.2010
 */
public abstract class EvaluationFunction {

    public abstract Object handle(List<? extends Object> parameters);
    
    public boolean lazyArguments(){
    	return false;
    }
    
    private List<LazyValue<? extends Object>> lazyValues;
    
    public List<LazyValue<? extends Object>> lazyValues() {
		return lazyValues;
	}
    
    public void setLazyValues(List<LazyValue<? extends Object>> lazyValues) {
		this.lazyValues = lazyValues;
	}

    @SuppressWarnings("unchecked")
    public Collection<? extends Class<?>> acceptedTypes(int num){
        return CollectionUtils.set(Double.class, Long.class);
    }
}
