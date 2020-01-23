package com.sse.tangpoetry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @name: Person
 * @author: yf.xiang
 * @create: 2020-01-22 20:48
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Integer id;

    private String name;
}
