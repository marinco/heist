package com.agency04.heist.validator;

import com.agency04.heist.model.Member;
import com.agency04.heist.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MemberUniqueValidator implements ConstraintValidator<Unique, Member> {

    @Autowired
    private MemberService memberService;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Member member, ConstraintValidatorContext context) {
        if (memberService != null && memberService.findByEmail(member.getEmail()).isPresent()) {
            return false;
        }
        return true;
    }
}