package com.wdjpng.blickhack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static private final int REQUEST_IMAGE_CAPTURE = 1;
    static private final int REQUEST_IMAGE_PICK = 2;

    private ClickListener clickListener;
    private TextRecogniser textRecogniser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        defineVariables();
    }

    private void defineVariables() {
        clickListener = new ClickListener(this);
        textRecogniser = new TextRecogniser(this);
    }



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
                textRecogniser.recogniseAndGoogleText(imageBitmap);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    public ClickListener getClickListener() {
        return clickListener;
    }
}

