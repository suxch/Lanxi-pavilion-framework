package cn.com.blueInfo.utils.client;

import cn.com.blueInfo.utils.PubFuncUtil;
import cn.com.blueInfo.utils.entity.BootstrapTable;
import cn.com.blueInfo.utils.entity.ResultInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

/**
 * @Description: TODO
 * @BelongsProject: springboot2-parent
 * @BelongsPackage: cn.com.blueInfo.framework.client
 * @Author: suxch
 * @CreateTime: 2024/8/15 16:25
 * @Version: 1.0
 */
public class HttpClient {

    /**
     * ResultInfo Post请求
     * @Title: resultInfoPost
     * @param url
     * @param requestParam
     * @return ResultInfo
     * @throws
     */
    public static ResultInfo resultInfoPost(String url, String requestParam) {
        ResultInfo result;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (requestParam != null && !requestParam.isEmpty())
            paramMap.put("requestParam", requestParam);

        String resultString = doPost(null, url, paramMap);
        result = JSON.parseObject(resultString, ResultInfo.class);
        System.out.println(result);
        return result;
    }

    /**
     * fileUpload post请求
     * @Title: fileInfoPost
     * @param url
     * @param request
     * @return ResultInfo
     * @throws
     */
    public static ResultInfo fileInfoPost(String url, HttpServletRequest request) {
        ResultInfo result;
        String resultString = doFilePost(url, request);
        System.out.println(resultString);
        result = JSON.parseObject(resultString, ResultInfo.class);
        return result;
    }

    /**
     * fileDownLoad post请求
     * @Title: downLoadFilePost
     * @param url
     * @param request
     * @return ResultInfo
     * @throws
     */
    public static ResultInfo downLoadFilePost(String url, HttpServletRequest request, HttpServletResponse servletResponse) {
        ResultInfo result = new ResultInfo();
        downFilePost(url, request, servletResponse);
        // result = JSON.parseObject(resultString, ResultInfo.class);
        // String result = resultString;
        // result.setData(resultString);
        return result;
    }

    /**
     * BootstrapTable Post请求
     * 用于表格直接发送请求
     * @Title: bootstrapTablePost
     * @param url
     * @param param
     * @return BootstrapTable
     * @throws
     */
    public static BootstrapTable bootstrapTablePost(String url, JSONObject param) {
        BootstrapTable result;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(param != null){
            paramMap.put("1", param.toJSONString());
        }
        String resultString = HttpClient.doPost("bootstrap", url, paramMap);
        result = JSON.parseObject(resultString, BootstrapTable.class);
        return result;
    }

    /**
     * BootstrapTable Post请求
     * 用于静态数据加载表格时使用
     * @Title: bootstrapPostTable
     * @param url
     * @param requestParam
     * @return BootstrapTable
     * @throws
     */
    public static BootstrapTable bootstrapTablePost(String url, String requestParam) {
        BootstrapTable result;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("requestParam", requestParam);
        String resultString = HttpClient.doPost(null, url, paramMap);
        result = JSON.parseObject(resultString, BootstrapTable.class);
        return result;
    }

