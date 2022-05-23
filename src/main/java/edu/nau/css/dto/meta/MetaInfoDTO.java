package edu.nau.css.dto.meta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MetaInfoDTO {

    @JsonProperty("objectId")
    private Long objectId;

    @JsonProperty("objectName")
    private String objectName;

    @JsonProperty("objectFullName")
    private String objectFullName;

    @JsonProperty("parentObjectId")
    private Long parentObjectId;

    @JsonProperty("isFolder")
    private Boolean isFolder;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime updatedAt;

    @JsonProperty("userId")
    private Long userId;

}
