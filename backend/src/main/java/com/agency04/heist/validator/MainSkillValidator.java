package com.agency04.heist.validator;

import com.agency04.heist.model.Member;
import com.agency04.heist.model.Skill;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MainSkillValidator implements ConstraintValidator<MainSkillValid, Member> {

    @Override
    public void initialize(MainSkillValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Member member, ConstraintValidatorContext constraintValidatorContext) {
        if (member.getMainSkill() == null) {
            return true;
        }
        for (Skill skill : member.getSkills()) {
            if (skill.getName().equals(member.getMainSkill())) {
                return true;
            }
        }
        return false;
    }
}
