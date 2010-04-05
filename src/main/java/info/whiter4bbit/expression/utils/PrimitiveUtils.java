package info.whiter4bbit.expression.utils;

import info.whiter4bbit.common.util.DataTypes;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveUtils {

    public static Object getFromString(String val, DataTypes to){
        if(to==DataTypes.DOUBLE){
            return Double.parseDouble(val);
        }
        if(to==DataTypes.INTEGER){
            return Integer.parseInt(val);
        }
        if(to==DataTypes.LONG){
            return Long.parseLong(val);
        }
        return null;
    }

    public static Object genericNumbetCast(Object val, DataTypes to){
        return genericNumberCast(val, to.getTypeName());
    }

    @SuppressWarnings("unchecked")
    public static<A, C extends Class<A>> A genericNumberCast(Object val, C to){
        String name = "";
        if(Integer.class.equals(to)) name="int";
        if(Long.class.equals(to)) name="long";
        if(Double.class.equals(to)) name="double";
        if(Float.class.equals(to)) name="float";
        return (A)genericNumberCast(val, name);
    }

	public static Object genericNumberCast(Object val, String to){
		if(val==null){
			throw new IllegalArgumentException("Value can't be null");
		}
		if(to==null){
			throw new IllegalArgumentException("To argument can't be null");
		}
		if(to.equals("double")
				&& to.equals("int")
				&& to.equals("long")
				&& to.equals("float")){
			throw new IllegalArgumentException("Type not supported:"+to);
		}
		try{
			String methodName = to+"Value";
			Method method = val.getClass().getMethod(methodName);
			return method.invoke(val);
		}catch(Exception e){

		}
		return null;
	}

	private static interface BinaryOp{
		Object add(Object left, Object right);
		Object minus(Object left, Object right);
		Object mult(Object left, Object right);
		Object div(Object left, Object right);
        Object pow(Object left, Object right);
	}

	private static class LongBO implements BinaryOp {
		@Override
		public Object add(Object left, Object right) {
			return (Long)left+(Long)right;
		}
		@Override
		public Object div(Object left, Object right) {
			return (Long)left/(Long)right;
		}
		@Override
		public Object minus(Object left, Object right) {
			return (Long)left-(Long)right;
		}
		@Override
		public Object mult(Object left, Object right) {
			return (Long)left*(Long)right;
		}

        @Override
        public Object pow(Object left, Object right) {
            return ((Double)Math.pow((Long)left, (Long)right)).longValue();
        }
    }
	
	private static class IntegerBO implements BinaryOp {
		@Override
		public Object add(Object left, Object right) {
			return (Integer)left+(Integer)right;
		}
		@Override
		public Object div(Object left, Object right) {
			return (Integer)left/(Integer)right;
		}
		@Override
		public Object minus(Object left, Object right) {
			return (Integer)left-(Integer)right;
		}
		@Override
		public Object mult(Object left, Object right) {
			return (Integer)left*(Integer)right;
		}

        @Override
        public Object pow(Object left, Object right) {
            return ((Double)Math.pow((Integer)left, (Integer)right)).intValue();
        }
	}
	
	private static class FloatBO implements BinaryOp {
		@Override
		public Object add(Object left, Object right) {
			return (Float)left+(Float)right;
		}
		@Override
		public Object div(Object left, Object right) {
			return (Float)left/(Float)right;
		}
		@Override
		public Object minus(Object left, Object right) {
			return (Float)left-(Float)right;
		}
		@Override
		public Object mult(Object left, Object right) {
			return (Float)left*(Float)right;
		}
        @Override
        public Object pow(Object left, Object right) {
            return ((Double)Math.pow((Float)left, (Float)right)).floatValue();
        }

	}
	
	private static class DoubleBO implements BinaryOp {
		@Override
		public Object add(Object left, Object right) {
			return (Double)left+(Double)right;
		}
		@Override
		public Object div(Object left, Object right) {
			return (Double)left/(Double)right;
		}
		@Override
		public Object minus(Object left, Object right) {
			return (Double)left-(Double)right;
		}
		@Override
		public Object mult(Object left, Object right) {
			return (Double)left*(Double)right;
		}

        @Override
        public Object pow(Object left, Object right) {
            return Math.pow((Double)left, (Double)right);
        }
	}
	
	private static Map<String, BinaryOp> binOps = new HashMap<String, BinaryOp>();
	
	static {
		binOps.put("long", new LongBO());		
		binOps.put("double", new DoubleBO());
		binOps.put("float", new FloatBO());
		binOps.put("int", new IntegerBO());
	}

    public static Object genericNumberOp(String op, Object left, Object right, DataTypes target){
        return genericNumberOp(op, left, right, target.getTypeName());
    }

	public static Object genericNumberOp(String op, Object left, Object right, String target){
		Object castedLeft = genericNumberCast(left, target);
		Object castedRight = genericNumberCast(right, target);
		BinaryOp binOP = binOps.get(target);
		if(binOP==null){
			throw new IllegalArgumentException("Invalid target type:"+target);
		}
		if("+".equals(op)){
			return binOP.add(castedLeft, castedRight);
		}
		if("-".equals(op)){
			return binOP.minus(castedLeft, castedRight);
		}
		if("*".equals(op)){
			return binOP.mult(castedLeft, castedRight);
		}
		if("/".equals(op)){
			return binOP.div(castedLeft, castedRight);
		}
        if("^".equals(op)){
			return binOP.pow(castedLeft, castedRight);
        }
		throw new IllegalArgumentException("Wrong operation "+op);
	}

}
