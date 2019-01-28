package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.mappers.AppConfigMapper;
import com.abhinav.learn_spring.models.entities.AppUpdateConfigEntity;
import com.abhinav.learn_spring.models.entries.AppUpdateConfigEntry;
import com.abhinav.learn_spring.models.repositories.AppUpdateConfigRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AppConfigService extends BaseService<AppUpdateConfigEntity, AppUpdateConfigEntry> {
    @Autowired
    private AppUpdateConfigRepository appUpdateConfigRepository;

    private AppConfigMapper appConfigMapper = Mappers.getMapper(AppConfigMapper.class);

    public AppUpdateConfigEntry create(AppUpdateConfigEntry request) {
        AppUpdateConfigEntity entity = convertToEntity(request);
        return convertToEntry(appUpdateConfigRepository.save(entity));
    }

    public AppUpdateConfigEntry getConfig() {
        List<AppUpdateConfigEntity> configs = StreamSupport.stream(appUpdateConfigRepository.findAll().spliterator(), false).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        } else {
            return convertToEntry(configs.get(0));
        }
    }

    public AppUpdateConfigEntity getConfigByVersionNo(Long versionNo) {
        return appUpdateConfigRepository.findByVersionNo(versionNo);
    }

    @Override
    public AppUpdateConfigEntry convertToEntry(AppUpdateConfigEntity entity) {
        return appConfigMapper.toEntry(entity);
    }

    @Override
    public AppUpdateConfigEntity convertToEntity(AppUpdateConfigEntry entry) {
        return appConfigMapper.toEntity(entry);
    }
}