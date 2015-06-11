package test;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.NameValuePair;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.qianjin.constant.Constant;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * REST API Demo : 用户体系集成 Jersey2.9实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/userapi/
 * 
 * @author Lynch 2014-09-09
 * 
 */
public class TestFinal {

	private static Logger LOGGER = LoggerFactory.getLogger(TestFinal.class);

	private static JsonNodeFactory factory = new JsonNodeFactory(false);

	/** METHOD_DELETE value:GET */
	public static String METHOD_GET = "GET";

	/** METHOD_DELETE value:POST */
	public static String METHOD_POST = "POST";

	/** METHOD_DELETE value:PUT */
	public static String METHOD_PUT = "PUT";

	/** METHOD_DELETE value:DELETE */
	public static String METHOD_DELETE = "DELETE";

	public static void main(String[] args) {
		//测试验证码
		
//		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
//		datanode.put(Constant.JSON_VERIFYCODES, "00c49b0ba48843e9946a3f9636406950");
//		createNewIMUserSingle(datanode);
		// 测试LOGIN 和 regist1
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put(Constant.JSON_PASSWORD, "liufacai1");
		datanode.put(Constant.JSON_TELEPHONE, "9999");
		
		ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
		// 测试regist2
//		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
//		datanode.put(Constant.JSON_ID, "ff8080814dd21098014dd213e3d20001");
//		datanode.put(Constant.JSON_NAME, "3test");
//		datanode.put(Constant.JSON_COMPANY_NAME, "3天地公司");
//		datanode.put(Constant.JSON_COMPANY_ISSAMEPEOPLE, "0");
//		datanode.put(Constant.JSON_COMPANY_BUSINESSLICENSE, "不为我要在地xcv".getBytes());
//		datanode.put(Constant.JSON_COMPANY_IDCARD, "test".getBytes());
//		datanode.put(Constant.JSON_COMPANY_POSITIONPROVE, "在是是中国".getBytes());
//		createNewIMUserSingle(datanode);
	}

	/**
	 * 注册IM用户[单个]
	 * 
	 * 给指定AppKey创建一个新的用户
	 * 
	 * @param dataNode
	 * @return
	 */
	public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

		ObjectNode objectNode = factory.objectNode();

		try {
			JerseyClient CLIENT = getJerseyClient(true);
			JerseyWebTarget webTarget = CLIENT.target(Constant.QIANJIN_URL)
					.path("/register1");
//			webTarget = webTarget.queryParam(Constant.JSON_TELEPHONE, "77777");
//			webTarget = webTarget.queryParam(Constant.JSON_PASSWORD, "liufacai1");
			
//			JerseyWebTarget webTarget = CLIENT.target(Constant.QIANJIN_URL)
//					.path("/register2");
//			webTarget = webTarget.queryParam(Constant.JSON_ID, "402880f04dd638bb014dd638dde00001");
//			webTarget = webTarget.queryParam(Constant.JSON_NAME, "1test");
//			webTarget = webTarget.queryParam(Constant.JSON_COMPANY_NAME, "天地公司");
//			webTarget = webTarget.queryParam(Constant.JSON_COMPANY_ISSAMEPEOPLE, "0");
//			webTarget = webTarget.queryParam(Constant.JSON_COMPANY_BUSINESSLICENSE, "不为我要在地xcv".getBytes());
//			webTarget = webTarget.queryParam(Constant.JSON_COMPANY_IDCARD, "test".getBytes());
//			webTarget = webTarget.queryParam(Constant.JSON_COMPANY_POSITIONPROVE, "在是是中国".getBytes());
//
			objectNode = sendRequest(webTarget, dataNode, METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * Send HTTPS request with Jersey
	 * 
	 * @return
	 */
	public static ObjectNode sendRequest(JerseyWebTarget jerseyWebTarget,
			Object body, String method, List<NameValuePair> headers)
			throws RuntimeException {

		ObjectNode objectNode = null;
		try {

			Invocation.Builder inBuilder = jerseyWebTarget.request();
			// if (credentail != null) {
			// //inBuilder.header("Authorization",
			// "Bearer YWMtXTzzLkX_EeSRRA0PhthlrwAAAUnqX7TBUDddVXrfAPHQyGJzZRyRKzGtw8E");
			// Token.applyAuthentication(inBuilder, credentail);
			// }

			Response response = null;
			if (METHOD_GET.equals(method)) {

				response = inBuilder.get(Response.class);

			} else if (METHOD_POST.equals(method)) {
				response = inBuilder.post(
						Entity.entity(body, MediaType.APPLICATION_JSON),
						Response.class);

			} else if (METHOD_PUT.equals(method)) {

				response = inBuilder.put(
						Entity.entity(body, MediaType.APPLICATION_JSON),
						Response.class);

			} else if (METHOD_DELETE.equals(method)) {

				response = inBuilder.delete(Response.class);

			}

			objectNode = response.readEntity(ObjectNode.class);
			objectNode.put("statusCode", response.getStatus());
			System.out.println(response.getStatus() + ":"
					+ objectNode.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	public static JerseyClient getJerseyClient(boolean isSSL) {
		ClientBuilder clientBuilder = JerseyClientBuilder.newBuilder()
				.register(MultiPartFeature.class);

		// Create a secure JerseyClient
		if (isSSL) {
			try {
				HostnameVerifier verifier = new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};

				TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkServerTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}

					public void checkClientTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}
				} };

				SSLContext sslContext = sslContext = SSLContext
						.getInstance("SSL");

				sslContext.init(null, tm, new SecureRandom());

				clientBuilder.sslContext(sslContext).hostnameVerifier(verifier);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
		}

		return (JerseyClient) clientBuilder.build().register(
				JacksonJsonProvider.class);
	}

}
