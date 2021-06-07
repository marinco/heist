package com.agency04.heist.service;

import com.agency04.heist.enums.HeistOutcome;
import com.agency04.heist.enums.RobberStatus;
import com.agency04.heist.event.SendEmailEvent;
import com.agency04.heist.model.Heist;
import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.model.Member;
import com.agency04.heist.repository.HeistRepository;
import com.agency04.heist.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class HeistServiceImpl implements HeistService {

    private static final Logger LOG = LoggerFactory.getLogger(HeistServiceImpl.class);

    @Autowired
    private HeistRepository heistRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    public List<Heist> findAll() {
        return heistRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Heist> findById(Long id) {
        return heistRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Heist> findByName(String name) {
        return heistRepository.findByName(name);
    }

    @Override
    public Heist add(Heist heist) {
        return heistRepository.save(heist);
    }

    @Override
    public Heist update(Heist heist) {
        return heistRepository.save(heist);
    }

    @Override
    public Heist start(Heist heist) {
        for (Member member : heist.getMembers()) {
            String subject = messageSource.getMessage("email.heistStarted.subject", null, Locale.getDefault());
            String message = messageSource.getMessage("email.heistStarted.message",
                    new Object[]{member.getName(), heist.getName()}, Locale.getDefault());

            eventPublisher.publishEvent(new SendEmailEvent(member.getEmail(), subject, message));
        }
        heist.setStatus(HeistStatus.IN_PROGGRESS);
        return heistRepository.save(heist);
    }

    @Override
    public Heist finish(Heist heist) {
        int expiredMembers = 0;
        int incarceratedMembers = 0;

        Random random = new Random();

        for (Member member : heist.getMembers()) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    member.setStatus(RobberStatus.EXPIRED);
                    expiredMembers++;
                } else {
                    member.setStatus(RobberStatus.INCARCERATED);
                    incarceratedMembers++;
                }
            }
            memberRepository.save(member);

            String subject = messageSource.getMessage("email.heistFinished.subject", null, Locale.getDefault());
            String message = messageSource.getMessage("email.heistFinished.message",
                    new Object[]{member.getName(), heist.getName(), heist.getStartTime(), heist.getEndTime()}, Locale.getDefault());

            eventPublisher.publishEvent(new SendEmailEvent(member.getEmail(), subject, message));

        }

        if ((expiredMembers + incarceratedMembers) * 2 > heist.getMembers().size()) {
            heist.setOutcome(HeistOutcome.FAILED);
        } else {
            heist.setOutcome(HeistOutcome.SUCCEDED);
        }

        LOG.debug("expired members {}, incarcerated members {}, outcome {}", expiredMembers, incarceratedMembers, heist.getOutcome());

        heist.setStatus(HeistStatus.FINISHED);
        return heistRepository.save(heist);
    }

    @Override
    public Heist confirmMembers(Heist heist) {
        for (Member member : heist.getMembers()) {
            String subject = messageSource.getMessage("email.memberConfirmed.subject", null, Locale.getDefault());
            String message = messageSource.getMessage("email.memberConfirmed.message",
                    new Object[]{member.getName(), heist.getName(), heist.getStartTime(), heist.getEndTime()}, Locale.getDefault());

            eventPublisher.publishEvent(new SendEmailEvent(member.getEmail(), subject, message));
        }
        heist.setStatus(HeistStatus.READY);
        return heistRepository.save(heist);
    }
}
