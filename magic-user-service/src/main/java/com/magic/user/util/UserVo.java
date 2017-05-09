package com.magic.user.util;

import com.magic.user.entity.User;
import com.magic.user.enums.*;
import com.magic.user.utils.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * User: joey
 * Date: 2017/5/6
 * Time: 14:43
 */
@Document(collection = "user")
public class UserVo {

    @Id
    private long id;

    private long userId;   //代理id

    private String realname;    //真实姓名

    private String username;    //账号

    private String telephone;   //电话

    private String email;   //电子邮箱

    private Date registerTime;  //注册时间

    private int registerIp; //注册ip

    private String generalizeCode;  //推广码

    private int gender;  //性别

    private int status;   //账号使用状态

    private int currencyType;  //币种

    private String bankCardNo;  //银行卡号

    private long ownerId;  //业主id

    private int isDelete;  //是否删除

    private int type;   //账号类型

    @Version
    private Long version;

    public static UserVo parseUserVo(User user) {
        UserVo userVo = new UserVo();
        if (user.getId() > 0) {
            userVo.setId(user.getId());
        }
        if (user.getUserId() > 0) {
            userVo.setUserId(user.getUserId());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            userVo.setEmail(user.getEmail());
        }
        if (StringUtils.isNotBlank(user.getUsername())) {
            userVo.setUsername(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getBankCardNo())) {
            userVo.setBankCardNo(user.getBankCardNo());
        }
        if (user.getCurrencyType() != null) {
            userVo.setCurrencyType(user.getCurrencyType().value());
        }
        if (user.getGender() != null) {
            userVo.setGender(user.getGender().value());
        }
        if (user.getIsDelete() != null) {
            userVo.setIsDelete(user.getIsDelete().value());
        }
        if (StringUtils.isNotBlank(user.getGeneralizeCode())) {
            userVo.setGeneralizeCode(user.getGeneralizeCode());
        }
        if (user.getOwnerId() > 0) {
            userVo.setOwnerId(user.getOwnerId());
        }
        if (StringUtils.isNotBlank(user.getRealname())) {
            userVo.setRealname(user.getRealname());
        }
        if (user.getRegisterIp() > 0) {
            userVo.setRegisterIp(user.getRegisterIp());
        }
        if (user.getRegisterTime() != null) {
            userVo.setRegisterTime(user.getRegisterTime());
        }
        if (user.getStatus() != null) {
            userVo.setStatus(user.getStatus().value());
        }
        if (StringUtils.isNotBlank(user.getTelephone())) {
            userVo.setTelephone(user.getTelephone());
        }
        if (user.getType() != null) {
            userVo.setType(user.getType().value());
        }
        return userVo;
    }

    public static User parseUser(UserVo userVo) {
        User user = new User();
        if (userVo.getId() > 0) {
            user.setId(userVo.getId());
        }
        if (userVo.getUserId() > 0) {
            user.setUserId(userVo.getUserId());
        }
        if (StringUtils.isNotBlank(userVo.getEmail())) {
            user.setEmail(user.getEmail());
        }
        if (StringUtils.isNotBlank(userVo.getUsername())) {
            user.setUsername(user.getUsername());
        }
        if (StringUtils.isNotBlank(userVo.getBankCardNo())) {
            user.setBankCardNo(user.getBankCardNo());
        }
        if (userVo.getCurrencyType() > 0) {
            user.setCurrencyType(CurrencyType.parse(userVo.getCurrencyType()));
        }
        if (userVo.getGender() > 0) {
            user.setGender(GeneraType.parse(userVo.getGender()));
        }
        if (userVo.getIsDelete() > 0) {
            user.setIsDelete(DeleteStatus.parse(userVo.getIsDelete()));
        }
        if (StringUtils.isNotBlank(userVo.getGeneralizeCode())) {
            user.setGeneralizeCode(user.getGeneralizeCode());
        }
        if (userVo.getOwnerId() > 0) {
            user.setOwnerId(user.getOwnerId());
        }
        if (StringUtils.isNotBlank(userVo.getRealname())) {
            user.setRealname(user.getRealname());
        }
        if (userVo.getRegisterIp() > 0) {
            user.setRegisterIp(user.getRegisterIp());
        }
        if (userVo.getRegisterTime() != null) {
            user.setRegisterTime(user.getRegisterTime());
        }
        if (userVo.getStatus() > 0) {
            user.setStatus(AccountStatus.parse(userVo.getStatus()));
        }
        if (StringUtils.isNotBlank(userVo.getTelephone())) {
            user.setTelephone(user.getTelephone());
        }
        if (userVo.getType() > 0) {
            user.setType(AccountType.parse(userVo.getType()));
        }
        return user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(int registerIp) {
        this.registerIp = registerIp;
    }

    public String getGeneralizeCode() {
        return generalizeCode;
    }

    public void setGeneralizeCode(String generalizeCode) {
        this.generalizeCode = generalizeCode;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(int currencyType) {
        this.currencyType = currencyType;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}