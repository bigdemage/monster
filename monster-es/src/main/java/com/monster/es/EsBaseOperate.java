package com.monster.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * @ClassName EsClient
 * @Deacription 创建连接
 * @Author wrx
 * @Date 2022/9/24/024 16:57
 * @Version 1.0
 **/
public class EsBaseOperate {

    public static void main(String[] args) throws IOException {
        //获取连接
        RestHighLevelClient client=getClient();

        //创建索引
//        createIndex(client);

        //查询索引
        getIndex(client);

        //删除索引
//        deleteIndex(client);

        client.close();
    }

    private static void deleteIndex(RestHighLevelClient client) throws IOException {
        DeleteIndexRequest res = new DeleteIndexRequest("wrxbank");
        AcknowledgedResponse response = client.indices().delete(res, RequestOptions.DEFAULT);
        System.out.println("删除结果:"+JSONObject.toJSONString(response));
    }

    /**
     * 查询索引
     * @param client
     */
    private static void getIndex(RestHighLevelClient client) throws IOException {
        String indexName="lynuser";
        GetIndexRequest req = new GetIndexRequest(indexName);
        GetIndexResponse res = client.indices().get(req, RequestOptions.DEFAULT);
        System.out.println("查询别名:"+res.getAliases());
        System.out.println("查询映射:"+res.getMappings());
        System.out.println("查询配置:"+res.getSettings());


    }


    private static RestHighLevelClient getClient() {
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
        return client;
    }
    /**
     * 创建
     * @param client
     * @throws IOException
     */
    private static void createIndex(RestHighLevelClient client) throws IOException {
        //创建索引
        CreateIndexRequest request=new CreateIndexRequest("lynpdd");
        CreateIndexResponse response=client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("索引操作结果:"+ JSONObject.toJSONString(response));
    }

}
