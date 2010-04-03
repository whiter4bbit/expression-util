package info.whiter4bbit.expression.utils;

public class TheTest {
	
	static class A{
		public String name = "masha";
		public String getName() {
			return name;
		}
	}
	
	static class B extends A{
		public String name = "pasha";
	}
	
	public static void main(String[] args) {
		System.out.println(new B().getName());
	}
}
