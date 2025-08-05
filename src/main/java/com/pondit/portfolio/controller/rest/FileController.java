package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.model.dto.FileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File Resource", description = "API for managing files")
@RestController
@RequestMapping("/api/upload image")
public class FileController {

    @Operation(summary = "Upload files")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadImage(@RequestParam("image") MultipartFile image){
        String fileName = image.getOriginalFilename();
        return ResponseEntity.ok(new FileResponse(fileName, "File uploaded successfully"));
    }
}
