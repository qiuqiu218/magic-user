package com.magic.user.service;

import com.magic.api.commons.model.Page;
import com.magic.user.entity.User;
import com.magic.user.vo.UserCondition;

import java.util.List;
import java.util.Map;

/**
 * User: joey
 * Date: 2017/5/5
 * Time: 23:21
 */
public interface UserService {

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    User get(long id);

    /**
     * @return
     * @Doc 获取所有股东
     */
    List<Map<String, Object>> findAllStock();

    /**
     * @param id
     * @return
     * @Doc 获取股东详情
     */
    Map<String, Object> getStockDetail(long id);

    /**
     * @param id
     * @param pwd
     * @return
     * @Doc 修改密码
     */
    int updatePwd(long id, String pwd);

    /**
     * @param user
     * @return
     * @Doc 修改用户信息
     */
    int update(User user);

    /**
     * @param user
     * @return
     * @Doc 添加股东
     */
    Long addStock(User user);

    /**
     * @param id
     * @param status
     * @return
     * @Doc 启用停用账号
     */
    int disable(long id, int status);


    /**
     * @param userCondition
     * @return
     * @Doc 分页查询代理信息
     */
    List<Map<String, Object>> findAgentByPage(UserCondition userCondition);

    /**
     * @param userCondition
     * @return
     * @Doc 获取代理条数
     */
    long getAgentCount(UserCondition userCondition);

    /**
     * 添加代理
     *
     * @param user
     * @return
     */
    long addAgent(User user);

    /**
     * @param id
     * @return
     * @Doc 根据股东id获取业主id
     */
    long getOwnerIdByStock(long id);

    /**
     * 根据推广码查询代理数据
     *
     * @param proCode
     * @return
     */
    User getUserByCode(String proCode);

    /**
     * @param id
     * @return
     * @Doc 查询代理详情
     */
    Map<String, Object> getAgentDetail(long id);
}
