package com.agency04.heist.service;

import com.agency04.heist.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> findAll();

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    Member add(Member member);

    Member update(Member member);
}
