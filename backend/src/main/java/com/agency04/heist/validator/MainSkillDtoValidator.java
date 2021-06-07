package com.agency04.heist.validator;

import com.agency04.heist.dto.SkillsDto;
import com.agency04.heist.model.Skill;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MainSkillDtoValidator implements ConstraintValidator<MainSkillValid, SkillsDto> {

    @Override
    public void initialize(MainSkillValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SkillsDto skillsDto, ConstraintValidatorContext constraintValidatorContext) {
        if (skillsDto.getMainSkill()==null){
            return true;
        }
        for (Skill skill : skillsDto.getSkills()) {
            if (skill.getName().equals(skillsDto.getMainSkill())) {
                return true;
            }
        }
        return false;
    }
}
