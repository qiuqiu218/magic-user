package com.magic.user.member.resource.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.magic.api.commons.core.context.RequestContext;
import com.magic.api.commons.model.PageBean;
import com.magic.api.commons.mq.Producer;
import com.magic.api.commons.mq.api.Topic;
import com.magic.api.commons.tools.CommonDateParseUtil;
import com.magic.user.constants.UserContants;
import com.magic.user.entity.AccountOperHistory;
import com.magic.user.entity.Member;
import com.magic.user.entity.User;
import com.magic.user.enums.AccountType;
import com.magic.user.exception.UserException;
import com.magic.user.po.DownLoadFile;
import com.magic.user.service.*;
import com.magic.user.vo.AccountModifyInfoVo;
import com.magic.user.vo.AccountModifyListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * InfoResourceServiceImpl
 *
 * @author zj
 * @date 2017/5/9
 */
@Service("infoResourceService")
public class InfoResourceServiceImpl {

    @Resource
    private MemberService memberService;

    @Resource
    private UserService userService;

//    @Resource
    private AccountIdMappingService accountIdMappingService;

    @Resource
    private LoginService loginService;

//    @Resource
    private AccountOperHistoryService accountOperHistoryService;

//    @Resource
    private Producer producer;

    private static HashSet<AccountType> sets = new HashSet<>();
    static {
        sets.add(AccountType.member);//会员
        sets.add(AccountType.agent);//代理
        sets.add(AccountType.stockholder);//股东
    }

    /**
     * 资料查询
     * @param rc RequestContext
     * @param type 账号类型
     * @param account
     * @return
     */
    public String infoDetail(RequestContext rc, Integer type, String account) {
        if (!checkParams(type, account)){
            throw UserException.ILLEGAL_PARAMETERS;
        }
        long uid = rc.getUid();
        User user = userService.getUserById(uid);
        if (user == null){
            throw UserException.ILLEGAL_USER;
        }
        long ownerId = user.getOwnerId();//业主ID
        long memberId = getMemberId(type, ownerId, account);
        if (memberId <=0){
            throw UserException.ILLEGAL_MEMBER;
        }
        AccountModifyInfoVo modifyInfoVo = getModifyInfo(type, memberId);
        return JSON.toJSONString(modifyInfoVo);
    }

    /**
     * 获取modifyinfo
     * @param type
     * @param memberId
     * @return
     */
    private AccountModifyInfoVo getModifyInfo(Integer type, long memberId) {
        AccountType accountType = AccountType.parse(type);
        if (accountType == AccountType.member) {
            Member member = memberService.getMemberById(memberId);
            if (member == null) {
                throw UserException.ILLEGAL_MEMBER;
            }
            return assembleModifyInfo(member);
        }
        User user = userService.getUserById(memberId);
        if (user == null){
            throw UserException.ILLEGAL_USER;
        }
        return assembleModifyInfo(user);

    }

    /**
     * 组装修改数据
     *
     * @param user
     * @return
     */
    private AccountModifyInfoVo assembleModifyInfo(User user) {
        AccountModifyInfoVo result = new AccountModifyInfoVo();
        result.setId(user.getUserId());
        result.setAccount(user.getUsername());
        result.setRealname(user.getRealname());
        result.setBankCardNo(user.getBankCardNo());
        result.setTelephone(user.getTelephone());
        result.setEmail(user.getEmail());
        return result;
    }

    /**
     * 组装修改数据
     *
     * @param member
     * @return
     */
    private AccountModifyInfoVo assembleModifyInfo(Member member) {
        AccountModifyInfoVo result = new AccountModifyInfoVo();
        result.setId(member.getId());
        result.setAccount(member.getUsername());
        result.setRealname(member.getRealname());
        result.setBankCardNo(member.getBankCardNo());
        result.setBank(member.getBank());
        result.setBankDeposit(member.getBankDeposit());
        result.setTelephone(member.getTelephone());
        result.setEmail(member.getEmail());
        return result;
    }

    /**
     *
     * 获取会员ID 账号系统
     *
     * @param ownerId
     * @param account
     * @return
     */
    private long getMemberId(int type, long ownerId, String account) {
        AccountType accountType = AccountType.parse(type);
        long uid = 0;
        if (accountType == AccountType.member){
            //TODO passport获取会员ID
        }
        uid = accountIdMappingService.getUid(ownerId, account);
        return uid;
    }

    /**
     * 检查参数合法性
     * @param type
     * @param account
     * @return
     */
    private boolean checkParams(Integer type, String account) {
        if (type == null || account == null || account.length() <= 0){
            return false;
        }
        AccountType accountType = AccountType.parse(type);
        if (accountType == null){
            return false;
        }
        if (!sets.contains(accountType)){
            return false;
        }
        return true;
    }

