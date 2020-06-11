package com.example.pendu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GamePendu extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout container;
    private Button btn_send;
    private TextView lettres_tapees;
    private EditText letter;
    private ImageView image;

    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<>();
    private boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendu_game);
        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.btn_send);
        letter = (EditText) findViewById(R.id.et_letter);
        image = (ImageView) findViewById(R.id.iv_pendu);
        lettres_tapees = (TextView) findViewById(R.id.tv_lettres_tapees);

        initGame();
        btn_send.setOnClickListener(this);

    }

    public void initGame() {
        word = "ORDINATEUR";
        win = false;
        error = 0;
        found = 0;
        lettres_tapees.setText("");
        image.setBackgroundResource(R.drawable.first);

        container.removeAllViews();

        for (int i = 0; i < word.length(); i++) {
            TextView oneletter = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            container.addView(oneletter);
        }
    }

    @Override
    public void onClick(View v) {

        String letterFromInput = letter.getText().toString().toUpperCase();
        letter.setText("");

        if (letterFromInput.length() > 0) {
            if(!letterAlreadyUsed(letterFromInput.charAt(0), listOfLetters)){
                listOfLetters.add(letterFromInput.charAt(0));
                checkIfLetterIsInWord(letterFromInput, word);
            }
            // le jeu pendu gagner
            if(found == word.length()){
                win = true;
                Toast.makeText(getApplicationContext(), "Victoire", Toast.LENGTH_SHORT).show();

            }
            // lettre ne se trouve pas dans le mot
            if(!word.contains(letterFromInput)){
                error ++;

            }
            if(error == 6){
                win = false;
                Toast.makeText(getApplicationContext(), "Perdu", Toast.LENGTH_SHORT).show();
            }
            //appel de la fct afficher les lettres fause
            showAlLetters(listOfLetters);
        }


    }

    public boolean letterAlreadyUsed(char a, List<Character> listOfLetters) {

        for (int i = 0; i < listOfLetters.size(); i++) {
            if (listOfLetters.get(i) == a) {
                Toast.makeText(getApplicationContext(), "Vous avez déjà entrée cette lettres !!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;

    }
    public void checkIfLetterIsInWord(String letter, String word){
        for(int i = 0; i < word.length(); i++){
            if(letter.equals(String.valueOf(word.charAt(i)))){
                TextView tv = (TextView) container.getChildAt(i);
                tv.setText((String.valueOf(word.charAt(i))));
                found++;
            };
        }
    }

    //afficher les lettres qui sont tapé ( et pas dans le mots )
    public void showAlLetters(List<Character> listOfLetters){
        String chaine = "";
        for(int i = 0; i < listOfLetters.size(); i++){
            chaine += listOfLetters.get(i) + "\n";

        }
        if(!chaine.equals("")){
            lettres_tapees.setText(chaine);
        }
    }
}
