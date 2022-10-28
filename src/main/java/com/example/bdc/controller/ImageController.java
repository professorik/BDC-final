package com.example.bdc.controller;

import com.example.bdc.model.ImageResponse;
import com.example.bdc.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Validated
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/image-input")
    public ImageResponse processImage(@RequestParam("min_level") @Min(0) @Max(100) int minLevel, @RequestParam String image) {
        return imageService.processImage(minLevel, image);
    }
}
