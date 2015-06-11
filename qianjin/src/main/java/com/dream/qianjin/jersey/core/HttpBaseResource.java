package com.dream.qianjin.jersey.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;

import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.constant.HttpConstant;
import com.dream.qianjin.constant.ServerConstant;

public abstract class HttpBaseResource {

	public static final Logger LOGGER = Logger.getLogger(HttpBaseResource.class
			.getName());

	/**
	 * 处理请求的数据
	 */
	public boolean executeRequestData(HttpServletRequest request,String subPath) {
		String type = request.getContentType();
		System.out.println("请求TYPE为："+type);
		if(type.indexOf(HttpConstant.HTTP_CONTENT_TYPE_DATA)!=-1){
			exeData(request,subPath);
			return true;
		}else if(type.indexOf(HttpConstant.HTTP_CONTENT_TYPE_JSON)!=-1){
			exeJson(request);
			return true;
		}
		return false;
	}

	private void exeData(HttpServletRequest request,String subPath) {
		// 获得磁盘文件条目工厂。
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件上传需要保存的路径，upload文件夹需存在。
		String path = request.getSession().getServletContext()
				.getRealPath(ServerConstant.PATH_ROOT + subPath);
		// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
		factory.setRepository(new File(path));
		// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
		factory.setSizeThreshold(1024 * 1024 * 10);
		// 上传处理工具类（高水平API上传处理？）
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		OutputStream out = null;
		InputStream in = null;
		try {
			// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
//			System.out.println("请求过来的URL格式为："+request.getCharacterEncoding());
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			//生成公司的UUID,可能是更新操作
			String titleID = null; 
			if(ServerConstant.PATH_USER.equals(subPath)){
				titleID = getValueID(list,Constant.JSON_ID);
			}else if (ServerConstant.PATH_COMPANY.equals(subPath)){
				titleID = getValueID(list,Constant.JSON_COMPANY_ID);
			}else{
				return ;
			}
			request.setAttribute(Constant.JSON_COMPANY_ID, titleID );
			for (FileItem item : list) {
				// 获取表单属性名字。
				String name = item.getFieldName().toLowerCase();
				// 如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if (item.isFormField()) {
					// 获取用户具体输入的字符串，
					byte[] valueByte = item.get();
					String valueStr = new String(valueByte,"utf-8");
					request.setAttribute(name, valueStr);
				}
				// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else {
					
					// 获取路径名
					String value = item.getName();
					// 取到最后一个反斜杠。
					int start = value.lastIndexOf("\\");
					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
					String filename = value.substring(start + 1);
					filename = titleID + "_" +filename;

					/*
					 * 第三方提供的方法直接写到文件中。 item.write(new File(path,filename));
					 */
					// 收到写到接收的文件中。
					File fileDir = new File(path);
					File files = new File(path, filename);
					
					if(files.exists()){
						files.delete();
					}
					String pathTemp = files.getPath();
					System.out.println(name+"的保存路径为："+files.getPath());
					pathTemp = pathTemp.replace("\\", "\\\\");
					System.out.println(name+"的保存路径（替换后）为："+pathTemp);
					request.setAttribute(name, pathTemp);
					
					if (!fileDir.exists()) {
						fileDir.mkdirs();
						files.createNewFile();
					} else {
						files.createNewFile();
					}
					out = new FileOutputStream(files);
					in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("获取文件总量的容量:" + item.getSize());

					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getValueID(List<FileItem> list,String name) throws UnsupportedEncodingException {
		for (int i = 0; i < list.size(); i++) {
			FileItem obj = list.get(i);
			if(obj.isFormField()){
				if(name.equalsIgnoreCase(obj.getName())){
					byte[] valueByte = obj.get();
					String valueStr = new String(valueByte,"utf-8");
					return valueStr;
				}
			}
		}
		return UUID.randomUUID().toString().replace("-", "");
	}

	private Map<String, String> exeJson(HttpServletRequest request) {
		// Map<String, String> dataMap = new HashMap<String, String>();
		/* 读取数据 */
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer("");
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (sb != null && sb.length() < 2048) {
			LOGGER.info("通过字符流接收请求的JSON为: " + sb.toString());
		} else {
			LOGGER.info("通过字符流接收请求的JSON(带图片的)为: " + sb.substring(0, 2048));
		}

		if (!StringUtils.isEmpty(sb)) {
			String[] sbSpl = sb.toString().split("&");
			for (int i = 0; i < sbSpl.length; i++) {
				String[] param = sbSpl[i].split("=");
				// dataMap.put(param[0], param[1]);
				request.setAttribute(param[0], param[1]);
			}
		}
		return null;
	}

}
