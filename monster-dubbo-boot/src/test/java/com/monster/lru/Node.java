package com.monster.lru;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName Node
 * @Deacription Lru实现，http://www.xiaojieboshi.com/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84/%E4%BB%8Eleetcode%E7%9C%9F%E9%A2%98%E8%AE%B2%E8%A7%A3%E6%89%8B%E5%86%99LRU%E7%AE%97%E6%B3%95.html#%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E9%80%89%E5%9E%8B
 * @Author wrx
 * @Date 2022/3/14/014 17:15
 * @Version 1.0
 **/
@Data
public class Node {
    //前驱节点
    private Node pre;
    //后驱节点
    private Node next;
    private String key;
    private String value;

    public Node(String key,String value){
        this.key=key;
        this.value=value;
    }

}
