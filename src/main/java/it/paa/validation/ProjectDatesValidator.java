package it.paa.validation;

import it.paa.model.entity.Project;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProjectDatesValidator implements ConstraintValidator<ProjectDates, Project> {


    @Override
    public boolean isValid(Project project, ConstraintValidatorContext context) {
        if(project.getStartDate() == null && project.getEndDate() == null)
            return project.getEndDate() == null;

        if(project.getStartDate() != null && project.getEndDate() == null)
            return true;

        if(project.getStartDate() != null && project.getEndDate() != null){
            return !project.getStartDate().isAfter(project.getEndDate());
        }

        return true;
    }
}
