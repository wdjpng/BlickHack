package com.wdjpng.blickhack;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * This class just implements methods to start certain activities
 * TODO rename
 */
public class Intents {

    private MainActivity mainActivity;

    static private final int REQUEST_IMAGE_CAPTURE = 1;
    static private final int REQUEST_IMAGE_PICK = 2;

    /**
     * This is the only available constructor for this class.
     * @param mainActivity is used by the app to be able to start activities
     */
    public Intents(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    /**
     * This method launches an generates an intent to take a picture.
     * The results of this intent get handled by the
     * onActivityResult(int requestCode, int resultCode, Intent data) function in the MainActivity.
     */
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
            mainActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    /**
     * This method launches an generates an intent to choose a picture from you files or from the gallery.
     * The results of this intent get handled by the
     * onActivityResult(int requestCode, int resultCode, Intent data) function in the MainActivity.
     */
    public void dispatchSelectPictureIntent() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, mainActivity.getString(R.string.selectImage));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        mainActivity.startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);
    }

    /**
     * This method launches the default Browser and searches in the default search for the query.
     * Please note that this app is only optimized and tested for the google search engine.
     *
     * @param query the search query, NOT the url
     */
    public void dispatchWebSearchIntent (String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);

        mainActivity.startActivity(intent);
    }
}
