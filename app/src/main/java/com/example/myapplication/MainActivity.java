package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.example.myapplication.databinding.ActivityMainBinding;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<Button> buttons = new ArrayList<>();
    int nbrHasard;
    int paf = 0;
    int flop = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        buttons.add(binding.buttonView4);
        buttons.add(binding.buttonView5);
        buttons.add(binding.buttonView6);
        buttons.add(binding.buttonView7);
        bougeLeLapin();
        editor.putInt("score", 0);
        editor.apply();
        for (Button b : buttons) {
            b.setOnClickListener(view -> {
                reagirClic(view);
                new Handler().postDelayed(() -> {
                    bougeLeLapin();
                }, 1000); // Wait for 1 second (1000 milliseconds)
            });
        }
        }



    private void reagirClic(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int bestScore = sharedPreferences.getInt("score", 0);
                Button BoutonDuLapin = buttons.get(nbrHasard);
                if (view == BoutonDuLapin) {
                    Drawable myImage = getResources().getDrawable(R.drawable.rabbit);
                    int width = myImage.getIntrinsicWidth() / 2;
                    int height = myImage.getIntrinsicHeight() / 2;
                    myImage.setBounds(0, 0, width, height);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BoutonDuLapin.setBackground(myImage);
                            TextView textView = findViewById(R.id.textView1);
                            flop++;
                            if(bestScore<flop){
                                TextView meilleurScore = findViewById(R.id.bestScore);
                                editor.putInt("score", flop);
                                editor.apply();
                                meilleurScore.setText("Best score : " + sharedPreferences.getInt("score", 0));
                            }
                            textView.setText("flops : " + flop);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Drawable myImage = getResources().getDrawable(R.drawable.cross);
                            int width = myImage.getIntrinsicWidth() / 2;
                            int height = myImage.getIntrinsicHeight() / 2;
                            myImage.setBounds(0, 0, width, height);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.setBackground(myImage);
                                    TextView textView = findViewById(R.id.textView2);
                                    paf++;
                                    textView.setText("pafs : " + paf);
                                }
                            });
                        }
                    });
                };
            }

        }).start();
}







    private void bougeLeLapin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable myImage = getResources().getDrawable(R.drawable.my_image);
                int width = myImage.getIntrinsicWidth() / 2;
                int height = myImage.getIntrinsicHeight() / 2;
                myImage.setBounds(0, 0, width, height);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Button b : buttons) {
                            b.setBackground(myImage);
                        }
                    }
                });
                Random random = new Random();
                nbrHasard = random.nextInt(4);
            }
        }).start();
    }

}