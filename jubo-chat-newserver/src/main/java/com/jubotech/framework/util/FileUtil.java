package com.jubotech.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

 
public class FileUtil {

	private final static String LOCAL_PATH = "c:/jl";
	//private final static String LOCAL_PATH = "/data/juliao/uploads";
	private final static String FILE_PATH = "/juliao";
	private final static String publicUrl="http://xxx.xx.com";

	 

	public static String getMd5ByFile(MultipartFile upload) throws Exception {
		byte[] uploadBytes = upload.getBytes();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(uploadBytes);
		String hashString = new BigInteger(1, digest).toString(16);
		// 修改统一方法，在MD5前加上时间戳
		return hashString.toUpperCase();
	}

	public static void copy(MultipartFile fileSrc, File fileDest) throws Exception {
		File fileDestParent = fileDest.getParentFile();
		if (fileDestParent != null && (!fileDestParent.exists())) {
			fileDestParent.mkdirs();
		}
		fileSrc.transferTo(fileDest);
	}

	/**
	 * 计算文件的MD5
	 */
	public static String getMD5(File file) throws Exception {
		return DigestUtils.md5Hex(new FileInputStream(file));
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 *
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful.<br>
	 *         If a deletion fails, the method stops attempting to delete and
	 *         returns "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			if (null != children) {
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteDir(new File(dir, children[i]));
					if (!success) {
						return false;
					}
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 根据传入的url和路径，并保存新文件，最后返回下载链接
	 *
	 * @param file
	 *            文件
	 * @return 最终路径
	 * 
	 */
	public static Map<String,Object> saveFile(MultipartFile file) throws Exception {
		 
		// 获取文件MD5
		String md5 = FileUtil.getMd5ByFile(file);
		// 这里频繁使用md5做文件目录不好，每次上传都是一个新的md5字符串，在没有批量上传的情况下，一个文件夹就只有一张图片
		// 改成：使用日期做文件目录，一天一个，方便管理
		String datePath = DateUtil.convertDate2String(new Date(), DateUtil.DATE_FORMAT_5);
		String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

		String newPath = LOCAL_PATH + FILE_PATH + File.separator + datePath + File.separator + md5 + "." + suffix;

		File fileTemp = new File(newPath);
		
		// 将文件流保存至指定路径
		FileUtils.copyInputStreamToFile(file.getInputStream(), fileTemp);
		
		String url = publicUrl + FILE_PATH + "/" + datePath + "/" + md5 + "." + suffix;
		
		Long  fileSize  = fileTemp.length();
		
		Map<String, Object> data  = new HashMap<String, Object>();
		
		data.put("url", url);
		
		data.put("fileSize", fileSize);
		
       return data;
	}
	
	 

}
