package edu.nau.css.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class ErrorDTO {

    @JsonProperty(value = "status")
    private int status;

    @JsonProperty(value = "path")
    private String path;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "time")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime time;

    @JsonProperty(value = "key")
    private List<String> keys;

}
