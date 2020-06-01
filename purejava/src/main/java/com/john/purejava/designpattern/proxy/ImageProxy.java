package com.john.purejava.designpattern.proxy;

/**
 * Created by JohnZh on 2020/5/27
 *
 * <p></p>
 */
public class ImageProxy implements Image {

    private LocalImage image;

    public ImageProxy(String filename) {
        image = new LocalImage(filename);
        image.load();
    }

    @Override
    public void display() {
        scaleImage();
        image.display();
        maskImage();
    }

    private void maskImage() {
        System.out.println("Mask image");
    }

    private void scaleImage() {
        System.out.println("Scale image");
    }
}
