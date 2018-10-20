package com.wdjpng.blickhack;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

/**
 * The use of this class is defining all needed listeners
 */
public class Listeners {
    private MainActivity mainActivity;
    private Intents intents;
    private QueryGenerator queryGenerator;

    protected OnSuccessListener<FirebaseVisionText> firebaseVisionTextOnSuccessListener;
    protected OnFailureListener firebaseVisionTextOnFailureListener;

    /**
     * This constructor defines the mainActivity variable and
     * calls defineListeners() and defineVariables()
     *
     *TODO make sur supass is = weitergeben
     * @param mainActivity is used by the app to be able to access different widgets and to surpass this it to the Intents
     */
    protected Listeners(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        defineListeners();
        defineVariables();
    }

    /**
     * The defineVariables() method defines the variables queryGenerator and intents
     * and TODO surpasses the mainActivity variable to the Intents constructor
     */
    private void defineVariables(){
        intents = new  Intents(mainActivity);
        queryGenerator = new QueryGenerator();

    }

    /**
     * The defineListeners() method currently defines all listeners in the hole app
     */
    private void defineListeners() {
        mainActivity.getSelectImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intents.dispatchSelectPictureIntent();
            }
        });

        mainActivity.getTakeImageButton().setOnClickListener(new View.OnClickListener() {
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
                    //TODO show error message to user

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


    /**
     * This method is used in the TextRecogniser class to define the
     * firebaseVisionTextOnSuccessListener of the FirebaseVisionTextRecognizer
     * the recogniseText() method in the TextRecogniser class
     *
     * @return The firebaseVisionTextOnSuccessListener
     * @see TextRecogniser
     */
    protected OnSuccessListener<FirebaseVisionText> getFirebaseVisionTextOnSuccessListener() {
        return firebaseVisionTextOnSuccessListener;
    }

    /**
     * This method is used in the TextRecogniser class to define the
     * firebaseVisionTextOnFailureListener of the FirebaseVisionTextRecognizer
     * the recogniseText() method in the TextRecogniser class
     *
     * @return The firebaseVisionTextOnFailureListener
     * @see TextRecogniser
     */
    protected OnFailureListener getFirebaseVisionTextOnFailureListener() {
        return firebaseVisionTextOnFailureListener;
    }
}
