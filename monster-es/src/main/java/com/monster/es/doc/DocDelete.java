package com.monster.es.doc;

import com.monster.es.base.EsClient;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;

/**
 * @ClassName DocInsert
 * @Deacription 文档删除
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocDelete {

    public static void main(String[] args) {

        EsClient.connect(client -> {

            DeleteRequest req=new DeleteRequest().index("lynuser").id("1003");

            DeleteResponse response = client.delete(req, RequestOptions.DEFAULT);

            //打印结果
            System.out.println("结果:"+response.toString());
        });
    }
}
