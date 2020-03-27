package com.posppay.newpay.modules.sdk.starpos.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class HttpConnectionPoolUtil {

    private static final String CHARSET = "UTF-8";

    private static final int CONNECT_TIMEOUT = 15000;// 设置连接建立的超时时间为10s
    private static final int SOCKET_TIMEOUT = 45000;
    private static final int MAX_CONN = 1000; // 最大连接数
    private static final int Max_PRE_ROUTE = 10;
    private static final int MAX_ROUTE = 10;
    private static CloseableHttpClient httpClient; // 发送请求的客户端单例
    private static PoolingHttpClientConnectionManager manager; //连接池管理类
    private static ScheduledExecutorService monitorExecutor;

    private final static Object syncLock = new Object(); // 相当于线程锁,用于线程安全

    /**
     * 对http请求进行基本设置
     * @param httpRequestBase http请求
     */
    private static void setRequestConfig(HttpRequestBase httpRequestBase){
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        httpRequestBase.setConfig(requestConfig);
    }

    public static CloseableHttpClient getHttpClient(String url){
        String hostName = url.split("/")[2];
//        System.out.println(hostName);
        int port = 80;
        if (hostName.contains(":")){
            String[] args = hostName.split(":");
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        }

        if (httpClient == null){
            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (syncLock){
                if (httpClient == null){
                    httpClient = createHttpClient(hostName, port);
                    //开启监控线程,对异常和空闲线程进行关闭
//                    monitorExecutor = Executors.newScheduledThreadPool(1);
//                    int i=0;
//                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
//                        @Override
//                        public void run() {
//                            //关闭异常连接
//                            manager.closeExpiredConnections();
//                            //关闭5s空闲的连接
//                            manager.closeIdleConnections(5000, TimeUnit.MILLISECONDS);
//
////                            log.info("close expired and idle for over 5s connection");
//                        }
//                    }, 5000L, 5000L, TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
    }

    /**
     * 根据host和port构建httpclient实例
     * @param host 要访问的域名
     * @param port 要访问的端口
     * @return
     */
    public static CloseableHttpClient createHttpClient(String host, int port){
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();

        manager = new PoolingHttpClientConnectionManager(registry);
        //设置连接参数
        manager.setMaxTotal(MAX_CONN); // 最大连接数
        manager.setDefaultMaxPerRoute(Max_PRE_ROUTE); // 路由最大连接数

        HttpHost httpHost = new HttpHost(host, port);
        manager.setMaxPerRoute(new HttpRoute(httpHost), MAX_ROUTE);
//        HttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(0,false);
        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 0){
                    //重试超过3次,放弃请求
                    log.error("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException){
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    log.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException){
                    // SSL握手异常
                    log.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException){
                    //超时
                    log.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException){
                    // 服务器不可达
                    log.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException){
                    // 连接超时
                    log.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException){
                    log.error("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)){
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).build();
        return client;
    }

    /**
     * 设置post请求的参数
     * @param httpPost
     * @param params
     */
    private static void setPostParams(HttpPost httpPost, Map<String, String> params){
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for (String key: keys){
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String send(String url, Map<String,String> formdata) throws  IOException {
        String body = "";
        HttpPost httpPost = new HttpPost(url);
        //设置参数到请求对象中
        httpClient = getHttpClient(url);
        httpPost.setHeader("Content-type", (new StringBuilder()).append("application/x-www-form-urlencoded; charset=").append(CHARSET).toString());
        httpPost.setHeader("Accept", new StringBuffer("text/xml;charset=").append(CHARSET).toString());
        httpPost.setHeader("Cache-Control", "no-cache");

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        //请求参数
        for (String key : formdata.keySet()) {
            params.add(new BasicNameValuePair(key, formdata.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, CHARSET));

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, CHARSET);
                //释放连接
                EntityUtils.consume(entity);
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取结果实体
            //释放链接
            return body;
        }

    }

    private static void closeConnectionPool() {
        try {
            httpClient.close();
//            manager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
