package com.example.bdc.service;

import com.example.bdc.exception.BadRequestException;
import com.example.bdc.exception.UnprocessableEntityException;
import com.example.bdc.model.ImageResponse;
import com.example.bdc.model.Mine;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private static final int WHITE = 255;
    private static final int THRESHOLD = 155;

    /**
     * @param x
     * @param y
     * @param size
     * Stores top-left coord and size
     */
    record Cell (int x, int y, int size){}

    public ImageResponse processImage(int minLevel, String img) throws BadRequestException {
        BufferedImage image;
        try {
            image = base64ToBufferedImage(img);
        } catch (IOException e){
            throw new BadRequestException("invalid base64 text");
        }
        List<Mine> mineList = new ArrayList<>();
        ImageResponse response = new ImageResponse(mineList);

        Cell start = cut(image);
        for (int i = start.x + 1; i + start.size <= image.getWidth(); i += start.size + 1) {
            for (int j = start.y + 1; j + start.size <= image.getHeight(); j += start.size + 1) {
                int percentage = getCellPercentage(image, new Cell(i, j, start.size));
                if (percentage >= minLevel) {
                    mineList.add(new Mine((i-start.x-1)/(start.size+1), (j-start.y-1)/(start.size+1), percentage));
                }
            }
        }

        return response;
    }

    private int getCellPercentage(BufferedImage image, Cell c) {
        int count = 0;
        for (int x = 0; x < c.size; x++) {
            for (int y = 0; y < c.size; y++) {
                if (getGrayScale(c.x + x, c.y + y, image) < THRESHOLD) {
                    ++count;
                }
            }
        }
        return (int) Math.round(100*count/Math.pow(c.size, 2));
    }

    private Cell cut(BufferedImage image){
        int x_first = -1, x_second = -1, y_first = -1;

        rows:
        for (int i = 0; i < image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {
                if (getGrayScale(i, j, image) != WHITE) {
                    continue rows;
                }
            }
            if (x_first == -1){
                x_first = i;
            } else {
                x_second = i;
                break;
            }
        }
        columns:
        for (int i = 0; i < image.getHeight(); ++i) {
            for (int j = 0; j < image.getWidth(); ++j) {
                if (getGrayScale(j, i, image) != WHITE) {
                    continue columns;
                }
            }
            y_first = i;
            break;
        }

        int size = x_second - x_first - 1;

        Cell res = new Cell(x_first, y_first, size);
        checkGridConstraints(image, res);
        return res;
    }

    private int getGrayScale(int x, int y, BufferedImage image) {
        Color color = new Color(image.getRGB(x, y));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        if (red != blue || red != green) {
            throw new UnprocessableEntityException(
                    "Invalid colors",
                    String.format("Color(%d,%d)=RGB(%d,%d,%d)", x, y, red, green, blue));
        }
        return red;
    }

    private BufferedImage base64ToBufferedImage(String img) throws IOException {
        img = img.replaceAll(" ", "+");
        img += Character.toString(13) + Character.toString(10);

        String imageDataBytes = img.substring(img.indexOf(",") + 1);

        InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(imageDataBytes.getBytes()));
        return ImageIO.read(stream);
    }

    private void checkGridConstraints(BufferedImage image, Cell c){
        if (c.size <= 0 || c.x < 0 || c.y < 0 || c.x >= image.getWidth() || c.y >= image.getHeight()) {
            throw new UnprocessableEntityException("Failed to process image", "Impossible to detect the grid");
        }
        for (int i = c.x; i < image.getWidth(); i += c.size + 1) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (getGrayScale(i, j, image) != WHITE) {
                    throw new UnprocessableEntityException("Failed to process image", "Cell must be square");
                }
            }
        }
        for (int j = c.y; j < image.getHeight(); j += c.size + 1) {
            for (int i = 0; i < image.getWidth(); i++) {
                if (getGrayScale(i, j, image) != WHITE) {
                    throw new UnprocessableEntityException("Failed to process image", "Cell must be square");
                }
            }
        }
    }
}
