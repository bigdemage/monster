package com.monster.es.base;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @ClassName EsClient
 * @Deacription 创建连接
 * @Author wrx
 * @Date 2022/9/24/024 16:57
 * @Version 1.0
 **/
public class EsClient {


    public static void connect(EsTask task) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "elastic"));  //es账号密码（默认用户名为elastic）
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.70.5.23", 9200, "http"),
                        new HttpHost("10.70.5.24", 9200, "http"),
                        new HttpHost("10.70.5.25", 9200, "http"))
                          .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                              @Override
                              public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                  httpClientBuilder.disableAuthCaching();
                                  return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                              }
                          }));
        try{
            task.doSomething(client);
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
