package com.fuhuitong.nitri.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * To change this template use FileImg | Settings | FileImg Templates.
 */
public class HttpClientPoolUtil {

    public static PoolingHttpClientConnectionManager cm = null;

    public static CloseableHttpClient httpClient = null;

    /**
     * 默认content 类型 application/x-www-form-urlencoded;text/html,charset=utf-8
     */
    //private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded;text/html,charset=utf-8";

    /**
     * 默认请求超时时间30s
     */
    private static final int DEFAUL_TTIME_OUT = 15000; //Integer.parseInt(PropertiesHander.getPropertiesValue("Http_Default_Timeout"));

    private static final int count = 32; //(PropertiesHander.getPropertiesValue("defaultMaxPerRoute"));

    private static final int totalCount = 1000; //Integer.parseInt(PropertiesHander.getPropertiesValue("maxTotal"));

    private static final int Http_Default_Keep_Time = 15000;  // Integer.parseInt(PropertiesHander.getPropertiesValue("Http_Default_Keep_Time"));

    /**
     * 初始化连接池
     */
    public static synchronized void initPools() {
        if (httpClient == null) {
            //设置代理IP、端口、协议（请分别替换）
            //  HttpHost proxy = new HttpHost("127.0.0.1", 5555, "http"); .setProxy(proxy)
            cm = new PoolingHttpClientConnectionManager();
            cm.setDefaultMaxPerRoute(count);
            cm.setMaxTotal(totalCount);
            httpClient = HttpClients.custom().setKeepAliveStrategy(defaultStrategy).setConnectionManager(cm).build();
        }
    }

