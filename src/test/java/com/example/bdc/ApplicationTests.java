package com.example.bdc;

import com.example.bdc.exception.BadRequestException;
import com.example.bdc.exception.UnprocessableEntityException;
import com.example.bdc.model.Mine;
import com.example.bdc.service.ImageService;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ImageService imageService;

    private final String[] paths = {"invalid_base64", "invalid_colors", "normal_1", "normal_2", "not_square"};
    final String PATH = "/src/test/java/com/example/bdc/images";

    @Test
    void contextLoads() {
        assertThat(imageService).isNotNull();
    }

    @Test
    @DisplayName("test invalid base 64")
    public void testImg1() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> imageService.processImage(50, getImage(0)));

        assertEquals("invalid base64 text", exception.getMessage());
    }

    @Test
    @DisplayName("test invalid colors")
    public void testImg2() {
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> imageService.processImage(50, getImage(1)));

        assertEquals("Invalid colors", exception.getMessage());
        assertEquals("Color(0,0)=RGB(253,210,9)", exception.getDetails());
    }

    @Test
    @DisplayName("test not square")
    public void testImg3() {
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> imageService.processImage(50, getImage(4)));

        assertEquals("Failed to process image", exception.getMessage());
        assertEquals("Cell must be square", exception.getDetails());
    }

    @Test
    @DisplayName("test normal 1")
    public void testImg4() throws FileNotFoundException {
        var res =  imageService.processImage(50, getImage(2));

        assertEquals(res.mines().size(), 110);
        assertEquals(res.mines().get(0), new Mine(1, 30, 86));
    }

    @Test
    @DisplayName("test normal 2")
    public void testImg5() throws FileNotFoundException {
        var res =  imageService.processImage(50, getImage(3));

        assertEquals(res.mines().size(), 11);
        assertEquals(res.mines().get(0), new Mine(0, 0, 100));
    }

    private String getImage(int index) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+String.format("%s/%s.txt", PATH, paths[index]));
        return IOUtils.toString(fis, StandardCharsets.UTF_8);
    }
}
