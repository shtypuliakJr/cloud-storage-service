package edu.nau.css.controller.impl;

import edu.nau.css.controller.ExternalDataTransferController;
import edu.nau.css.controller.mapper.FileMetaInfoMapper;
import edu.nau.css.domain.ObjectEntity;
import edu.nau.css.domain.User;
import edu.nau.css.dto.meta.MetaInfoDTO;
import edu.nau.css.service.ExternalDataTransferService;
import edu.nau.css.service.MetaDataService;
import edu.nau.css.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static edu.nau.css.constants.Endpoint.OBJECT_ENDPOINT;

@RestController
public class ExternalDataTransferControllerImpl implements ExternalDataTransferController {

    private final ExternalDataTransferService dataTransferService;
    private final MetaDataService metaDataService;
    private final UserService userService;

    @Autowired
    public ExternalDataTransferControllerImpl(ExternalDataTransferService dataTransferService, MetaDataService metaDataService, UserService userService) {
        this.dataTransferService = dataTransferService;
        this.metaDataService = metaDataService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadObject(HttpServletRequest request) {

        String key = request.getRequestURI().substring(OBJECT_ENDPOINT.length() + 1);

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        metaDataService.checkObjectExistence(key, user);

        ObjectEntity objectEntity = dataTransferService.downloadObject(String.format("%s/%s", user.getName(), key));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", objectEntity.getFilename()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(objectEntity.getLength())
                .body(objectEntity.getInputStreamResource());
    }

    @Override
    public ResponseEntity<StreamingResponseBody> downloadMultipleObject(@RequestBody List<String> keys, HttpServletResponse response) {

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        metaDataService.checkObjectsExistence(keys, user);

        StreamingResponseBody streamingResponseBody = null;
        try {
            streamingResponseBody = dataTransferService.downloadMultipleObjects(keys, user, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", "content.zip"))
                .body(streamingResponseBody);
    }

    @Override
    public ResponseEntity<List<MetaInfoDTO>> uploadObject(MultipartFile[] files, HttpServletRequest request) {

        String path = request.getRequestURI().substring(OBJECT_ENDPOINT.length());
        String key = path.isEmpty() || path.equals("/") ? "/" : path.substring(1);

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<MetaInfoDTO> uploadedFilesMetaInfoList = dataTransferService.uploadObject(files, key, user).stream()
                .map(FileMetaInfoMapper::mapObjectMetaInfoToFileMetaInfoDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFilesMetaInfoList);
    }

}
