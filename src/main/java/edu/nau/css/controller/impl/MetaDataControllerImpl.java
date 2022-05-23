package edu.nau.css.controller.impl;

import edu.nau.css.controller.MetaDataController;
import edu.nau.css.controller.mapper.FileMetaInfoMapper;
import edu.nau.css.controller.mapper.FolderMetaInfoMapper;
import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import edu.nau.css.dto.meta.MetaInfoDTO;
import edu.nau.css.service.MetaDataService;
import edu.nau.css.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static edu.nau.css.constants.Endpoint.INFO_ENDPOINT;

@RestController
@RequestMapping(INFO_ENDPOINT)
public class MetaDataControllerImpl implements MetaDataController {

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private UserService userService;

    @Override
    public MetaInfoDTO getObjectInfo(HttpServletRequest request) {

        String path = request.getRequestURI().substring(INFO_ENDPOINT.length());
        path = path.isEmpty() || path.equals("/") ? "/" : path.substring(1);

        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        ObjectMetaInfo objectInfo = metaDataService.getObjectMetaInfo(path, user);

        return objectInfo.getIsFolder()
                ? FolderMetaInfoMapper.mapObjectMetaInfoToFolderMetaInfoDTO(objectInfo)
                : FileMetaInfoMapper.mapObjectMetaInfoToFileMetaInfoDTO(objectInfo);
    }

}