    /**
     * Http connection keepAlive 设置
     */
    public static ConnectionKeepAliveStrategy defaultStrategy = new ConnectionKeepAliveStrategy() {
        @Override
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            int keepTime = Http_Default_Keep_Time;
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (Exception e) {
                        e.printStackTrace();
                        //  logger.error("format KeepAlive timeout exception, exception:" + e.toString());
                    }
                }
            }
            return keepTime * 1000;
        }
    };

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static PoolingHttpClientConnectionManager getHttpConnectionManager() {
        return cm;
    }

    /**
     * 执行http post请求 默认采用Content-Type：application/json，Accept：application/json
     *
     * @param uri  请求地址
     * @param data 请求数据
     * @return
     */
    public static String execute(String uri, String data, Map<String, String> mapHeader) {
        long startTime = System.currentTimeMillis();
        HttpEntity httpEntity = null;
        HttpEntityEnclosingRequestBase method = null;
        String responseBody = "";
        try {
            if (httpClient == null) {
                initPools();
            }
            method = (HttpEntityEnclosingRequestBase) getRequest(uri, HttpPost.METHOD_NAME, DEFAULT_CONTENT_TYPE, 0);
            method.setEntity(new StringEntity(data));
            if (mapHeader != null) {
                for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
                    method.addHeader(entry.getKey(), entry.getValue());
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
            }
            HttpContext context = HttpClientContext.create();
            CloseableHttpResponse httpResponse = httpClient.execute(method, context);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity, "UTF-8");
            }

        } catch (Exception e) {
            if (method != null) {
                method.abort();
            }
            e.printStackTrace();
            System.out.println(
                    "execute post request exception, url:" + uri + ", exception:" + e.toString() + ", cost time(ms):"
                            + (System.currentTimeMillis() - startTime));
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consumeQuietly(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(
                            "err close response exception, url:" + uri + ", exception:" + e.toString() + ", cost time(ms):"
                                    + (System.currentTimeMillis() - startTime));
                }
            }
        }
        return responseBody;
    }


    public static String execute(String uri, String data) {

        long startTime = System.currentTimeMillis();
        HttpEntity httpEntity = null;
        HttpEntityEnclosingRequestBase method = null;
        String responseBody = "";
        try {
            if (httpClient == null) {
                initPools();
            }
            method = (HttpEntityEnclosingRequestBase) getRequest(uri, HttpPost.METHOD_NAME, DEFAULT_CONTENT_TYPE, 0);
            method.setEntity(new StringEntity(data));

            HttpContext context = HttpClientContext.create();
            CloseableHttpResponse httpResponse = httpClient.execute(method, context);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity, "UTF-8");
            }

        } catch (Exception e) {
            if (method != null) {
                method.abort();
            }
            e.printStackTrace();
            System.out.println(
                    "execute post request exception, url:" + uri + ", exception:" + e.toString() + ", cost time(ms):"
                            + (System.currentTimeMillis() - startTime));
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consumeQuietly(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(
                            "err close response exception, url:" + uri + ", exception:" + e.toString() + ", cost time(ms):"
                                    + (System.currentTimeMillis() - startTime));
                }
            }
        }
        return responseBody;
    }

    /**
     * 创建请求
     *
     * @param uri         请求url
     * @param methodName  请求的方法类型
     * @param contentType contentType类型
     * @param timeout     超时时间
     * @return
     */
    public static HttpRequestBase getRequest(String uri, String methodName, String contentType, int timeout) {
        if (httpClient == null) {
            initPools();
        }
        HttpRequestBase method = null;
        if (timeout <= 0) {
            timeout = DEFAUL_TTIME_OUT;
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout * 1000).setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000).setExpectContinueEnabled(false).build();

        if (HttpPut.METHOD_NAME.equalsIgnoreCase(methodName)) {
            method = new HttpPut(uri);
        } else if (HttpPost.METHOD_NAME.equalsIgnoreCase(methodName)) {
            method = new HttpPost(uri);
        } else if (HttpGet.METHOD_NAME.equalsIgnoreCase(methodName)) {
            method = new HttpGet(uri);
        } else {
            method = new HttpPost(uri);
        }
        if (StringUtils.isBlank(contentType)) {
            contentType = DEFAULT_CONTENT_TYPE;
        }
        method.addHeader("Content-Type", contentType);
        // method.addHeader("Accept", contentType);
        method.setConfig(requestConfig);
        return method;
    }

    /**
     * 执行GET 请求
     *
     * @param uri
     * @return
     */
    public static String execute(String uri) {
        long startTime = System.currentTimeMillis();
        HttpEntity httpEntity = null;
        HttpRequestBase method = null;
        String responseBody = "";
        try {
            if (httpClient == null) {
                initPools();
            }
            method = getRequest(uri, HttpGet.METHOD_NAME, DEFAULT_CONTENT_TYPE, 0);
            HttpContext context = HttpClientContext.create();
            CloseableHttpResponse httpResponse = httpClient.execute(method, context);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println("请求URL: " + uri + "————>返回状态码：" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            if (method != null) {
                method.abort();
            }
            e.printStackTrace();
            System.out.println("err execute get request exception, url:" + uri + ", exception:" + e.toString() + ",cost time(ms):"
                    + (System.currentTimeMillis() - startTime));
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consumeQuietly(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("err close response exception, url:" + uri + ", exception:" + e.toString() + ",cost time(ms):"
                            + (System.currentTimeMillis() - startTime));
                }
            }
        }
        return responseBody;
    }


    /**
     * 执行GET 请求
     *
     * @param uri
     * @return
     */
    public static String execute(String uri, Map<String, String> mapHeader) {
        System.out.println("uri-->" + uri);
        long startTime = System.currentTimeMillis();
        HttpEntity httpEntity = null;
        HttpRequestBase method = null;
        String responseBody = "";
        try {
            if (httpClient == null) {
                initPools();
            }
            method = getRequest(uri, HttpGet.METHOD_NAME, DEFAULT_CONTENT_TYPE, 0);
            HttpContext context = HttpClientContext.create();
            if (mapHeader != null) {
                for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
                    method.addHeader(entry.getKey(), entry.getValue());
                }
            }
            CloseableHttpResponse httpResponse = httpClient.execute(method, context);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println("请求URL: " + uri + "————>返回状态码：" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            if (method != null) {
                method.abort();
            }
            e.printStackTrace();
            System.out.println("err execute get request exception, url:" + uri + ", exception:" + e.toString() + ",cost time(ms):"
                    + (System.currentTimeMillis() - startTime));
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consumeQuietly(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("err close response exception, url:" + uri + ", exception:" + e.toString() + ",cost time(ms):"
                            + (System.currentTimeMillis() - startTime));
                }
            }
        }
        return responseBody;
    }

    public static String getLocationUrl(String url) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(10000).setSocketTimeout(50000)
                .setRedirectsEnabled(false).build();//不允许重定向
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        String location = null;
        int responseCode = 0;

        HttpResponse response;
        try {
            response = httpClient.execute(new HttpGet(url));
            responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 302) {
                Header locationHeader = response.getFirstHeader("Location");
                location = locationHeader.getValue();

            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return location;
    }
}