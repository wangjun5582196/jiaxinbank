/*
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期     修改人员  修改说明
 * ========    =======  ============================================
 * 2019/8/2  chenbw22470  新增
 * ========    =======  ============================================
 */
package com.ai.indeed.email;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * 功能说明:
 *
 * @author chenbw22470
 */
public class Md5CalculateUtil {

	/**
	 * 获取一个文件的md5值(可处理大文件)
	 *
	 * @return md5 value
	 */
	public static String getMD5(File file) {
		FileInputStream fileInputStream = null;
		try {
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (Exception e) {

			return null;
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {

			}
		}
	}

	/**
	 * 求一个字符串的md5值
	 *
	 * @param target 字符串
	 * @return md5 value
	 */
	public static String MD5(String target) {
		return DigestUtils.md5Hex(target);
	}

//	public static void main(String[] args) {
//		long beginTime = System.currentTimeMillis();
//		File file = new File("D:\\work\\数据接收对账\\Documents\\01产品说明\\现场对账单数据\\国信对账单\\110002397787.pdf");
//		String md5 = getMD5(file);
//		long endTime = System.currentTimeMillis();
//		System.out.println("MD5:" + md5 + "\n 耗时:" + ((endTime - beginTime) / 1000) + "s");
//	}
}
