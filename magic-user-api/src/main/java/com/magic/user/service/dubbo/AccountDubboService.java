package com.magic.user.service.dubbo;

import com.magic.user.entity.Member;
import com.magic.user.entity.User;
import com.magic.user.po.OwnerStaticInfo;

import java.util.Collection;
import java.util.Map;

/**
 * AccountDubboService
 *
 * @author zj
 * @date 2017/5/15
 */
public interface AccountDubboService {

    /**
     * 获取业主ID
     * @param uid 股东或代理ID
     * @return
     */
    long getOwnerId(long uid);

    /**
     * 获取用户
     * @param uid 股东或代理ID
     * @return
     */
    User getUser(long uid);

    /**
     * 获取会员
     * @param uid
     * @return
     */
    Member getMember(long uid);

    /**
     * 获取业主下股东、代理、会员、子账号数
     *
     * @param ownerIds
     * @return
     */
    Map<Long, OwnerStaticInfo> getOwnerStaticInfo(Collection<Long> ownerIds);

    /**
     * 检查uid是否登录，是否禁用
     * @param uid
     * @return
     */
    Member checkMemberLogin(long uid);
}
