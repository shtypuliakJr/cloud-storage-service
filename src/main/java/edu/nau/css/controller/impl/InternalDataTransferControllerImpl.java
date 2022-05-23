package edu.nau.css.controller.impl;

import edu.nau.css.controller.InternalDataTransferController;
import edu.nau.css.controller.mapper.FileMetaInfoMapper;
import edu.nau.css.controller.mapper.FolderMetaInfoMapper;
import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import edu.nau.css.dto.meta.MetaInfoDTO;
import edu.nau.css.dto.param.PairSourceDestinationDTO;
import edu.nau.css.dto.response.ObjectActionResponseDTO;
import edu.nau.css.service.UserService;
import edu.nau.css.service.impl.InternalDataTransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static edu.nau.css.constants.Endpoint.OBJECT_ENDPOINT;

@RestController
public class InternalDataTransferControllerImpl implements InternalDataTransferController {

    @Autowired
    private InternalDataTransferServiceImpl insideDataTransferService;

    @Autowired
    private UserService userService;

    @Override
    public void moveObjectsInside(@RequestBody List<PairSourceDestinationDTO> moveSourceDestinationList) {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public List<PairSourceDestinationDTO> copyObjectsInside(@RequestBody List<PairSourceDestinationDTO> copySourceDestinationList) {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        insideDataTransferService.copyObjectsInside(copySourceDestinationList, user);
        return copySourceDestinationList;
    }

    @Override
    public ResponseEntity<MetaInfoDTO> renameObject(HttpServletRequest request, @RequestParam String newName) {

        String key = request.getRequestURI().substring(OBJECT_ENDPOINT.length() + 1);

        String newKey = key.contains("/")
                ? key.substring(0, key.lastIndexOf("/")) + "/" + newName
                : newName;

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        ObjectMetaInfo objectMetaInfo = insideDataTransferService.renameObject(key, newKey, user);

        return ResponseEntity.ok().body(FileMetaInfoMapper.mapObjectMetaInfoToFileMetaInfoDTO(objectMetaInfo));
    }

    @Override
    public ResponseEntity<ObjectActionResponseDTO> deleteObject(HttpServletRequest request) {
        String key = request.getRequestURI().substring(OBJECT_ENDPOINT.length() + 1);

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<String> deletedObjects = insideDataTransferService.deleteObject(key, user);

        return ResponseEntity.ok(ObjectActionResponseDTO.builder()
                .withCode(HttpStatus.OK.value())
                .withMethod("DELETE")
                .withObjectKey(key)
                .withInsideObjects(deletedObjects)
                .withTime(LocalDateTime.now())
                .build());
    }

    @Override
    public ResponseEntity<MetaInfoDTO> createFolder(HttpServletRequest request, String folderName) {

        String path = request.getRequestURI().substring(OBJECT_ENDPOINT.length());
        String parentFolderKey = path.isEmpty() ? "/" : path.substring(1);
        String folderKey = parentFolderKey.equals("/") ? folderName : String.format("%s/%s", path, folderName);

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        ObjectMetaInfo objectMetaInfo = insideDataTransferService.createFolder(folderKey, parentFolderKey, user);
        return ResponseEntity.ok()
                .body(FolderMetaInfoMapper.mapObjectMetaInfoToFolderMetaInfoDTO(objectMetaInfo));
    }

}
