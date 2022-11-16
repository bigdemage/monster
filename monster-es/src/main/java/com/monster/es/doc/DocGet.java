package com.monster.es.doc;

import com.alibaba.fastjson.JSONObject;
import com.monster.es.base.EsClient;
import com.monster.es.base.User;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @ClassName DocInsert
 * @Deacription 文档查询
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocGet {

    public static void main(String[] args) {

        EsClient.connect(client -> {
            GetRequest req = new GetRequest().index("lynuser").id("2003");

            GetResponse response = client.get(req, RequestOptions.DEFAULT);

            //打印结果
            System.out.println("_index:"+response.getIndex());
            System.out.println("_id:"+response.getId());
            System.out.println("_type:"+response.getType());
            System.out.println("查询结果:"+response.getSourceAsString());
        });
    }
}
