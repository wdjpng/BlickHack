package com.wdjpng.blickhack;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;

public class QueryGenerator {

    protected String generateQuery(FirebaseVisionText firebaseVisionText) throws IncorrectTextException {
        String text = firebaseVisionText.getText();

        if(!(text.contains("?") && text.contains("\n"))){
            throw new IncorrectTextException();
        }

        String query = "";


        String question = text.substring(0, text.indexOf('?') + 1);
        question = question.substring(question.substring(0, question.lastIndexOf("\n")).lastIndexOf("\n"));
        question = question.replace("\n", " ");


        String answer = text.substring(text.indexOf('?') + 1);
        ArrayList<String> answerArray = new ArrayList<>();

        int i = 0;
        while(true){
            String[] generatedAnswers = generateAnswers(answer);
            answer = generatedAnswers[0];

            if(answer.equals("")){
                break;
            }

            answerArray.add(i, generatedAnswers[1]);

            i++;
        }

        query += question + " ";
        for (String term : answerArray) {
            for (String word : term.split("\\s+")) {
                query += word + " OR ";
            }
        }

        query = query.substring(0, query.lastIndexOf("OR"));


        return query;
    }

    private Boolean isPossibleAnswer(String answer) {
        answer = answer.toLowerCase();

        if (answer.contains("x") && answer.contains("1") ||
                answer.contains("extra-leben") ||
                answer.equals("spieler") ||
                answer.equals("frage")) {

            return false;
        }

        return true;
    }

    private String[] generateAnswers(String answer) {
        String[] generatedAnswers = new String[2];


        if (answer.startsWith("\n")) {
            answer = answer.substring(1);
        }

        if (answer.indexOf('\n') == -1) {
            if (isPossibleAnswer(answer)) {
                generatedAnswers = new String[]{"", answer};
            }
            else{
                return new String[]{"",""};
            }
        } else {
            String theoreticalAnswer = answer.substring(0, answer.indexOf("\n"));

            if (isPossibleAnswer(theoreticalAnswer)) {
                generatedAnswers[1] = theoreticalAnswer;
                answer = answer.substring(answer.indexOf("\n"));
                generatedAnswers[0] = answer;
            } else {
                answer = answer.substring(answer.indexOf("\n"));
                generatedAnswers = generateAnswers(answer);
            }
        }
        return generatedAnswers;
    }
}
