package com.g96.ftms.security;

import com.g96.ftms.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CustomUserDetails implements UserDetails {

    private Long userId;
    private String account;
    private String fullName;
    private String email;
    private String phone;
    private String emergencyPhone;
    private String address;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public static CustomUserDetails mapUserToUserDetails(User user) {
        // Sử dụng Builder để tạo đối tượng CustomUserDetails
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .emergencyPhone(user.getEmergencyPhone())
                .address(user.getAddress())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
