package com.dd.blog;

import java.io.FileNotFoundException;

public class Test {

	/*
	 * public static void main(String[] args) { String text =
	 * "0123hello9012hello8901hello7890"; String match = "hello"; int index =
	 * text.indexOf(match); int matchLength = match.length(); while (index >= 0) {
	 * // indexOf returns -1 if no match found System.out.println(index); index =
	 * text.indexOf(match, index + matchLength); } }
	 */

	public void test() throws ArithmeticException {
		int c = 1 + 2;
		System.out.println("ArithmeticException Hello");
	}

	public void test1() throws FileNotFoundException {
		int c = 1 + 2;
		System.out.println("FileNotFoundException Hello");
	}
	
	public static void main(String[] args) {
		Test t=new Test();
		t.test();
		//t.test1();
	}
}
