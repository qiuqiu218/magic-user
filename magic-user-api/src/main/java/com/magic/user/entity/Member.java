package com.magic.user.entity;

import com.magic.user.enums.AccountStatus;
import com.magic.user.enums.CurrencyType;
import com.magic.user.enums.DeleteStatus;
import com.magic.user.enums.GeneraType;

import java.util.Date;

/**
 * @Doc 会员
 */
public class Member {
    private long id;

    private long memberId; //会员id

    private String realname;    //真实姓名

    private String username;    //账号

    private String telephone;   //电话

    private String bankCardNo;  //银行卡号

    private long agentId;   //代理id

    private String agentUsername; //代理账号

    private long stockId;   //股东id

    private String stockUsername;   //股东账号

    private long proprietorId;  //业主id

    private String proprietorUsername; //业主账号

    private String sourceUrl;   //加入来源网址

    private int registerIp; //注册ip

    private Date registerTime;  //注册时间

    private AccountStatus status; //状态

    private GeneraType gender;  //性别

    private CurrencyType currencyType;   //币种

    private DeleteStatus isDelete;      //删除状态

    private String temp1;

    private String temp2;

    private String temp3;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public String getStockUsername() {
        return stockUsername;
    }

    public void setStockUsername(String stockUsername) {
        this.stockUsername = stockUsername;
    }

    public long getProprietorId() {
        return proprietorId;
    }

    public void setProprietorId(long proprietorId) {
        this.proprietorId = proprietorId;
    }

    public String getProprietorUsername() {
        return proprietorUsername;
    }

    public void setProprietorUsername(String proprietorUsername) {
        this.proprietorUsername = proprietorUsername;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(int registerIp) {
        this.registerIp = registerIp;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public GeneraType getGender() {
        return gender;
    }

    public void setGender(GeneraType gender) {
        this.gender = gender;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public DeleteStatus getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(DeleteStatus isDelete) {
        this.isDelete = isDelete;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }
}