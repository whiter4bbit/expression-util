package info.whiter4bbit.common.util;

import info.whiter4bbit.common.util.lazy.F;

import java.util.*;

/**
 * CollectionUtils
 * Date: 05.03.2010
 */
public class CollectionUtils {
	
    public static<A> Set<A> set(A...a){
        Set<A> set = new HashSet<A>();
        Collections.addAll(set, a);
        return set;
    }

    public static<A,B> List<B> mapList(Collection<A> list, F<A,B> f){
        List<B> map = new ArrayList<B>();
        for(A a : list){
            map.add(f.f(a));
        }
        return map;
    }

    public static<A,B> Set<B> mapSet(Collection<A> list, F<A,B> f){
        Set<B> map = new HashSet<B>();
        for(A a : list){
            map.add(f.f(a));
        }
        return map;
    }
    
    public static<A,B> Map<A,B> singletonMap(A a, B b){
    	Map<A,B> map = new HashMap<A,B>();
    	map.put(a, b);
    	return map;
    }

}
