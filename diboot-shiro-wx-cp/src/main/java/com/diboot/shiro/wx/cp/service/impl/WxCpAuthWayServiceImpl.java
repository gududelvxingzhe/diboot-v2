package com.diboot.shiro.wx.cp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.diboot.core.util.V;
import com.diboot.shiro.config.AuthType;
import com.diboot.shiro.entity.SysUser;
import com.diboot.shiro.jwt.BaseJwtAuthenticationToken;
import com.diboot.shiro.service.AuthWayService;
import com.diboot.shiro.service.SysUserService;
import com.diboot.shiro.wx.cp.entity.WxCpMember;
import com.diboot.shiro.wx.cp.service.WxCpMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 企业微信认证实现
 * @author Wangyl
 * @version v2.0
 * @date 2019/6/10
 */
@Service
public class WxCpAuthWayServiceImpl implements AuthWayService {

    @Autowired
    private WxCpMemberService wxCpMemberService;

    @Autowired
    private SysUserService sysUserService;

    private AuthType authType = AuthType.WX_CP;

    private BaseJwtAuthenticationToken token;

    @Override
    public AuthType authType() {
        return authType;
    }

    @Override
    public void initByToken(BaseJwtAuthenticationToken token) {
        this.token = token;
    }

    @Override
    public SysUser getUser() {
        LambdaQueryWrapper<WxCpMember> queryWrapper = Wrappers.<WxCpMember>lambdaQuery()
                .eq(WxCpMember::getUserId, token.getAccount());

        List<WxCpMember> wxCpMemberList = wxCpMemberService.getEntityList(queryWrapper);
        if (V.isEmpty(wxCpMemberList)){
            return null;
        }
        WxCpMember wxCpMember = wxCpMemberList.get(0);
        SysUser entity = sysUserService.getEntity(wxCpMember.getSysUserId());
        return entity;
    }

    @Override
    public boolean requirePassword() {
        return authType.isRequirePassword();
    }

    @Override
    public boolean isPasswordMatch() {
        return true;
    }

    @Override
    public boolean isPreliminaryVerified() {
        return false;
    }

    @Override
    public Long getExpiresInMinutes() {
        return null;
    }
}
