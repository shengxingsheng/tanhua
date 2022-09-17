package com.tanhua.server.controller;

import com.tanhua.model.dto.MovementDto;
import com.tanhua.server.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author sxs
 * @create 2022-09-17 14:07
 */
@RestController
@RequestMapping("/movements")
public class MovementsController {
    @Autowired
    private MovementService movementService;
    @PostMapping
    public ResponseEntity movements(MovementDto movementDto, MultipartFile[] imageContent) throws IOException {
        movementService.movements(movementDto,imageContent);
        return ResponseEntity.ok(null);
    }
}
