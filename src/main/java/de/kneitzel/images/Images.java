package de.kneitzel.images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Helper class to scale images
 */
public class Images {

    /**
     * Calculates the scale factor required to scale a picturoe of given original size so that the result fits inside
     * the target size.
     * @param originalWidth original width.
     * @param originalHeight original height.
     * @param targetWidth target width.
     * @param targetHeight target height.
     * @return scale factor.
     */
    protected static double calculateScaleFactor (final int originalWidth, final int originalHeight,
                                  final int targetWidth, final int targetHeight) {
        double scaleFactorWidth = (double) targetWidth / (double) originalWidth;
        double scaleFactorHeight = (double) targetHeight / (double) originalHeight;

        return Math.min(scaleFactorWidth, scaleFactorHeight);
    }

    /**
     * Scales an image without changing the ratio between width and heights.
     * @param originalImage original Image to scale.
     * @param width target width.
     * @param height target height.
     * @return the scaled image.
     */
    public static BufferedImage scaleImage(final BufferedImage originalImage, final int width, final int height) {

        double scaleFactor = calculateScaleFactor(originalImage.getWidth(), originalImage.getHeight(), width, height);

        int newHeight = (int) (scaleFactor * originalImage.getHeight());
        int newWidth = (int) (scaleFactor * originalImage.getWidth());
        int dx = (width - newWidth) / 2;
        int dy = (height - newHeight) / 2;

        // Scale the image
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = scaledImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(originalImage, dx, dy, newWidth, newHeight, null);
        graphics.dispose();
        return scaledImage;
    }

    /**
     * Scales an image without changing the ratio between width and heights.
     * @param originalImageBytes Original Image bytes.
     * @param width target width.
     * @param height target height.
     * @return Bytes of scaled image.
     */
    public static byte[] scaleImage(final byte[] originalImageBytes, final int width, final int height) {
        // Validation
        if (originalImageBytes == null) return null;
        if (originalImageBytes.length==0) return originalImageBytes;

        try {
            // Create the image from a byte array.
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalImageBytes));

            // Scale the image.
            BufferedImage scaledImage = scaleImage(originalImage, width, height);

            // Get the bytes of the image
            try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                ImageIO.write(scaledImage, "png", stream);
                return stream.toByteArray();
            }
        } catch (Exception ex) {
            // TODO: Log something :)
            return null;
        }
    }

    /**
     * Draws an Image inside another image.
     * @param backgroundImage Image to draw into.
     * @param drawImage Image to draw.
     * @param x x position to draw.
     * @param y y position to draw.
     * @param width width of the image drawn.
     * @param height height of the image drawn.
     */
    public static void drawPicture(final BufferedImage backgroundImage, final BufferedImage drawImage, final int x, final int y, final int width, final int height) {
        BufferedImage resizedImage = scaleImage(drawImage, width, height);

        Graphics2D graphics = backgroundImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(resizedImage, x, y, width, height, null);
        graphics.dispose();
    }
}
