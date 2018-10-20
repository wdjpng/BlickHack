package com.wdjpng.blickhack;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.MediaStore;

public class Intents {

    private MainActivity mainActivity;

    static private final int REQUEST_IMAGE_CAPTURE = 1;
    static private final int REQUEST_IMAGE_PICK = 2;


    public Intents(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
            mainActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public void dispatchSelectPictureIntent() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, mainActivity.getString(R.string.selectImage));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        mainActivity.startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);
    }

    public void dispatchWebSearchIntent (String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);

        mainActivity.startActivity(intent);
    }
}
