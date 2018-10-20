package com.wdjpng.blickhack;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static private final int REQUEST_IMAGE_CAPTURE = 1;
    static private final int REQUEST_IMAGE_PICK = 2;

    private Button selectImageButton;
    private Button takeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineWidgets();
        defineListeners();
    }

    private void defineWidgets() {
        selectImageButton = this.findViewById(R.id.selectImageButton);
        takeImageButton = this.findViewById(R.id.takeImageButton);
    }

    private void defineListeners() {
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchSelectPictureIntent();
            }
        });

        takeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap = null;

        try {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");

            }

            else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();

                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            }

            if(imageBitmap != null){
                recogniseAndGoogleText(imageBitmap);
            }
        }

        catch (IOException | NullPointerException e ) {
            e.printStackTrace();
        }

    }

    private void searchQuery(String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        startActivity(intent);
    }

    //TODO make this instant without need for congermation
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private void dispatchSelectPictureIntent() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, getString(R.string.selectImage));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);
    }

    private void recogniseAndGoogleText(final Bitmap imageBitmap){

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);

        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();


        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        searchQuery(generateQuery(firebaseVisionText));
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    //TODO test
    private String generateQuery(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> textBlocks = new LinkedList<>(firebaseVisionText.getTextBlocks());
        String text = firebaseVisionText.getText();


        String query = "";

        String question = "";

        question= text.substring(0, text.indexOf('?') + 1);
        question= question.substring(question.substring(0, question.lastIndexOf("\n")).lastIndexOf("\n"));
        question= question.replace("\n", " ");




        String answer = text.substring(text.indexOf('?') + 1);
        String[] answerArray = new String[3];

        for(int i = 0; i < answerArray.length; i++){
            if(answer.indexOf('\n') == -1){
                System.out.print("SHIT");
            }
            String[] generatedAnswers = generateAnswers(answer);
            answer = generatedAnswers[0];
            answerArray[i] = generatedAnswers[1];
        }

        query += question + " ";
        //TODO rename
        for(String term : answerArray){
            for(String word: term.split("\\s+")){
                query += word + " OR ";
            }
        }
        query = query.substring(0, query.length()-4);



        System.out.println(query);

        //TODO google just accepts short questions

        /**
        for(int i = 0; i < textBlocks.size(); i++){
            query += textBlocks.get(i).getText();
        }
        **/

        /**
        //TODO implement OR right
        for(int i = 0; i <= 4; i++){
            if(i == 0 || i == 4)
                query += "\"" + textBlocks.get(i).getText() + "\"";
            else
                query += "\"" + textBlocks.get(i).getText() + "\"" + "OR";
        }
**/

        return query;
    }

    private Boolean isPossibleAnswer(String answer){
        answer = answer.toLowerCase();

        if(answer.contains("x") && answer.contains("1") ||
                answer.contains("extra-leben") ||
                answer.equals("spieler") ||
                answer.equals("frage")){

            return false;
        }

        return true;
    }

    private String[] generateAnswers(String answer){
        String[] generatedAnswers = new String[2];



        if(answer.startsWith("\n")){
            answer = answer.substring(1);
        }

        if(answer.indexOf('\n') == -1){
            if(isPossibleAnswer(answer)){
                generatedAnswers = new String[]{"", answer};
            }
        }

        else{
        String theoreticalAnswer =  answer.substring(0, answer.indexOf("\n"));

        if(isPossibleAnswer(theoreticalAnswer)){
            generatedAnswers[1] = theoreticalAnswer;
            answer = answer.substring(answer.indexOf("\n"));
            generatedAnswers[0] = answer;
        }

        else{
            answer = answer.substring(answer.indexOf("\n"));
            generatedAnswers = generateAnswers(answer);
        }
        }
        return generatedAnswers;
    }
}

