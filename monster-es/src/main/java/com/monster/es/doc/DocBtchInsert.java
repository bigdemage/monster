package com.monster.es.doc;

import com.alibaba.fastjson.JSONObject;
import com.monster.es.base.EsClient;
import com.monster.es.base.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @ClassName DocInsert
 * @Deacription 批量增加文档
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocBtchInsert {

    public static void main(String[] args) {

        EsClient.connect(client -> {
            BulkRequest request=new BulkRequest();
            for(int i=1;i<=10;i++){
                User user= User.builder().name("芭比波"+i+"号").age(i+20).sex("man").build();
                IndexRequest indexReq = new IndexRequest().index("lynuser").id(2000 + i + "").source(JSONObject.toJSONString(user), XContentType.JSON);
                request.add(indexReq);
            }
            BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
            System.out.println("_tooks:"+bulkResponse.getTook());
            System.out.println("_items:"+bulkResponse.getItems());

        });
    }
}
