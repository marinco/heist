package com.agency04.heist;

import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.enums.RobberStatus;
import com.agency04.heist.enums.Sex;
import com.agency04.heist.event.NewHeistEvent;
import com.agency04.heist.event.SendEmailEvent;
import com.agency04.heist.model.*;
import com.agency04.heist.service.HeistService;
import com.agency04.heist.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class DbInit implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DbInit.class);

    @Autowired
    private HeistService heistService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Override
    public void run(String... args) throws Exception {
        LOG.debug("debug mode on!!");

        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("combat", "*******"));
        Member member = new Member("Lumbarda", Sex.M, "marin.milina96@gmail.com", skills, "combat", RobberStatus.AVAILABLE);

        memberService.add(member);
        LOG.debug("Member is {}", member);


        Heist heist = new Heist();
        heist.setName("fabrika od solada");
        heist.setLocation("negdje");
        heist.setStartTime(Instant.now().plus(5, ChronoUnit.SECONDS));
        heist.setEndTime(Instant.now().plus(10, ChronoUnit.SECONDS));
        heist.setStatus(HeistStatus.READY);

        List<SkillRequirement> skillRequirements=List.of(new SkillRequirement("combat", "*", 1));
        heist.setSkills(skillRequirements);

        heist.getMembers().add(member);

        heistService.add(heist);

        LOG.debug("Heist created is {}", heist);

        eventPublisher.publishEvent(new NewHeistEvent(heist.getId(), heist.getStartTime(), heist.getEndTime()));

        String subject = messageSource.getMessage("email.memberAdded.subject", null, Locale.getDefault());
        String message = messageSource.getMessage("email.memberAdded.message",
                new Object[]{member.getName()}, Locale.getDefault());


        eventPublisher.publishEvent(new SendEmailEvent(member.getEmail(), subject, message));
    }
}
