package edu.nau.css.controller.mapper;

import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.dto.meta.FileMetaInfoDTO;
import edu.nau.css.dto.meta.MetaInfoDTO;

public class FileMetaInfoMapper {

    public static MetaInfoDTO mapObjectMetaInfoToFileMetaInfoDTO(ObjectMetaInfo objectMetaInfo) {

        return FileMetaInfoDTO.builder()
                .withObjectId(objectMetaInfo.getObjectId())
                .withObjectName(objectMetaInfo.getObjectName())
                .withObjectFullName(objectMetaInfo.getObjectName())
                .withFileType(objectMetaInfo.getFileType())
                .withParentObjectId(objectMetaInfo.getParentObject().getObjectId())
                .withIsFolder(objectMetaInfo.getIsFolder())
                .withCreatedAt(objectMetaInfo.getCreatedAt())
                .withUpdatedAt(objectMetaInfo.getUpdatedAt())
                .withUserId(objectMetaInfo.getUser().getUserId())
                .build();
    }

}