    /**
     * 豆瓣网的get请求
     * @Title: doGetByDouBan
     * @param url
     * @return String
     * @throws
     */
    public static String doGetByDouBan(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            //httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("客户端协议异常", e);
        } catch (IOException e) {
            throw new RuntimeException("返回数据解析异常", e);
        } finally {
            PubFuncUtil.closeResource(response, httpClient);
        }
        return result;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * get请求
     * @Title: doGet
     * @param url
     * @return String
     * @throws
     */
    public static String doGet(String url, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            for (int h_i = 0, h_len = headerMap.size(); h_i < h_len; h_i++) {

            }
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("客户端协议异常", e);
        } catch (IOException e) {
            throw new RuntimeException("返回数据解析异常", e);
        } finally {
            PubFuncUtil.closeResource(response, httpClient);
        }
        return result;
    }

    public static String doPost(String url, Map<String, Object> paramMap) {
        return doPost(null, url, paramMap);
    }

    /**
     * post请求
     * Form Data请求方式
     * @Title: doPost
     * @param url
     * @param paramMap
     * @return String
     * @throws
     */
    private static String doPost(String type, String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        if (StringUtils.isNotEmpty(type) && "bootstrap".equals(type)) {
            httpPost.addHeader("Content-Type", "application/json");
        } else {
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        // 封装post请求参数
        if (null != paramMap && !paramMap.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            for (Entry<String, Object> mapEntry : entrySet) {
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("参数加密异常", e);
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("客户端协议异常", e);
        } catch (IOException e) {
            throw new RuntimeException("返回数据解析异常", e);
        } finally {
            PubFuncUtil.closeResource(httpResponse, httpClient);
        }
        return result;
    }

    /**
     * 文件上传post
     * @Title: doFilePost
     * @param url
     * @param request
     * @return String
     * @throws
     */
    public static String doFilePost(String url, HttpServletRequest request) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(600000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(600000)// 设置连接请求超时时间
                .setSocketTimeout(600000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.setCharset(StandardCharsets.UTF_8);
		/*
		String userLoginName = request.getParameter("userLoginName");
		httpPost.addHeader("Authorization", userLoginName);*/

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator<?> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                List<MultipartFile> fileList = multiRequest.getFiles(iter.next().toString());
                for (int f_i = 0, f_len = fileList.size(); f_i < f_len; f_i++) {
                    String fileName = fileList.get(0).getOriginalFilename();
                    InputStream input = null;
                    try {
                        input = fileList.get(0).getInputStream();
                        reqEntity.addBinaryBody("file", input, ContentType.APPLICATION_OCTET_STREAM, fileName);
                        String requestData = request.getParameter("requestParam");
                        reqEntity.addTextBody("requestParam", requestData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        HttpEntity multipart = reqEntity.build();
        httpPost.setEntity(multipart);
        // }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("客户端协议异常", e);
        } catch (IOException e) {
            throw new RuntimeException("返回数据解析异常", e);
        } finally {
            PubFuncUtil.closeResource(httpResponse, httpClient);
        }
        return result;

    }

    /**
     * post文件下载
     * @Title: downFilePost
     * @param url
     * @param request
     * @return String
     * @throws
     */
    public static OutputStream downFilePost(String url, HttpServletRequest request, HttpServletResponse servletResponse) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        OutputStream backContent = null;
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
		/*String encoderMessage;
		try {*/
        String userLoginName = request.getParameter("userLoginName");
        //encoderMessage = URLEncoder.encode(userLoginName,"UTF-8");
        httpPost.addHeader("Authorization", userLoginName);
		/*} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        String fileName = request.getParameter("fileName");
        if (fileName != null) {
            try {
                System.out.println(new String(fileName.getBytes("iso-8859-1"), "UTF-8"));
                reqEntity.addTextBody("fileName", fileName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        reqEntity.addTextBody("piid", request.getParameter("piid"));
        HttpEntity multipart = reqEntity.build();
        httpPost.setEntity(multipart);
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            servletResponse.setHeader("Content-Disposition", "attachment;fileName=" + getFileName(httpResponse));
            servletResponse.setHeader("Content-type", "multipart/form-data");
            HttpEntity entity = httpResponse.getEntity();
            InputStream is = entity.getContent();
            try {
                OutputStream fout = servletResponse.getOutputStream();
                int l = -1;
                byte[] tmp = new byte[1024];
                while ((l = is.read(tmp)) != -1) {
                    fout.write(tmp, 0, l);
                    // 注意这里如果用OutputStream.write(buff)的话，图片会失真
                }
                backContent = fout;
                fout.flush();
                fout.close();
            } finally {
                // 关闭低层流。
                is.close();
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException("客户端协议异常", e);
        } catch (IOException e) {
            throw new RuntimeException("返回数据解析异常", e);
        } finally {
            PubFuncUtil.closeResource(httpResponse, httpClient);
        }
        return backContent;

    }

    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        // filename = new
                        // String(param.getValue().toString().getBytes(),
                        // "utf-8");
                        // filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return filename;
    }

}
