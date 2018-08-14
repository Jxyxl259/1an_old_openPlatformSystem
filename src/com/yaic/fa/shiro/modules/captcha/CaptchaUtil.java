package com.yaic.fa.shiro.modules.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.yaic.servicelayer.random.RandomGenerator;
import com.yaic.servicelayer.util.RandomUtil;


/**
 * 验证码工具类
 */
class CaptchaUtil
{
	private static final String FONT_NAME = "Fixedsys";
	private static final int FONT_SIZE = 18;

	private final int width = 80;// 图片宽
	private final int height = 25;// 图片高
	private final int lineNum = 50;// 干扰线数量
	private final int strNum = 4;// 随机产生字符数量

	/**
	 * 生成随机图片
	 *
	 * @param randomCode
	 * @return BufferedImage
	 */
	public BufferedImage genRandomCodeImage(final StringBuffer randomCode)
	{
		// BufferedImage类是具有缓冲区的Image类
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 获取Graphics对象,便于对图像进行各种绘制操作
		final Graphics g = image.getGraphics();
		// 设置背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设置干扰线的颜色
		g.setColor(getRandColor(110, 120));

		// 绘制干扰线
		for (int i = 0; i <= lineNum; i++)
		{
			drowLine(g);
		}
		// 绘制随机字符
		g.setFont(new Font(FONT_NAME, Font.ROMAN_BASELINE, FONT_SIZE));
		for (int i = 1; i <= strNum; i++)
		{
			randomCode.append(drowString(g, i));
		}
		g.dispose();
		return image;
	}

	/**
	 * 给定范围获得随机颜色
	 */
	@Deprecated
	private Color getRandColor(int fc, int bc)
	{
		if (fc > 255)
		{
			fc = 255;
		}
		if (bc > 255)
		{
			bc = 255;
		}
		final int r = fc + RandomUtil.nextInt(bc - fc);
		final int g = fc + RandomUtil.nextInt(bc - fc);
		final int b = fc + RandomUtil.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 绘制字符串
	 */
	@Deprecated
	private String drowString(final Graphics g, final int i)
	{
		g.setColor(new Color(RandomUtil.nextInt(101), RandomUtil.nextInt(111), RandomUtil.nextInt(121)));
		final String rand = RandomGenerator.getRandomChar();
		g.translate(RandomUtil.nextInt(3), RandomUtil.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return rand;
	}

	/**
	 * 绘制干扰线
	 */
	@Deprecated
	private void drowLine(final Graphics g)
	{
		final int x = RandomUtil.nextInt(width);
		final int y = RandomUtil.nextInt(height);
		final int x0 = RandomUtil.nextInt(16);
		final int y0 = RandomUtil.nextInt(16);
		g.drawLine(x, y, x + x0, y + y0);
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		final CaptchaUtil tool = new CaptchaUtil();
		final StringBuffer code = new StringBuffer();
		final BufferedImage image = tool.genRandomCodeImage(code);
		System.out.println(">>> random code =: " + code);
		try
		{
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", new FileOutputStream(new File("random-code.jpg")));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}
