package com.wdjpng.blickhack;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class TextRecogniser {
    private MainActivity mainActivity;
    private  ClickListener clickListener;

    public TextRecogniser(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.clickListener = mainActivity.getClickListener();
    }

    public void recogniseAndGoogleText(final Bitmap imageBitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);


        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();


        textRecognizer.processImage(image)
                .addOnSuccessListener(clickListener.getFirebaseVisionTextOnSuccessListener())
                .addOnFailureListener(clickListener.getFirebaseVisionTextOnFailureListener());
    }
}
