package com.fk.visitor.api.service;

import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.entity.OperatorRole;
import com.fk.visitor.api.repository.OperatorRepository;
import com.fk.visitor.api.repository.SysRoleRepository;
import cn.kinkii.novice.security.model.KAccount;
import cn.kinkii.novice.security.model.KUrlAuthority;
import cn.kinkii.novice.security.service.KAccountNotFoundException;
import cn.kinkii.novice.security.service.KAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Slf4j
@Service
public class OperatorAuthService implements KAccountService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public KAccount loadKAccountByUsername(String username) throws KAccountNotFoundException {
        Operator operator = operatorRepository.findByUsername(username);
        if (operator == null) {
            operator = operatorRepository.findByMobile(username);
        }
        if (operator == null) {
            operator = operatorRepository.findByEmail(username);
        }
        if (operator == null) {
            throw new KAccountNotFoundException("账号或密码错误");
        }
        OperatorAccount account = new OperatorAccount(operator.getId(), operator.getUsername(), operator.getPassword(), operator);
        if (operator.getRoles() != null && operator.getRoles().size() > 0) {
            Collection<String> roleNames = new HashSet<>();
            Collection<KUrlAuthority> authorities = new HashSet<>();
            for (OperatorRole role : operator.getRoles()) {
                roleNames.add(role.getKeyCode());
                if (role.getIsSupervisor()) {
                    account.setSupervisor(true);
                }
                //注意当前项目KUrlAuthority中的Method统一设置为'*' 即允许所有方法
                sysRoleRepository.findById(role.getKeyCode())
                        .ifPresent(sr -> sr.getPermissions()
                                .forEach(p -> authorities.add(new KUrlAuthority(p.getUrl(), p.getMethod()))));
            }
            account.setRoleNames(roleNames);
            account.setAuthorities(authorities);
        }
        return account;
    }
}
