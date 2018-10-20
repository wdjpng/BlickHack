package com.wdjpng.blickhack;

import android.graphics.Bitmap;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class TextRecogniser {
    private MainActivity mainActivity;
    private Listeners listeners;

    protected TextRecogniser(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.listeners = mainActivity.getListeners();
    }

    protected void recogniseText(final Bitmap imageBitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);


        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();


        textRecognizer.processImage(image)
                .addOnSuccessListener(listeners.getFirebaseVisionTextOnSuccessListener())
                .addOnFailureListener(listeners.getFirebaseVisionTextOnFailureListener());
    }
}
