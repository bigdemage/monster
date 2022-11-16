package com.monster.lru;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LruCache
 * @Deacription TODO
 * @Author wrx
 * @Date 2022/3/14/014 17:19
 * @Version 1.0
 * 头节点，尾节点，容量限制，字典表，构造方法
 **/
public class LruCache {
    //长度
    private int limit;

    //头
    private Node head;
    //尾
    private Node end;

    private Map<String,Node> hashMap;

    public LruCache(int limit){
        this.limit=limit;
        hashMap=new HashMap<>();
    }

    /**
     * 取值
     * @param key
     * @return
     */
    private String get(String key){
        Node node=hashMap.get(key);
        if(node==null){
            return "-1";
        }
        //取值后node移到链表尾部
        moveNodeTail(node);
        //返回值
        return node.getValue();
    }

    /**
     * 插入一个值
     * @param value
     */
    private void put(String key,String value){
        Node node=hashMap.get(key);
        //两种情况，node存在和不存在，存在则更新值并且放链尾；不存在就要put值
        if(node!=null){
            moveNodeTail(node);
            hashMap.put(key,node);
        }else{
            int size=hashMap.size();
            //如果长度已经是最大长度，则需要删除头节点
            if(size>=limit){
                String deleteKey =deleteNode(head);
                hashMap.remove(deleteKey);
            }
            node=new Node(key,value);
            //加到链尾
            addTailNode(node);
            //map插入值
            hashMap.put(key,node);
        }

    }


    /**
     * 节点移动到尾部
     * @param node
     */
    private void moveNodeTail(Node node) {
        //如果已经是尾节点则不用移动
        if(node==end){
            return;
        }
        //把节点在当前位置删除
        deleteNode(node);
        //放链表尾部
        addTailNode(node);
    }


    /**
     * 删除当前节点
     * 返回key让map删除
     * @param node
     */
    private String deleteNode(Node node) {
        if(node==head && node==end){
            head=null;
            end=null;
        }else if(node==head){
            head=head.getNext();
            head.setPre(null);
        }else if(node==end){
            end=end.getPre();
            end.setNext(null);
        }else{
            node.getPre().setNext(node.getNext());
            node.getNext().setPre(node.getPre());
        }

        return node.getKey();
    }

    /**
     * node移动到尾部
     * @param node
     */
    private void addTailNode(Node node) {
        //尾部有节点，则把尾部往前挪一个位置
        if(end!=null){
            end.setNext(node);
            node.setPre(end.getNext());
            node.setNext(null);
        }
        end=node;
        if(head==null){
            head=node;
        }
    }

}