    /**
     * 资料修改
     * @param rc RequestContext
     * @param id 账号ID
     * @param type 账号类型
     * @param realname 姓名
     * @param telephone 手机号
     * @param email 邮箱
     * @param bankCardNo 银行卡号
     * @param bank 银行名称
     * @param bankDeposit 开户行
     * @param loginPassword 登陆密码
     * @param paymentPassword 支付密码
     * @return
     */
    public String modifyInfo(RequestContext rc, Long id, Integer type, String realname, String telephone, String email, String bankCardNo, String bank, String bankDeposit, String loginPassword, String paymentPassword) {
        if (!checkParams(id, type, realname, telephone, email, bankCardNo, bank, bankDeposit, loginPassword, paymentPassword)){
            throw UserException.ILLEGAL_PARAMETERS;
        }
        long uid = rc.getUid();
        User operator = userService.getUserById(uid);//操作人
        if (operator == null){
            throw UserException.ILLEGAL_USER;
        }
        Map<String, Object> newMap = new HashMap<>();//修改后数据
        Map<String, Object> oldMap = new HashMap<>();//修改前数据
        AccountType accountType = AccountType.parse(type);
        if (accountType == AccountType.member){//账号
            Member member = memberService.getMemberById(id);
            if (member == null){
                throw UserException.ILLEGAL_MEMBER;
            }
            boolean result = memberService.updateMember(id, realname, telephone, email, bankCardNo, bank, bankDeposit);
            if (result){
                if (realname != null){
                    newMap.put("realname", realname);
                    oldMap.put("realname", member.getRealname());
                }
                if (telephone != null){
                    newMap.put("telephone", telephone);
                    oldMap.put("telephone", member.getTelephone());
                }
                if (email != null){
                    newMap.put("email", email);
                    oldMap.put("email", member.getEmail());
                }
                if (bankCardNo != null){
                    newMap.put("bankCardNo", bankCardNo);
                    oldMap.put("bankCardNo", member.getBankCardNo());
                }
                if (bank != null){
                    newMap.put("bank", bank);
                    oldMap.put("bank", member.getBank());
                }
                if (bankDeposit != null){
                    newMap.put("bankDeposit", bankDeposit);
                    newMap.put("bankDeposit", member.getBankDeposit());
                }
            }
            if (loginPassword != null){
                //todo passport修改密码
                //todo 如果成功修改，则添加
                newMap.put("loginPassword", "password reset");
            }
            if (paymentPassword != null){
                //todo jason
                //todo 如果修改成功，则添加
                newMap.put("paymentPassword", "password reset");
            }
            //todo 组织map
        }else {//代理或股东
            User user = userService.getUserById(id);
            if (user == null){
                throw UserException.ILLEGAL_USER;
            }
            //用户数据更新
            boolean result = userService.updateUser(id, realname, telephone, email, bankCardNo, bank, bankDeposit);
            if (result){
                if (realname != null){
                    newMap.put("realname", realname);
                    oldMap.put("realname", user.getRealname());
                }
                if (telephone != null){
                    newMap.put("telephone", telephone);
                    oldMap.put("telephone", user.getTelephone());
                }
                if (email != null){
                    newMap.put("email", email);
                    oldMap.put("email", user.getEmail());
                }
                if (bankCardNo != null){
                    newMap.put("bankCardNo", bankCardNo);
                    oldMap.put("bankCardNo", user.getBankCardNo());
                }
            }
            if (loginPassword != null){
                result = loginService.resetPassword(id, loginPassword);
                if (result) {
                    newMap.put("loginPassword", "password reset");
                }
            }
            //todo 组织map
        }
        if (newMap.size() > 0){
            JSONObject object = new JSONObject();
            object.put("after", newMap);
            object.put("before", oldMap);
            object.put("operator", operator);
            //todo 修改topic,并增加消费者
            producer.send(Topic.USER_LOGIN_SUCCESS, String.valueOf(uid), object.toJSONString());
        }
        return UserContants.EMPTY_STRING;
    }

