package com.agency04.heist.scheduler;

import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.model.*;
import com.agency04.heist.service.HeistService;
import com.agency04.heist.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskSchedulerRunner implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TaskSchedulerRunner.class);

    @Autowired
    private HeistService heistService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Value("${skill.level.min}")
    private int min;

    @Value("${skill.level.max}")
    private int max;

    @Value("${levelUpTime}")
    private long levelUpTime;

    @Value("*")
    private String skillLevel;

    @Override
    public void run(String... args) throws Exception {

        scheduler.scheduleWithFixedDelay(() -> {
            LOG.debug("Increasing skill level..., uptime is {}", levelUpTime);

            List<Heist> activeHeists = heistService.findAll().stream()
                    .filter(heist -> (heist.getStatus() == HeistStatus.IN_PROGGRESS
                            && Duration.between(Instant.now().minusSeconds(levelUpTime), heist.getStartTime()).isNegative()))
                    .collect(Collectors.toList());

            for (Heist heist : activeHeists) {
                LOG.debug("Increasing skills for heist {}", heist);
                for (Member member : heist.getMembers()) {
                    boolean memberChanged=false;
                    for (Skill skill : member.getSkills()) {
                        if (skill.getLevel().length() < max) {
                            for (SkillRequirement skillRequirement : heist.getSkills()) {
                                if (skillRequirement.getName().equals(skill.getName())) {
                                    LOG.debug("Increasing skill {}", skill);
                                    skill.setLevel(skill.getLevel() + skillLevel);
                                    memberChanged=true;
                                }
                            }
                        }
                    }
                    if (memberChanged){
                        LOG.debug("Updating member {}", member);
                        memberService.update(member);
                    }
                }
            }
        }, levelUpTime * 1000L);
    }
}
