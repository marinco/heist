package com.agency04.heist.service;

import com.agency04.heist.event.SendEmailEvent;
import com.agency04.heist.model.Member;
import com.agency04.heist.repository.MemberRepository;
import com.agency04.heist.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member add(Member member) {
        memberRepository.save(member);
        String subject = messageSource.getMessage("email.memberAdded.subject", null, Locale.getDefault());
        String message = messageSource.getMessage("email.memberAdded.message",
                new Object[]{member.getName()}, Locale.getDefault());

        eventPublisher.publishEvent(new SendEmailEvent(member.getEmail(), subject, message));

        return member;
    }

    @Override
    public Member update(Member member) {
        return memberRepository.save(member);
    }
}
