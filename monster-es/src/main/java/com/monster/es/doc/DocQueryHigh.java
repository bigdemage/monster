package com.monster.es.doc;

import com.monster.es.base.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName DocInsert
 * @Deacription 高级查询
 * @Author wrx
 * @Date 2022/9/24/024 17:50
 * @Version 1.0
 **/
public class DocQueryHigh {

    public static void main(String[] args) {

        EsClient.connect(client -> {
            SearchRequest request=new SearchRequest();
            request.indices("lynuser");
            //构建查询请求体
            SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
            int type=9;
                    SearchResponse response=null;
                    switch (type){
                        case 1:
                            //条件查询
                            response=tiaojianchaxun(request,client,sourceBuilder);
                            break;
                        case 2:
                            //分页查询
                            response=fenyechaxun(request,client,sourceBuilder);
                    break;
                case 3:
                    //查询排序
                    response=chaxunpaixu(request,client,sourceBuilder);
                    break;
                case 4:
                    //组合查询
                    response=zuhechaxun(request,client,sourceBuilder);
                    break;
                case 5:
                    //范围查询
                    response=fanweichaxun(request,client,sourceBuilder);
                    break;
                case 6:
                    //模糊查询
                    response=mohuchaxun(request,client,sourceBuilder);
                    break;
                case 7:
                    //高亮查询
                    response=gaoliangchaxun(request,client,sourceBuilder);
                    break;
                case 8:
                    //最大值查询
                    response=zuidazhichaxun(request,client,sourceBuilder);
                    break;
                case 9:
                    //分组查询
                    response=fenzuchaxun(request,client,sourceBuilder);
                    break;
            }
            System.out.println("response:"+response);
            SearchHits hits=response.getHits();
            System.out.println("took:"+response.getTook());
            System.out.println("timeout:"+response.isTimedOut());
            System.out.println("total:"+hits.getTotalHits());
            System.out.println("maxScore:"+hits.getMaxScore());
            System.out.println("hits------------>");
            hits.forEach(hit->{
                System.out.println(hit.getId()+"-"+hit.getSourceAsString());
                //打印高亮结果
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                System.out.println(highlightFields);
            });
            System.out.println("<------------");
        });
    }

    /**
     * 分组查询
     * @param request
     * @param client
     * @param sourceBuilder
     * @return
     */
    private static SearchResponse fenzuchaxun(SearchRequest request, RestHighLevelClient client, SearchSourceBuilder sourceBuilder) throws IOException {
        //根据age分组
        sourceBuilder.aggregation(AggregationBuilders.terms("age_groupby").field("age"));
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 最大值查询
     * @param request
     * @param client
     * @param sourceBuilder
     * @return
     * @throws Exception
     */
    private static SearchResponse zuidazhichaxun(SearchRequest request, RestHighLevelClient client, SearchSourceBuilder sourceBuilder) throws Exception{
        //aggregation-聚合
        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 高亮查询
     * @param request
     * @param client
     * @param sourceBuilder
     * @return
     * @throws Exception
     */
    private static SearchResponse gaoliangchaxun(SearchRequest request, RestHighLevelClient client, SearchSourceBuilder sourceBuilder) throws Exception{
        //条件查询
/*        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name","伍佰"));
        sourceBuilder.query(boolQueryBuilder);*/
        sourceBuilder.query(QueryBuilders.termQuery("name","伍"));
        //构建高亮查询
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        //设置标签前缀
        highlightBuilder.preTags("<font color='red'>");
        //设置标签后缀
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.field("name");
        sourceBuilder.highlighter(highlightBuilder);
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 模糊查询
     * @param request
     * @param client
     * @param sourceBuilder
     * @return
     * @throws IOException
     */
    private static SearchResponse mohuchaxun(SearchRequest request, RestHighLevelClient client, SearchSourceBuilder sourceBuilder) throws IOException {
        //模糊查询
        sourceBuilder.query(QueryBuilders.fuzzyQuery("name","芭比").fuzziness(Fuzziness.ONE));
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 范围查询
     * @param request
     * @param client
     * @param sourceBuilder
     * @return
     * @throws IOException
     */
    private static SearchResponse fanweichaxun(SearchRequest request, RestHighLevelClient client, SearchSourceBuilder sourceBuilder) throws IOException {
        //根据什么范围
        RangeQueryBuilder rangeQuery=QueryBuilders.rangeQuery("age");
        //大于等于---gte greater than or equal
        //大于---gt greater than
        //小于---lt less than
        //小于等于---lte less than or equal
        rangeQuery.lte(25);
        sourceBuilder.query(rangeQuery);
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 组合查询
     * @param request
     * @param client
     * @param sourceBuilder
     * @return
     * @throws IOException
     */
    private static SearchResponse zuhechaxun(SearchRequest request, RestHighLevelClient client, SearchSourceBuilder sourceBuilder) throws IOException {
        //组合查询用bool
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        //必须包含
        boolQueryBuilder.must(QueryBuilders.matchQuery("name","伍佰"));
        //一定不含
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name","伍佰"));
        //可能包含
        boolQueryBuilder.should(QueryBuilders.matchQuery("sex","female"));
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 查询排序
     * @param request
     * @param client
     * @return
     */
    private static SearchResponse chaxunpaixu(SearchRequest request, RestHighLevelClient client,SearchSourceBuilder sourceBuilder) throws IOException {
        //全查
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.sort("age", SortOrder.DESC);
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 分页查询
     * @param request
     * @param client
     * @return
     */
    private static SearchResponse fenyechaxun(SearchRequest request, RestHighLevelClient client,SearchSourceBuilder sourceBuilder) throws IOException {
        //全查
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //分页--从第几页开始/每页多少条
        sourceBuilder.from(0);
        sourceBuilder.size(2);
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

    /**
     * 条件查询
     * @param request
     * @param client
     * @return
     * @throws IOException
     */
    private static SearchResponse tiaojianchaxun(SearchRequest request,RestHighLevelClient client,SearchSourceBuilder sourceBuilder) throws IOException {
        //termQuery会分词
        sourceBuilder.query(QueryBuilders.termQuery("name","伍"));
        request.source(sourceBuilder);
        return client.search(request,RequestOptions.DEFAULT);
    }

}
