package edu.nau.css.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder(setterPrefix = "with")
@Getter
@Setter
public class ObjectActionResponseDTO {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("method")
    private String method;

    @JsonProperty("objectKey")
    private String objectKey;

    @JsonProperty("insideObjects")
    private List<String> insideObjects;

    @JsonProperty("time")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime time;

}
