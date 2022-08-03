package com.ldu.spring_blogcrud.security;

import com.ldu.spring_blogcrud.entity.User;
import com.ldu.spring_blogcrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        System.out.println("조회 전");
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nickname을 찾을 수 없습니다."));

        return new UserDetailsImpl(user.getNickname(), user.getPassword());
    }
}
