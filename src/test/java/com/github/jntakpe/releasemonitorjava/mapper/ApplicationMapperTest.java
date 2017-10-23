package com.github.jntakpe.releasemonitorjava.mapper;

import com.github.jntakpe.releasemonitorjava.model.AppVersion;
import com.github.jntakpe.releasemonitorjava.model.Application;
import com.github.jntakpe.releasemonitorjava.model.VersionType;
import com.github.jntakpe.releasemonitorjava.model.api.ApplicationDTO;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationMapperTest {

    @Test
    public void map_shouldMapDTOWithoutIdToEntity() {
        AppVersion version = new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        ApplicationDTO dto = new ApplicationDTO().setGroup("foo").setName("bar").setVersions(Collections.singletonList(version));
        Application entity = ApplicationMapper.map(dto);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getGroup()).isEqualTo(dto.getGroup());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getVersions()).isEqualTo(dto.getVersions());
    }

    @Test
    public void map_shouldMapDTOWithIdToEntity() {
        AppVersion version = new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        String id = new ObjectId().toString();
        ApplicationDTO dto = new ApplicationDTO()
                .setId(id)
                .setGroup("foo")
                .setName("bar")
                .setVersions(Collections.singletonList(version));
        Application entity = ApplicationMapper.map(dto);
        assertThat(entity.getId()).isEqualTo(new ObjectId(id));
    }

    @Test
    public void map_shouldMapEntityWithoutIdToDTO() {
        AppVersion version = new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        Application entity = new Application()
                .setGroup("foo")
                .setName("bar")
                .setVersions(Collections.singletonList(version));
        ApplicationDTO dto = ApplicationMapper.map(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getGroup()).isEqualTo(entity.getGroup());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getVersions()).isEqualTo(entity.getVersions());
    }

    @Test
    public void map_shouldMapEntityWithIdToDTO() {
        AppVersion version = new AppVersion().setRaw("1.2.3").setMajor(1).setMinor(2).setPatch(3).setType(VersionType.RELEASE);
        ObjectId id = new ObjectId();
        Application entity = new Application()
                .setId(id)
                .setGroup("foo")
                .setName("bar")
                .setVersions(Collections.singletonList(version));
        ApplicationDTO dto = ApplicationMapper.map(entity);
        assertThat(dto.getId()).isEqualTo(id.toString());
    }

}