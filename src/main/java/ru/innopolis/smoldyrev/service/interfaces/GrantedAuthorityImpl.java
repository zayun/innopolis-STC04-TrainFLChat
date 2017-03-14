package ru.innopolis.smoldyrev.service.interfaces;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by smoldyrev on 13.03.17.
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String roleUser;

    public GrantedAuthorityImpl(String roleUser) {
        this.roleUser = roleUser;
    }

    @Override
    public String getAuthority() {
        return roleUser;
    }
}
