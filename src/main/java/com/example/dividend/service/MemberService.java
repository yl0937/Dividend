package com.example.dividend.service;

import com.example.dividend.exception.impl.AlreadyExistUserException;
import com.example.dividend.exception.impl.CantfindUserException;
import com.example.dividend.exception.impl.Wrongpwd;
import com.example.dividend.model.Auth;
import com.example.dividend.persist.Repository.MemberRepository;
import com.example.dividend.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }

    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new AlreadyExistUserException();
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.memberRepository.save(member.toEntity());
        return result;
    }

    public MemberEntity authenticate(Auth.SignIn member) {

        var user = this.memberRepository.findByUsername(member.getUsername()).orElseThrow(()-> new CantfindUserException());
        if(this.passwordEncoder.matches(member.getPassword(),user.getPassword())){
            throw new Wrongpwd();

        }

        return user;
    }
}