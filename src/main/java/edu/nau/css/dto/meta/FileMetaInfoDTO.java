package edu.nau.css.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FileMetaInfoDTO extends MetaInfoDTO {

    @JsonProperty(value = "fileType")
    private final String fileType;

    @Builder(setterPrefix = "with")
    public FileMetaInfoDTO(Long objectId, String objectName, String objectFullName, Long parentObjectId, Boolean isFolder, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, String fileType) {
        super(objectId, objectName, objectFullName, parentObjectId, isFolder, createdAt, updatedAt, userId);
        this.fileType = fileType;
    }
}
