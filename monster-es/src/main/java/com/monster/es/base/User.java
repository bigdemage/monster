package com.monster.es.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName User
 * @Deacription TODO
 * @Author wrx
 * @Date 2022/9/24/024 17:48
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -1940746705463092922L;
    private String name;
    private String sex;
    private Integer age;
}
