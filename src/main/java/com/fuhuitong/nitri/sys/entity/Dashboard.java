package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Wang
 * @Date 2019/6/6 0006 15:44
 **/

@Data
public class Dashboard implements Serializable {
    /**
     * 今日贷款数量
     */
    private int dayLoanCount;

    /**
     * 今日贷款金额
     */
    private BigDecimal dayLoanMonySum;

    /**
     * 贷款总量
     */
    private int LoanCount;

    /**
     * 贷款总金额
     */
    private BigDecimal LoanMonySum;

    /**
     * 今日用户注册量
     */
    private int dayRegisterCount;

    /**
     * 用户注册总量
     */
    private int RegisterCount;

    /**
     * 商户数量
     */
    private int merchantCount;

    @TableField(exist = false)
    private List<AweekStatistics> aweekStatisticsList;


   /* public int getDayLoanCount() {
        return dayLoanCount;
    }

    public void setDayLoanCount(int dayLoanCount) {
        this.dayLoanCount = dayLoanCount;
    }

    public BigDecimal getDayLoanMonySum() {
       return dayLoanMonySum.setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }

    public void setDayLoanMonySum(BigDecimal dayLoanMonySum) {
        this.dayLoanMonySum = dayLoanMonySum;
    }

    public int getLoanCount() {
        return LoanCount;
    }

    public void setLoanCount(int loanCount) {
        LoanCount = loanCount;
    }

    public BigDecimal getLoanMonySum() {
        return LoanMonySum.setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }

    public void setLoanMonySum(BigDecimal loanMonySum) {
        LoanMonySum = loanMonySum;
    }

    public int getDayRegisterCount() {
        return dayRegisterCount;
    }

    public void setDayRegisterCount(int dayRegisterCount) {
        this.dayRegisterCount = dayRegisterCount;
    }

    public int getRegisterCount() {
        return RegisterCount;
    }

    public void setRegisterCount(int registerCount) {
        RegisterCount = registerCount;
    }

    public int getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(int merchantCount) {
        this.merchantCount = merchantCount;
    }*/
}
