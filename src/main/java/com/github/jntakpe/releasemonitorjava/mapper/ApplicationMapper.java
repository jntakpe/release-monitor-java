package com.github.jntakpe.releasemonitorjava.mapper;

import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.model.api.ApplicationDTO;
import org.bson.types.ObjectId;

public final class ApplicationMapper {

    private ApplicationMapper() {
    }

    public static Application map(ApplicationDTO dto) {
        return new Application()
                .setId(dto.getId() != null ? new ObjectId(dto.getId()) : null)
                .setGroup(dto.getGroup())
                .setName(dto.getName())
                .setVersions(dto.getVersions());
    }

    public static ApplicationDTO map(Application entity) {
        return new ApplicationDTO()
                .setId(entity.getId().toString())
                .setGroup(entity.getGroup())
                .setName(entity.getName())
                .setVersions(entity.getVersions());
    }

}
