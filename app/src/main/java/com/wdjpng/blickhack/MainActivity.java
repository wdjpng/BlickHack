package com.wdjpng.blickhack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;

/**
 * This code is under the MIT License.
 * All this code is, if not written otherwise, written by wdjpng.
 *
 * @author wdjpng
 * @version 1.2.2
 */

/**
 * The MainActivity basically just delegates setting onClickListeners.
 */
public class MainActivity extends AppCompatActivity {

    static private final int REQUEST_IMAGE_CAPTURE = 1;
    static private final int REQUEST_IMAGE_PICK = 2;

    private Listeners listeners;
    private TextRecogniser textRecogniser;

    private Button selectImageButton;
    private Button takeImageButton;

    /**
     * The onCreate function will be called when the app launches.
     * The only thing it really makes is setting the contentView and defining some objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineWidgets();
        defineObjects();
    }

    /**
     *This method connects all widget variables in this class
     *with all the important widgets in the layout activity_main
     */
    private void defineWidgets() {
        selectImageButton = this.findViewById(R.id.selectImageButton);
        takeImageButton = this.findViewById(R.id.takeImageButton);
    }

    /**
     * This method defines the listeners and the textRecogniser
     * @see Listeners
     * @see TextRecogniser
     */
    private void defineObjects() {
        listeners = new Listeners(this);
        textRecogniser = new TextRecogniser(this);
    }




    /**
     * This method handles the results of the dispatchTakePictureIntent() and
     * the dispatchSelectPictureIntent() methods in the Intents class.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap = null;

        try {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");

            } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();

                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            }

            if (imageBitmap != null) {
                textRecogniser.recogniseText(imageBitmap);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * This is the getter for the OnCLickListener onClickListener
     * @return The OnCLickListener onClickListener
     */
    protected Listeners getListeners() {
        return listeners;
    }

    /**
     * This is the getter for the selectImageButton
     * @return The button selectImageButton
     */
    protected Button getSelectImageButton() {
        return selectImageButton;
    }

    /**
     * This is the getter for the takeImageButton
     * @return The button takeImageButton
     */
    protected Button getTakeImageButton() {
        return takeImageButton;
    }
}

