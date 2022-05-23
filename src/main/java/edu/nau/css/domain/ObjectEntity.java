package edu.nau.css.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.Serializable;
import java.util.Map;

@Builder
@Getter
@Setter
public class ObjectEntity implements Serializable {

    private String filename;
    private String fileType;
    private Long length;
    private Map<String, String> meta;
    private ObjectMetaInfo objectMetaInfo;
    private InputStreamResource inputStreamResource;

}
