package com.wdjpng.blickhack;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

public class ClickListener {
    private MainActivity mainActivity;
    private Button selectImageButton;
    private Button takeImageButton;
    private Intents intents;
    private QueryGenerator queryGenerator;

    protected OnSuccessListener<FirebaseVisionText> firebaseVisionTextOnSuccessListener;
    protected OnFailureListener firebaseVisionTextOnFailureListener;

    protected ClickListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        defineWidgets();
        defineListeners();
        defineVariables();
    }

    private void defineVariables(){
        intents = new  Intents(mainActivity);
        queryGenerator = new QueryGenerator(mainActivity);

    }

    private void defineWidgets() {
        selectImageButton = mainActivity.findViewById(R.id.selectImageButton);
        takeImageButton = mainActivity.findViewById(R.id.takeImageButton);
    }

    private void defineListeners() {
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intents.dispatchSelectPictureIntent();
            }
        });

        takeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intents.dispatchTakePictureIntent();
            }
        });


        firebaseVisionTextOnSuccessListener = new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {

                try {
                    intents.dispatchWebSearchIntent(queryGenerator.generateQuery(firebaseVisionText));
                }
                catch (IncorrectTextException incorrectTextException){
                    incorrectTextException.printStackTrace();
                    //TODO shwo errror message to user

                }


            }
        };

        firebaseVisionTextOnFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        };
    }

    protected OnSuccessListener<FirebaseVisionText> getFirebaseVisionTextOnSuccessListener() {
        return firebaseVisionTextOnSuccessListener;
    }

    protected OnFailureListener getFirebaseVisionTextOnFailureListener() {
        return firebaseVisionTextOnFailureListener;
    }
}
