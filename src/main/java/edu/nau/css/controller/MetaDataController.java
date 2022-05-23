package edu.nau.css.controller;

import edu.nau.css.dto.meta.MetaInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static edu.nau.css.constants.Endpoint.INFO_ENDPOINT;
import static edu.nau.css.constants.Endpoint.OBJECT_KEY_SUFFIX;

@Tag(name = "Meta Info", description = "Object meta-information API")
@RequestMapping(INFO_ENDPOINT)
public interface MetaDataController {

    @Operation(summary = "Get object information", tags = "info")
    @ApiResponse(
            responseCode = "200",
            description = "Found object info in db",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MetaInfoDTO.class)))
            })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(OBJECT_KEY_SUFFIX)
    MetaInfoDTO getObjectInfo(HttpServletRequest request);

}