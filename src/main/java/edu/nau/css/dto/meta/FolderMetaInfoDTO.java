package edu.nau.css.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FolderMetaInfoDTO extends MetaInfoDTO {

    @JsonProperty(value = "objectMetaInfoDTOs")
    private final List<MetaInfoDTO> objectMetaInfoDTOs;

    @Builder(setterPrefix = "with")
    public FolderMetaInfoDTO(Long objectId, String objectName, String objectFullName, Long parentObjectId, Boolean isFolder, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, List<MetaInfoDTO> objectMetaInfoDTOs) {
        super(objectId, objectName, objectFullName, parentObjectId, isFolder, createdAt, updatedAt, userId);
        this.objectMetaInfoDTOs = objectMetaInfoDTOs;
    }
}
