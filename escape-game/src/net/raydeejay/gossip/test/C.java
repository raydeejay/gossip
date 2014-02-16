package net.raydeejay.gossip.test;

public class C {
	private C c1, c2;
	public C() {}
	public C(C c1, C c2) {this.c1 = c1; this.c2 = c2;}
	public int foo() {return 5;}

	public C getC1() {return c1;}
	public C getC2() {return c2;}
}
