package com.fuhuitong.nitri.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author Wang
 * @Date 2019/6/14 0014 11:33
 **/
@Data
public class AweekStatistics implements Serializable {


    private String weekday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate clickDate;


    private BigDecimal sumamount;

    private int count;


}
