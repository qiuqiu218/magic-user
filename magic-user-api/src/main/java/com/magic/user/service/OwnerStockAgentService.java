package com.magic.user.service;

import com.magic.user.entity.Member;
import com.magic.user.entity.OwnerStockAgentMember;
import com.magic.user.enums.AccountType;

import java.util.List;

/**
 * User: joey
 * Date: 2017/5/9
 * Time: 10:42
 */
public interface OwnerStockAgentService {

    /**
     * @param ownerStockAgentMember
     * @Doc 添加业主股东代理用户数映射
     */
    boolean add(OwnerStockAgentMember ownerStockAgentMember);

    /**
     * @Doc 根据所有id获取映射数据
     * @return
     */
    OwnerStockAgentMember findById(OwnerStockAgentMember ownerStockAgentMember);

    /**
     * @Doc 修改业主股东代理用户数映射(注册会员成功后更新业主、股东、代理的会员数)
     * @param member
     * @return
     */
    boolean updateMemNumber(Member member);

    /**
     * @Doc 根据Id(股东Id/业主Id/代理Id)获取映射数据
     * @return
     */
    OwnerStockAgentMember findById(Long id, AccountType type);

    /**
     *@Doc  根据ID列表（股东Id/业主Id/代理Id）获取映射数据
     * @param ids
     * @param type
     * @return
     */
    List<OwnerStockAgentMember> findByIds(List<Long> ids, AccountType type);
}
