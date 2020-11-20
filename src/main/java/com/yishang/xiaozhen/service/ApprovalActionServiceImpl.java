package com.yishang.xiaozhen.service;

import com.yishang.xiaozhen.entity.ApprovalAction;
import com.yishang.xiaozhen.mapper.ApprovalActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 审批日志表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class ApprovalActionServiceImpl{

    @Autowired
    private ApprovalActionMapper approvalActionMapper;

    public Integer insert(ApprovalAction object) {
        int insert = approvalActionMapper.insert(object);
        return insert;
    }


    public Map<String,Object> list(Object object) {
        return null;
    }


    public Object detail(String id) {
        return null;
    }

    public Integer delete(String id) {
        return null;
    }


    public Integer update(Object object) {
        return null;
    }
}
