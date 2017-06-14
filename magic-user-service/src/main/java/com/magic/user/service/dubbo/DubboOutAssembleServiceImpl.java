package com.magic.user.service.dubbo;

import com.alibaba.fastjson.JSON;
import com.magic.api.commons.ApiLogger;
import com.magic.bc.query.service.AgentSchemeService;
import com.magic.bc.query.service.UserLevelService;
import com.magic.bc.query.vo.UserLevelVo;
import com.magic.cms.service.MsgDubboService;
import com.magic.config.service.DomainDubboService;
import com.magic.config.service.RegisterDubboService;
import com.magic.config.vo.OwnerDomainVo;
import com.magic.config.vo.OwnerInfo;
import com.magic.passport.po.SubAccount;
import com.magic.passport.service.dubbo.PassportDubboService;
import com.magic.service.java.UuidService;
import com.magic.user.enums.AccountType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * User: joey
 * Date: 2017/5/19
 * Time: 16:33
 *
 * @Doc 统一管理对外部dubbo接口的调用
 */
@Service("dubboOutAssembleService")
public class DubboOutAssembleServiceImpl {

    @Resource
    private UuidService uuidService;

    @Resource
    private PassportDubboService passportDubboService;

    @Resource
    private DomainDubboService domainDubboService;

    @Resource
    private AgentSchemeService agentSchemeService;

    @Resource
    private UserLevelService userLevelService;

    @Resource
    private MsgDubboService msgDubboService;

    @Resource
    private RegisterDubboService registerDubboService;

    private static final Map<Long, String> EMPTY_MAP = new HashMap<>();

    /**
     * 分配ID 13位时间戳+2位机器识别码+4位随机数
     *
     * @return
     */
    public long assignUid() {
        try {
            return uuidService.assignUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过域名获取业主信息
     * @param sourceUrl
     * @return
     */
    public OwnerInfo getOwnerInfoByDomain(String sourceUrl) {
        try {
           return domainDubboService.getOwnerInfoByDomain(sourceUrl);
        } catch (Exception e) {
            ApiLogger.error(String.format("Failed to get the owner information. sourceUrl: %s", sourceUrl), e);
        }
        return null;
    }

    /**
     * 获取用户最近登陆数据，如登录ip和登录时间
     *
     * @param uid
     * @return
     */
    public SubAccount getSubLoginById(Long uid) {
        try {
            return passportDubboService.getSubLoginById(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @doc 获取多个用户的最近登录时间
     * @param ids
     * @return
     */
    public Map<Long, SubAccount> getSubLogins(Set<Long> ids) {
        try {
            return passportDubboService.getSubLogins(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 获取用户ID
     *
     * @param ownerId
     * @param username
     * @return
     */
    public long getUid(Long ownerId, String username) {
        try {
            return passportDubboService.getUid(ownerId, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取配置列表
     * @param ownerId
     * @return
     */
    public Map<String,Object> agentSchemeList(Long ownerId) {
        try {
            return agentSchemeService.agentSchemeList(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取业主的所有域名
     * @param ownerId
     * @return
     */
    public List<OwnerDomainVo> queryAllDomainList(Long ownerId) {
        try {
            return domainDubboService.queryAllDomainList(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取层级名称
     *
     * @param ids
     * @return
     */
    public Map<Long, String> getLevels(Collection<Long> ids){
        try {
            return userLevelService.getBatchByIds(ids);
        }catch (Exception e){
            ApiLogger.error(String.format("get levels error. ids: %s", JSON.toJSONString(ids)), e);
        }
        return EMPTY_MAP;
    }

    /**
     * 获取层级映射列表
     * @param ownerId
     * @return
     */
    public List<UserLevelVo> getLevelListSimple(Long ownerId) {
        try {
            return userLevelService.getUserLevel(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取会员的未读消息数量
     * @param uid
     * @return
     */
    public long getNoReadMessageCount(long uid) {
        long result = 0;
        try {
            result = msgDubboService.getNoReadMessageCount(uid);
        } catch (Exception e) {
            ApiLogger.error("failed to get membership number of unread messages!",e);
        }
        return result;
    }

    /**
     * 获取注册时设置的必填项
     * @param ownerId
     * @param type
     * @return
     */
    public List<String> getMustRegisterarameters(Long ownerId, int type) {
        if(type == AccountType.member.value()){
            type = 1;//会员
        }else if(type == AccountType.agent.value()){
            type = 2;//代理
        }else{
            return null;
        }
        List<String> result = null;
        try {
            result = registerDubboService.getRequired(ownerId,type);
        } catch (Exception e) {
            ApiLogger.error("获取注册时设置的必填项失败", e);
        }
        return result;
    }
}