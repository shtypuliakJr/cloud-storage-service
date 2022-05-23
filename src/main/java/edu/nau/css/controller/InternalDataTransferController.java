package edu.nau.css.controller;

import edu.nau.css.dto.meta.MetaInfoDTO;
import edu.nau.css.dto.param.PairSourceDestinationDTO;
import edu.nau.css.dto.response.ObjectActionResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static edu.nau.css.constants.Endpoint.*;

@RequestMapping(OBJECT_ENDPOINT)
public interface InternalDataTransferController {

    @PostMapping(MOVE_PART)
    void moveObjectsInside(@RequestBody List<PairSourceDestinationDTO> insideActionParameterDTO);

    @PostMapping(COPY_PART)
    List<PairSourceDestinationDTO> copyObjectsInside(@RequestBody List<PairSourceDestinationDTO> insideActionParameterDTO);

    @PutMapping(OBJECT_KEY_SUFFIX)
    ResponseEntity<MetaInfoDTO> renameObject(HttpServletRequest request, @RequestParam String newKey);

    @DeleteMapping(OBJECT_KEY_SUFFIX)
    ResponseEntity<ObjectActionResponseDTO> deleteObject(HttpServletRequest request);

    @PostMapping(value = OBJECT_KEY_SUFFIX, params = "folderName")
    ResponseEntity<MetaInfoDTO> createFolder(HttpServletRequest request, @RequestParam("folderName") String folderName);

}