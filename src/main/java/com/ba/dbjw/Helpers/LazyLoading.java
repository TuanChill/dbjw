package com.ba.dbjw.Helpers;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LazyLoading {

    public static void loadAndDisplayImage(ImageView imgPreview, String imageUrl) {
        Thread thread = new Thread(() -> {
            try {
                Image image = new Image(imageUrl);
                System.out.println(imageUrl);
                Platform.runLater(() -> {
                    imgPreview.setImage(image);
                    System.out.println(imgPreview.getImage().getUrl());
                });
            } catch (Exception e) {
            }
        });

        thread.start();
    }
}
