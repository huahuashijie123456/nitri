package com.fuhuitong.nitri.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {
    /*private int page;
    private int rows;
    private int total;*/
    private T data;
}
