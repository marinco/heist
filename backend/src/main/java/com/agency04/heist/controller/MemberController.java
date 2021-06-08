package com.agency04.heist.controller;

import com.agency04.heist.dto.SkillsDto;
import com.agency04.heist.model.Member;
import com.agency04.heist.model.Skill;
import com.agency04.heist.service.MemberService;
import com.agency04.heist.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/member")
public class MemberController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping
    public List<Member> getAll() {
        return memberService.findAll();
    }

    @GetMapping("/{memberId}")
    public Member getOne(@PathVariable Long memberId) {
        return memberService.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist member with id '" + memberId + "' not found"));
    }

    @GetMapping("/{memberId}/skills")
    public ResponseEntity<?> getSkills(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist member with id '" + memberId + "' not found"));

        SkillsDto skillsDto=new SkillsDto(member.getSkills(), member.getMainSkill());
        return ResponseEntity.ok(skillsDto);
    }
    @GetMapping("/{memberId}/heist")
    public ResponseEntity<?> getHeists(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist member with id '" + memberId + "' not found"));
        return ResponseEntity.ok(member.getHeists());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Member member) {
        memberService.add(member);
        LOG.info("Member added {}", member);
        return ResponseEntity.created(URI.create("/member/" + member.getId())).build();
    }

    @PutMapping("/{memberId}/skills")
    public ResponseEntity<?> updateSkills(@RequestBody @Valid SkillsDto skillsDto, @PathVariable Long memberId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist member with id '" + memberId + "' not found"));

        member.setSkills(skillsDto.getSkills());
        member.setMainSkill(skillsDto.getMainSkill());

        memberService.update(member);

        return ResponseEntity.noContent().header(HttpHeaders.CONTENT_LOCATION,"/member/" + memberId + "/skills").build();
    }

    @DeleteMapping("/{memberId}/skills/{skillName}")
    public ResponseEntity<?> removeSkill(@PathVariable Long memberId, @PathVariable String skillName) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Heist member with id '" + memberId + "' not found"));

        for (Skill skill : member.getSkills()) {
            if (skill.getName().equals(skillName)) {
                LOG.debug("member before delete skill {}", member);
                member.getSkills().remove(skill);
                if (skill.getName().equals(member.getMainSkill())) {
                    member.setMainSkill(null);
                }
                LOG.debug("member skill delete is {}", member);
                memberService.update(member);
                return ResponseEntity.noContent().build();
            }
        }
        LOG.warn("Member {} does not have skill {}", member.getName(), skillName);
        throw new ResourceNotFoundException("Skill '" + skillName + "' does not exist");
    }
}
