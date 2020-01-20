package com.fuhuitong.nitri.common.utils.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Wang
 * @Date 2019/5/14 0014 9:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExcelEntity implements Serializable {
    private Integer sort;
    private String fieldName;
    private String name;
    private String dict;
    private String dataformat;
    private boolean permission;
}
