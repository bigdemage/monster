package com.monster.util;

import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

public class Jjhj {
    public static void main(String[] args) {

        String eventDate="aabb";
        String city="lanzhou";

        String eventId=eventDate;
        if(StringUtils.isNotBlank(city)) eventId+="_"+city;

        System.out.println(eventId);

        String ss=new StringJoiner("_").add("dynnnnn" + eventDate).add(city).toString();
        System.out.println(ss);

    }
}
