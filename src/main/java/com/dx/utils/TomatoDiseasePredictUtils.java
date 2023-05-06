package com.dx.utils;

import com.dx.entity.PredictJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dx.entity.PredictResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author 32825
 */
public class TomatoDiseasePredictUtils {
    static int result=-1;
    public static int predict(String picturePath){
        ObjectMapper objectMapper = new ObjectMapper();
        PredictJson pj=new PredictJson(picturePath);
        try {
            String predictJson = objectMapper.writeValueAsString(pj);
            String reultJson=sendJsonPost("http://47.94.206.121:8501/v1/models/my_model:predict",predictJson);
            PredictResult pr = objectMapper.readValue(reultJson, PredictResult.class);
            result= findMax(pr.getPredictions()[0]);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static int findMax(float[] arr) {
        float max = arr[0];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] >max){
                max= arr[i];
                index = i;
            }
        }
        return index;
    }

    public static String sendJsonPost(String url, String sendData) {
        String body = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            //装填参数
            StringEntity s = new StringEntity(sendData,"UTF8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
            //设置header信息
            httpPost.setHeader("Content-type", "application/json");
//            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //执行请求操作，并拿到结果（同步阻塞）
            HttpResponse response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "UTF8");
            }
//            EntityUtils.consume(entity);
            //为防止频繁调用一个接口导致接口爆掉，每次调用完成后停留100毫秒
            Thread.sleep(100);
        } catch (Exception e) {
//            log.info("JSON数据发送失败，异常：{}", e.getMessage());
//            log.error("异常：", e);
        }
        return body;
    }
}
