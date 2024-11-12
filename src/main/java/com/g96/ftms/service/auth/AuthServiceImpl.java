
package com.g96.ftms.service.auth;

import com.g96.ftms.dto.JwtResponeDTO;
import com.g96.ftms.dto.LoginDTO;
import com.g96.ftms.entity.Role;
import com.g96.ftms.entity.User;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.UserRepository;
import com.g96.ftms.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JwtResponeDTO authenticateUser(LoginDTO loginDTO) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getAccount(), loginDTO.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByAccount(loginDTO.getAccount());
            if (user == null) {
                throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_CREDENTIALS);
            }

            // Check if user is active
            if (!user.getStatus()) {
                throw new AppException(HttpStatus.FORBIDDEN, ErrorCode.ACCOUNT_INACTIVE);
            }
            Set<String> roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
            String token = jwtTokenProvider.generateToken(user.getAccount(), roles);
            String refreshToken = jwtTokenProvider.generateRefreshToken();

            Long expire = jwtTokenProvider.getExpirationDateFromToken(token);

            return new JwtResponeDTO(token, refreshToken, userDetails.getUsername(), user.getRoleNames(), expire, roles);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_CREDENTIALS);
        }
    }
}
