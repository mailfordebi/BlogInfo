package com.dd.blog.resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class BlogInfoUtil {

	public byte[] imageResize(byte[] imageContent, String imageName) throws IOException {
		//int scaledWidth = 1024;
		//int scaledHeight = 768;
		
		int scaledWidth = 300;
		int scaledHeight = 168;

		InputStream in = new ByteArrayInputStream(imageContent);
		BufferedImage inputImage = ImageIO.read(in);

		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		String formatName = imageName.substring(imageName.lastIndexOf(".") + 1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(outputImage, formatName, baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
}