    /**
     * 参数检查
     * @param id
     * @param type
     * @param realname
     * @param telephone
     * @param email
     * @param bankCardNo
     * @param bank
     * @param bankDeposit
     * @param loginPassword
     * @param paymentPassword
     * @return
     */
    private boolean checkParams(Long id, Integer type, String realname, String telephone, String email, String bankCardNo, String bank, String bankDeposit, String loginPassword, String paymentPassword) {
        if (id == null || id <= 0){
            return false;
        }
        if (type == null || type <= 0){
            return false;
        }
        AccountType accountType = AccountType.parse(type);
        if (accountType == null){
            return false;
        }
        if (!sets.contains(accountType)){
            return false;
        }
        if (realname != null && realname.length() == 0){
            return false;
        }
        if (telephone != null && telephone.length() == 0){
            return false;
        }
        if (email != null && email.length() == 0){
            return false;
        }
        if (bankCardNo != null && bankCardNo.length() == 0){
            return false;
        }
        if (bank != null && bank.length() == 0){
            return false;
        }
        if (bankDeposit != null && bankDeposit.length() == 0){
            return false;
        }
        if (loginPassword != null && loginPassword.length() == 0){
            return false;
        }
        if (paymentPassword != null && paymentPassword.length() == 0){
            return false;
        }
        if (realname == null && telephone == null && email == null && bankCardNo == null
                && bank == null && bankDeposit == null && loginPassword == null && paymentPassword == null){
            return false;
        }
        return true;

    }


    /**
     * 修改记录列表
     *
     *
     * @param rc
     * @param type
     * @param account
     * @param page
     * @param count
     * @return
     */
    public String modifyList(RequestContext rc, Integer type, String account, int page, int count) {
        long uid = rc.getUid();
        long total = accountOperHistoryService.getCount(type, account, uid);
        if (total <= 0){
            return JSON.toJSONString(assemblePageBean(page, count, 0, null));
        }
        List<AccountOperHistory> list = accountOperHistoryService.getList(type, account, uid, page, count);
        List<AccountModifyListVo> modifyListVos = assembleModifyList(list);
        return JSON.toJSONString(assemblePageBean(page, count, total, modifyListVos));
    }

    /**
     * 组装数据
     * @param list
     * @return
     */
    private List<AccountModifyListVo> assembleModifyList(List<AccountOperHistory> list) {
        List<AccountModifyListVo> result = new ArrayList<>();
        Iterator<AccountOperHistory> iterator = list.iterator();
        while (iterator.hasNext()){
            AccountOperHistory next = iterator.next();
            if (next != null){
                AccountModifyListVo accountModifyListVo = new AccountModifyListVo();
                accountModifyListVo.setAccount(next.getUsername());
                accountModifyListVo.setAccountId(next.getUserId());
                AccountType type = next.getType();
                accountModifyListVo.setType(type.value());
                accountModifyListVo.setShowType(AccountType.getDesc(type));
                //todo 新增业主ID 和 业主名称
                accountModifyListVo.setOwnerId(next.getOwnerId());
                accountModifyListVo.setOwnerName(next.getOwnerName());
                accountModifyListVo.setBefore(JSONObject.parseObject(next.getBeforeInfo()));
                accountModifyListVo.setAfter(JSONObject.parseObject(next.getAfterInfo()));
                accountModifyListVo.setOperatorId(next.getProcUserId());
                accountModifyListVo.setOperatorName(next.getProcUsername());
                accountModifyListVo.setOperatorTime(CommonDateParseUtil.date2string(next.getCreateTime(), CommonDateParseUtil.YYYY_MM_DD_HH_MM_SS));
                result.add(accountModifyListVo);
            }
        }
        return result;
    }

    /**
     * 组装翻页数据
     *
     * @param page 页码
     * @param count 当页条数
     * @param total 总条数
     * @param list 详细列表数据
     * @return
     */
    private static PageBean<AccountModifyListVo> assemblePageBean(int page, int count, long total, Collection<AccountModifyListVo> list){
        PageBean<AccountModifyListVo> result = new PageBean<>();
        result.setPage(page);
        result.setCount(count);
        result.setTotal(total);
        result.setList(list);
        return result;
    }

    /**
     * 资料修改记录导出
     *
     * @param rc RequestContext
     * @param type 账号类型
     * @param account 账号名
     * @return
     */
    public DownLoadFile modifyListExport(RequestContext rc, Integer type, String account) {
        long uid = rc.getUid(); //业主ID、股东或代理ID
        String filename = assembleFileName(uid, "资料修改记录");
        DownLoadFile downLoadFile = new DownLoadFile();
        downLoadFile.setFilename(filename);
        //TODO 查询表数据，生成excel的zip，并返回excel byte[]
        List<AccountOperHistory> list = accountOperHistoryService.getList(type, account, uid);
        byte[] content = new byte[5];
        downLoadFile.setContent(content);
        return downLoadFile;
    }

    /**
     * 组装文件名
     *
     * @param uid
     * @param name
     * @return
     */
    private String assembleFileName(long uid, String name) {
        StringBuilder filename = new StringBuilder();
        filename.append(uid);
        filename.append("-");
        filename.append(name);
        filename.append(System.currentTimeMillis());
        filename.append(".xlsx");
        return filename.toString();
    }
}
