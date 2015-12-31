package com.wwh;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class MainClass {

	/**
	 * @param args
	 */
	private final static int RED_MAX = 200;
	private final static int GREEN_MAX = 150;
	private static ArrayList<Ponit> arrayList = new ArrayList<Ponit>();
	private static int sumX = 0;
	private static int sumY = 0;
	private static ArrayList<Integer> topArrayListX = new ArrayList<Integer>();
	private static ArrayList<Integer> bottemArrayListX = new ArrayList<Integer>();
	private static ArrayList<Integer> topArrayListY = new ArrayList<Integer>();
	private static ArrayList<Integer> bottemArrayListY = new ArrayList<Integer>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedImage image = ImageIO.read(new File("demo3.jpg"));
			System.out.println("w=" + image.getWidth() + "h="
					+ image.getHeight());
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					Color color = ParseColor(image.getRGB(i, j));
					if (color.getRed() > RED_MAX
							&& color.getGreen() < GREEN_MAX) {
						System.out.println(i + "," + j);
						Ponit point = new Ponit(i, j);
						arrayList.add(point);
						sumX = sumX + i;
						sumY = sumY + j;
					}
				}
			}
			int centerX = sumX / arrayList.size();
			int centerY = sumY / arrayList.size();
			for (Ponit point : arrayList) {
				if (point.i > centerX) {
					topArrayListX.add(point.i);
					topArrayListY.add(point.j);

				} else {
					bottemArrayListX.add(point.i);
					bottemArrayListY.add(point.j);
				}
			}
			int topCenterx = getCenter(topArrayListX);
			int topCentery = getCenter(topArrayListY);
			int bottemCenterx = getCenter(bottemArrayListX);
			int bottemCentery = getCenter(bottemArrayListY);
			System.out.print("topCenterx=" + topCenterx + ",topCentery="
					+ topCentery + ",bottemCenterx=" + bottemCenterx
					+ ",bottemCentery=" + bottemCentery);
			double sum = 0;
			for (Ponit point : arrayList) {

				double temp = pointToLine(topCenterx, topCentery,
						bottemCenterx, bottemCentery, point.i, point.j);
				// System.out.println("temp=" + temp);
				sum = sum + temp;
			}
			System.out.println("temp=" + sum / arrayList.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Color ParseColor(int color) {
		int blue = color & 0x000000ff;
		int green = (color & 0x0000ff00) >> 8;
		int red = (color & 0x00ff0000) >> 16;
		return new Color(red, green, blue);
	}

	public static int getCenter(ArrayList<Integer> arr) {
		Collections.sort(arr);
		int length = arr.size();
		Integer j = arr.get(length / 2);
		if (length % 2 == 0) {
			j = (arr.get(length / 2) + arr.get(length / 2 + 1)) / 2;
			return j;
		} else {
			j = arr.get(length / 2);
			return j;
		}
	}

	// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )

	private static double pointToLine(int x1, int y1, int x2, int y2, int x0,

	int y0) {

		double space = 0;

		double a, b, c;

		a = lineSpace(x1, y1, x2, y2);// 线段的长度

		b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离

		c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离

		if (c <= 0.000001 || b <= 0.000001) {

			space = 0;

			return space;

		}

		if (a <= 0.000001) {

			space = b;

			return space;

		}

		if (c * c >= a * a + b * b) {

			space = b;

			return space;

		}

		if (b * b >= a * a + c * c) {

			space = c;

			return space;

		}

		double p = (a + b + c) / 2;// 半周长

		double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积

		space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）

		return space;

	}

	// 计算两点之间的距离

	private static double lineSpace(int x1, int y1, int x2, int y2) {

		double lineLength = 0;

		lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)

		* (y1 - y2));

		return lineLength;

	}
}
