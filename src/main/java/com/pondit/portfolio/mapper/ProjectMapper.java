package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project entityToDomain(ProjectEntity entity) {
        Project domain = new Project();
        BeanUtils.copyProperties(entity, domain);
        return domain;
    }
}
