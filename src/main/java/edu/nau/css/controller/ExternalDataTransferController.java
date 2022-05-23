package edu.nau.css.controller;

import edu.nau.css.dto.meta.MetaInfoDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static edu.nau.css.constants.Endpoint.OBJECT_ENDPOINT;
import static edu.nau.css.constants.Endpoint.OBJECT_KEY_SUFFIX;

@RequestMapping(OBJECT_ENDPOINT)
public interface ExternalDataTransferController {

    @GetMapping(OBJECT_KEY_SUFFIX)
    ResponseEntity<InputStreamResource> downloadObject(HttpServletRequest request);

    @GetMapping
    ResponseEntity<StreamingResponseBody> downloadMultipleObject(@RequestBody List<String> keys, HttpServletResponse response) throws IOException;

    @PostMapping(OBJECT_KEY_SUFFIX)
    ResponseEntity<List<MetaInfoDTO>> uploadObject(@RequestParam(value = "file") MultipartFile[] files, HttpServletRequest request);

}