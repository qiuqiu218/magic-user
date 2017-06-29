package com.magic.user.dao;

import com.alibaba.fastjson.JSON;
import com.magic.api.commons.ApiLogger;
import com.magic.api.commons.utils.StringUtils;
import com.magic.user.bean.Account;
import com.magic.user.bean.MemberCondition;
import com.magic.user.enums.AccountType;
import com.magic.user.vo.MemberConditionVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: joey
 * Date: 2017/5/17
 * Time: 15:09
 */
@Component
public class MemberMongoDaoImpl extends BaseMongoDAOImpl<MemberConditionVo> {


    /**
     * @param memberId
     * @param status
     * @return
     * @Doc 修改会员可用状态
     */
    public boolean updateMemberStatus(Long memberId, Integer status) {
        Query query = new Query(new Criteria("memberId").is(memberId));
        Update update = new Update().set("status", status);
        return super.update(query, update) != null;
    }

    /**
     * @param memberId
     * @return
     * @Doc 获取会员详情
     */
    public MemberConditionVo get(Long memberId) {
        Query query = new Query(new Criteria("memberId").is(memberId));
        return super.findOne(query);
    }

    /**
     * @param memberCondition
     * @param page
     * @param count
     * @return
     * @Doc 分页查询代理信息
     */
    public List<MemberConditionVo> queryByPage(MemberCondition memberCondition, Integer page, Integer count) {
        if (memberCondition != null) {
            Query query = assembleQuery(memberCondition);
            if (page != null && count != null) {
                query.skip((page - 1) * count);
                query.limit(count);
                query.with(new Sort(Sort.Direction.DESC, "registerTime"));
            }
            List<MemberConditionVo> result = super.find(query);
            ApiLogger.info(String.format("get data from mongo. query: %s, result: %s", JSON.toJSONString(query), JSON.toJSONString(result)));
            return result;
        }
        return null;
    }

    /**
     * @param memberCondition
     * @return
     * @Doc 获取会员总数
     */
    public long getCount(MemberCondition memberCondition) {
        if (memberCondition != null) {
            Query query = assembleQuery(memberCondition);
            return count(query);
        }
        return 0;
    }

    /**
     * @param memberCondition
     * @return
     * @Doc 组装查询会员数的条件
     */
    public Query assembleQuery(MemberCondition memberCondition) {
        Query query = new Query();
        if (memberCondition != null) {
            query.addCriteria(new Criteria("ownerId").is(memberCondition.getOwnerId()));
            if (memberCondition.getRegister() != null) {
                Long start = memberCondition.getRegister().getStart();
                Long end = memberCondition.getRegister().getEnd();
                if (start !=null && end != null) {
                    query.addCriteria(new Criteria("registerTime").gte(start).lte(end));
                } else if (start != null) {
                    query.addCriteria(new Criteria("registerTime").gte(start));
                } else if (end != null) {
                    query.addCriteria(new Criteria("registerTime").lte(end));
                }
            }
            if (memberCondition.getDepositMoney() != null) {
                Long min = memberCondition.getDepositMoney().getMin();
                Long max = memberCondition.getDepositMoney().getMax();
                if (min != null && max != null) {
                    query.addCriteria(new Criteria("depositMoney").gte(min).lte(max));
                } else if (min != null) {
                    query.addCriteria(new Criteria("depositMoney").gte(min));
                } else if (max != null) {
                    query.addCriteria(new Criteria("depositMoney").lte(max));
                }
            }
            if (memberCondition.getWithdrawMoney() != null) {
                Long min = memberCondition.getWithdrawMoney().getMin();
                Long max = memberCondition.getWithdrawMoney().getMax();
                if (min != null && max != null) {
                    query.addCriteria(new Criteria("withdrawMoney").gte(min).lte(max));
                }else if (min !=null) {
                    query.addCriteria(new Criteria("withdrawMoney").gte(min));
                } else if (max != null) {
                    query.addCriteria(new Criteria("withdrawMoney").lte(max));
                }
            }
            if (memberCondition.getStatus() != null ) {
                query.addCriteria(new Criteria("status").is(memberCondition.getStatus()));
            }
            if (memberCondition.getLevel() != null) {
                query.addCriteria(new Criteria("level").is(memberCondition.getLevel()));
            }

            if (memberCondition.getDepositNumber() != null) {
                Long min = memberCondition.getDepositNumber().getMin();
                Long max = memberCondition.getDepositNumber().getMax();
                if (min != null && max != null) {
                    query.addCriteria(new Criteria("depositCount").gte(min).lte(max));
                } else if (min != null) {
                    query.addCriteria(new Criteria("depositCount").gte(min));
                } else if (max != null) {
                    query.addCriteria(new Criteria("depositCount").lte(max));
                }
            }
            if (memberCondition.getWithdrawNumber() != null) {
                Long min = memberCondition.getWithdrawNumber().getMin();
                Long max = memberCondition.getWithdrawNumber().getMax();
                if (min != null && max != null) {
                    query.addCriteria(new Criteria("withdrawCount").gte(min).lte(max));
                } else if (min != null) {
                    query.addCriteria(new Criteria("withdrawCount").gte(min));
                } else if (max != null) {
                    query.addCriteria(new Criteria("withdrawCount").lte(max));
                }
            }
            Account account = memberCondition.getAccount();
            if (account != null && StringUtils.isNotBlank(account.getName())) {
                if (AccountType.parse(account.getType()) == AccountType.agent) {
                    query.addCriteria(new Criteria("agentName").is(account.getName()));
                }
                if (AccountType.parse(account.getType()) == AccountType.member) {
                    query.addCriteria(new Criteria("memberName").is(account.getName()));
                }
            }

        }
        return query;
    }

    public boolean updateLevel(long memberId, long level) {
        Query query = new Query(new Criteria("memberId").is(memberId));
        Update update = new Update().set("level", level);
        return super.update(query, update) != null;
    }
}
