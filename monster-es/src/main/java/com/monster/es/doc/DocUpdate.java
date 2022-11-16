package com.monster.es.doc;

import com.alibaba.fastjson.JSONObject;
import com.monster.es.base.EsClient;
import com.monster.es.base.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @ClassName DocInsert
 * @Deacription 文档修改
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocUpdate {

    public static void main(String[] args) {

        EsClient.connect(client -> {

            UpdateRequest req =new UpdateRequest();
            req.index("lynuser").id("2005");
            req.doc(XContentType.JSON,"sex","female","name","树枝孤鸟");
            UpdateResponse response = client.update(req, RequestOptions.DEFAULT);
            //打印结果
            System.out.println("_index:"+response.getIndex());
            System.out.println("_id:"+response.getId());
            System.out.println("_result:"+response.getResult());

        });
    }
}
