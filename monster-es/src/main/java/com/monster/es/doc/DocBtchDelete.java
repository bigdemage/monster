package com.monster.es.doc;

import com.alibaba.fastjson.JSONObject;
import com.monster.es.base.EsClient;
import com.monster.es.base.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
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
public class DocBtchDelete {

    public static void main(String[] args) {

        EsClient.connect(client -> {
            BulkRequest request=new BulkRequest();
            for(int i=1;i<=5;i++){
                DeleteRequest indexReq = new DeleteRequest().index("lynuser").id(2000 + i + "");
                request.add(indexReq);
            }
            BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
            System.out.println("_tooks:"+bulkResponse.getTook());
            System.out.println("_items:"+bulkResponse.getItems().length);

        });
    }
}
