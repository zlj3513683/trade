package com.posppay.newpay.modules.sdk.cup.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Slf4j
public class HttpUtils {
    private static final String CHARSET = "UTF-8";
    private static PoolingHttpClientConnectionManager poolConnManager = null;


    /**
     * 设置信任自定义的证书
     *
     * @param keyStorePath 密钥库路径
     * @param keyStorepass 密钥库密码
     * @return
     */
    public static SSLContext custom(String keyStorePath, String keyStorepass) {
        SSLContext sc = null;
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
//
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//		} catch (KeyStoreException | NoSuchAlgorithmException| CertificateException | IOException | KeyManagementException e) {
//			e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
            }
        }
        return sc;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 设置代理
     *
     * @param hostOrIP
     * @param port
     */
    public static HttpClientBuilder proxy(String hostOrIP, int port) {
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost(hostOrIP, port, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return HttpClients.custom().setRoutePlanner(routePlanner);
    }

    /**
     * 模拟请求
     *
     * @param url 资源地址
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String send(String url, String xml) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";

        //绕过证书验证，处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        //创建自定义的httpclient对象
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(45000)
                .setConnectTimeout(15000)
                .setStaleConnectionCheckEnabled(true)
                .build();
//        CloseableHttpClient client = proxy("127.0.0.1", 6688)
//                .setConnectionManager(connManager)
//                .setDefaultRequestConfig(defaultRequestConfig).build();
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0,false))
                .build();
        //创建post方式请求对象

        HttpPost httpPost = new HttpPost(url);
        //设置参数到请求对象中
        httpPost.setEntity(new StringEntity(xml, CHARSET));

//        System.out.println("请求地址：" + url);
//        System.out.println("请求参数：" + xml);

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", (new StringBuilder()).append("text/xml; charset=").append(CHARSET).toString());
        httpPost.setHeader("Accept", new StringBuffer("text/xml;charset=").append(CHARSET).toString());
        httpPost.setHeader("Cache-Control", "no-cache");
//		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, CHARSET);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

//	public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, HttpProcessException {
//		String url = "https://www.facebook.com/";
//		String body = send(url, null, "utf-8");
//		System.out.println("交易响应结果");
//		System.out.println(body);
//	}
//
}
