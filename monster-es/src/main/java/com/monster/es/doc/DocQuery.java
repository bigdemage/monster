package com.monster.es.doc;

import com.alibaba.fastjson.JSONObject;
import com.monster.es.base.EsClient;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @ClassName DocInsert
 * @Deacription 批量增加文档
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocQuery {

    public static void main(String[] args) {

        EsClient.connect(client -> {
            SearchRequest request=new SearchRequest();
            request.indices("lynuser");
            //构建查询请求体
            SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
            //查询所有数据
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            request.source(sourceBuilder);
            //查询结果
            SearchResponse response=client.search(request,RequestOptions.DEFAULT);
            SearchHits hits=response.getHits();
            System.out.println("took:"+response.getTook());
            System.out.println("timeout:"+response.isTimedOut());
            System.out.println("total:"+hits.getTotalHits());
            System.out.println("maxScore:"+hits.getMaxScore());
            System.out.println("hits------------>");
            hits.forEach(hit->{
                System.out.println(hit.getId()+"-"+hit.getSourceAsString());
            });
        });
    }
}
