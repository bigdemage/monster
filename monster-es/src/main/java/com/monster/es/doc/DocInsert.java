package com.monster.es.doc;

import com.alibaba.fastjson.JSONObject;
import com.monster.es.base.EsClient;
import com.monster.es.base.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @ClassName DocInsert
 * @Deacription 文档增加
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocInsert {

    public static void main(String[] args) {

        EsClient.connect(client -> {
            IndexRequest req =new IndexRequest();
            //设置索引和id
            req.index("lynuser").id("1003");
            //创建数据
            User user = User.builder().age(12).name("啊卡卡").sex("Man").build();
            String userJson= JSONObject.toJSONString(user);
            //添加文档数据
            req.source(userJson, XContentType.JSON);
            IndexResponse response = client.index(req, RequestOptions.DEFAULT);
            //打印结果
            System.out.println("_index:"+response.getIndex());
            System.out.println("_id:"+response.getId());
            System.out.println("_result:"+response.getResult());
        });
    }
}
