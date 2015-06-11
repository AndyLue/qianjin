package test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dream.qianjin.bean.User;
import com.dream.qianjin.core.service.IBaseService;
import com.dream.qianjin.easemob.EasemobUserUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JunitTest {

//	@Test
	public void test() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","spring-service.xml");
		IBaseService<User> userDao = (IBaseService<User>)ctx.getBean("userService");
		
		System.out.println("402880474d47958c014d47958d8c0001".length());
		
		User user = new User();
//		user.setPassword("222dd");
		user.setName("fsdfsdf");
		
		userDao.save(user);
	}
	
//	@Test
	public void test1() throws Exception{
		User user = new User();
		
		doMyMethod(user,"com.dream.qianjin.bean.User");
		
	}
	private void doMyMethod(User user,String className) throws ClassNotFoundException {
		Class<? extends User> ss = user.getClass();
		Field[] fileds = ss.getDeclaredFields();
		
		Class<?> demo = Class.forName(className);
		Field[] xx = demo.getDeclaredFields();
		for (int i = 0; i < fileds.length; i++) {
			Field filed = fileds[i];
			
			setter(user, filed.getName(), "name", String.class);
		}
	}
	
	public static void setter(Object obj, String att, Object value,
            Class<?> type) {

        try {

            Method method = obj.getClass().getMethod("set" + att, type);

            method.invoke(obj, value);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
//	@Test
	public void test22(){
		uploadFile(new File("C:\\Users\\Andy_Liu\\Downloads\\new_zxzq_v6\\DllRes\\barcode.bmp"));
	}
	
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";
	public static String uploadFile(File file) {
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		String RequestURL = "http://192.168.0.112:8888/qianjin/register2?id=1111";
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			if (file != null) {
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				OutputStream outputSteam = conn.getOutputStream();

				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的 比如:abc.png
				 */

				sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
						+ file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				if (res == 200) {
					return SUCCESS;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
//	@Test
	public void test4(){
		ObjectNode creObj = EasemobUserUtil.createUser("lw", "123");
		System.out.println(creObj);
		ObjectNode obj = EasemobUserUtil.Login("lw", "123");
		System.out.println(obj);
	}
//	@Test
	public void test3(){
		JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
		ObjectNode node = jsonFactory.objectNode();
		ObjectNode node1 = jsonFactory.objectNode();
		ObjectNode node2 = jsonFactory.objectNode();
		node.put("id", 100);
		node1.put("id", 1);
		node1.put("name", "liudehua");
		node2.put("i", 2);
		node2.put("name", "zhangxueyou");
		ArrayNode arryNode = node.arrayNode();
		arryNode.add("ffff");
		arryNode.add(node1);
		arryNode.add(node2);
		node.put("entities", arryNode);
		System.out.println(node.toString());
		
		ObjectNode node4 = jsonFactory.objectNode();
		ArrayNode arryNode1 = node4.arrayNode();
		arryNode1.add("111111");
		arryNode1.add("111112");
		arryNode1.add("111113");
		node4.put("verifycodes", arryNode1);
		System.out.println(node4.toString());
	}

}
