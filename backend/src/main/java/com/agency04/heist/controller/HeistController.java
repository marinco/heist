package com.agency04.heist.controller;

import com.agency04.heist.Util;
import com.agency04.heist.dto.*;
import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.enums.RobberStatus;
import com.agency04.heist.event.NewHeistEvent;
import com.agency04.heist.exception.InvalidRequestException;
import com.agency04.heist.model.Heist;
import com.agency04.heist.model.Member;
import com.agency04.heist.model.Skill;
import com.agency04.heist.model.SkillRequirement;
import com.agency04.heist.service.HeistService;
import com.agency04.heist.service.MemberService;
import com.agency04.heist.validator.InsertOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated({Default.class, InsertOnly.class})
@RequestMapping("/heist")
public class HeistController {

    private static final Logger LOG = LoggerFactory.getLogger(HeistController.class);

    @Autowired
    private HeistService heistService;

    @Autowired
    private MemberService memberService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping
    public List<Heist> getAll() {
        return heistService.findAll();
    }

    @GetMapping("/{heistId}")
    public ResponseEntity<?> getOne(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));
        HeistDto heistDto = new HeistDto(heist);
        return ResponseEntity.ok(heistDto);
    }

    @GetMapping("/{heistId}/members")
    public ResponseEntity<?> getMembers(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));

        if (heist.getStatus() == HeistStatus.PLANNING) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        List<HeistMemberDto> heistMemberDtos = heist.getMembers().stream()
                .map(member -> new HeistMemberDto(member.getName(), member.getSkills()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(heistMemberDtos);
    }

    @GetMapping("/{heistId}/skills")
    public ResponseEntity<?> getSkills(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));
        return ResponseEntity.ok(heist.getSkills());
    }

    @GetMapping("/{heistId}/status")
    public ResponseEntity<?> getStatus(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));
        HeistStatusDto heistStatusDto = new HeistStatusDto(heist.getStatus());
        return ResponseEntity.ok(heistStatusDto);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Heist heist) {
        heistService.add(heist);
        LOG.info("Heist {} added", heist);
        eventPublisher.publishEvent(new NewHeistEvent(heist.getId(), heist.getStartTime(), heist.getEndTime()));
        return ResponseEntity.created(URI.create("/heist/" + heist.getId())).build();
    }

    @PatchMapping("/{heistId}/skills")
    public ResponseEntity<?> updateRequiredSkills(@RequestBody @Valid UpdateRequiredSkillsDto updateRequiredSkillsDto, @PathVariable Long heistId) {

        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));

        if (heist.getStatus() == HeistStatus.IN_PROGGRESS || heist.getStatus() == HeistStatus.FINISHED) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        heist.setSkills(updateRequiredSkillsDto.getSkills());
        heistService.update(heist);

        LOG.info("Heist added {}", heist);

        return ResponseEntity.noContent().header(HttpHeaders.CONTENT_LOCATION, "/heist/" + heistId + "/skills").build();
    }

    @GetMapping("/{heistId}/eligible_members")
    public ResponseEntity<?> getEligibleMembers(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));

        if (heist.getStatus() != HeistStatus.PLANNING) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        List<Member> eligibleMembers = new ArrayList<>();

        for (SkillRequirement skillRequirement : heist.getSkills()) {
            for (Member member : memberService.findAll()) {
                if (member.getStatus() != RobberStatus.AVAILABLE && member.getStatus() != RobberStatus.RETIRED) {
                    continue;
                }

                if (eligibleMembers.contains(member)) {
                    continue;
                }

                boolean overlaps = false;
                for (Heist memberHeist : member.getHeists()) {
                    if (Util.overlap(memberHeist, heist)) {
                        overlaps = true;
                        break;
                    }
                }
                if (overlaps) {
                    continue;
                }

                for (Skill skill : member.getSkills()) {
                    if (skill.getName().equals(skillRequirement.getName()) && skill.getLevel().length() >= skillRequirement.getLevel().length()) {
                        eligibleMembers.add(member);
                        break;
                    }
                }

            }
        }

        List<HeistMemberDto> heistMemberDtos = eligibleMembers.stream()
                .map(member -> new HeistMemberDto(member.getName(), member.getSkills()))
                .collect(Collectors.toList());

        EligibleMembersDto eligibleMembersDto = new EligibleMembersDto(heist.getSkills(), heistMemberDtos);

        return ResponseEntity.ok(eligibleMembersDto);
    }

    @PutMapping("/{heistId}/members")
    public ResponseEntity<?> confirmMembers(@RequestBody MembersDto membersDto, @PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));

        if (heist.getStatus() != HeistStatus.PLANNING) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        for (String memberName : membersDto.getMembers()) {
            Member member = memberService.findByName(memberName)
                    .orElseThrow(() -> new InvalidRequestException("Member with name '" + memberName + "' not found"));

            if (member.getStatus() != RobberStatus.AVAILABLE && member.getStatus() != RobberStatus.RETIRED) {
                throw new InvalidRequestException("Only available and retired members can participate in a heist");
            }

            for (Heist memberHeist : member.getHeists()) {
                if (Util.overlap(memberHeist, heist)) {
                    throw new InvalidRequestException("Member '" + memberName + "' has already a heist planned in that period");
                }
            }

            boolean skillRequired = false;
            for (Skill skill : member.getSkills()) {
                for (SkillRequirement skillRequirement : heist.getSkills()) {
                    if (skill.getName().equals(skillRequirement.getName()) && skill.getLevel().length() >= skillRequirement.getLevel().length()) {
                        skillRequired = true;
                        break;
                    }
                }
            }
            if (!skillRequired) {
                throw new InvalidRequestException("Member '" + memberName + "' doesn't require skills");
            }
            heist.getMembers().add(member);
        }

        heistService.confirmMembers(heist);

        return ResponseEntity.noContent().header(HttpHeaders.CONTENT_LOCATION, "/heist/" + heistId + "/skills").build();
    }

    @PutMapping("/{heistId}/start")
    public ResponseEntity<?> start(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));

        if (heist.getStatus() != HeistStatus.READY) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        heistService.start(heist);

        LOG.info("Heist '{}' has started!", heist.getName());

        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/heist/" + heistId + "/status").build();
    }

    @GetMapping("/{heistId}/outcome")
    public ResponseEntity<?> getOutcome(@PathVariable Long heistId) {
        Heist heist = heistService.findById(heistId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist with id '" + heistId + "' not found"));

        if (heist.getStatus() != HeistStatus.FINISHED) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        HeistOutcomeDto heistOutcomeDto = new HeistOutcomeDto(heist.getOutcome());
        return ResponseEntity.ok(heistOutcomeDto);
    }
}
