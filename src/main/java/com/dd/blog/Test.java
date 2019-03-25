package com.dd.blog;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("user.dir"));
		
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);
		
		System.out.println(Test.class.getResource("/Test.JPG"));
		
		File file = new File("resources/images");
		String absolutePath = file.getAbsolutePath();
		System.out.println(new File("resources/images").toURI().getPath());

	}

}
