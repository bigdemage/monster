package com.monster.es.base;

import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public interface EsTask {

    void doSomething(RestHighLevelClient client) throws Exception;
}
