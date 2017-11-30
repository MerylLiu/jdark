package com.iptv.core.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtil {

	public static void zoomImage(String sourcePath, String destPath, int w, int h) {
		double wr = 0, hr = 0;
		File srcFile = new File(sourcePath);
		File destFile = new File(destPath);

		try {
			BufferedImage bufImg = ImageIO.read(srcFile); // 读取图片
			Image Itemp = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);// 设置缩放目标图片模板

			wr = w * 1.0 / bufImg.getWidth(); // 获取缩放比例
			hr = h * 1.0 / bufImg.getHeight();

			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
			Itemp = ato.filter(bufImg, null);
			ImageIO.write((BufferedImage) Itemp, destPath.substring(destPath.lastIndexOf(".") + 1), destFile); // 写入缩减后的图片
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String inputPath = "/Users/mac/Downloads/logo256x256.png";
		String outputPath = "/Users/mac/Downloads/icons/";

		zoomImage(inputPath, outputPath + "icon29.png", 29, 29);
		zoomImage(inputPath, outputPath + "icon40.png", 40, 40);
		zoomImage(inputPath, outputPath + "icon50.png", 50, 50);
		zoomImage(inputPath, outputPath + "icon57.png", 57, 57);
		zoomImage(inputPath, outputPath + "icon58.png", 58, 58);
		zoomImage(inputPath, outputPath + "icon72.png", 72, 72);
		zoomImage(inputPath, outputPath + "icon76.png", 76, 76);
		zoomImage(inputPath, outputPath + "icon80.png", 80, 80);
		zoomImage(inputPath, outputPath + "icon87.png", 87, 87);
		zoomImage(inputPath, outputPath + "icon100.png", 100, 100);
		zoomImage(inputPath, outputPath + "icon114.png", 114, 114);
		zoomImage(inputPath, outputPath + "icon120.png", 120, 120);
		zoomImage(inputPath, outputPath + "icon144.png", 144, 144);
		zoomImage(inputPath, outputPath + "icon152.png", 152, 152);
		zoomImage(inputPath, outputPath + "icon180.png", 180, 180);
	}
}
