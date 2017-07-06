package jtp.c.dendai.ac.jp.action_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Droid droid;
    //int lastscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startButton = (ImageButton) findViewById(R.id.start_btn);
        TextView textView = (TextView) findViewById(R.id.scoreText);

        Intent intent = getIntent();
        textView.setText("LAST GAME ： " + intent.getIntExtra("SCORE", 0));

        startButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(android.view.View v) {
                // ゲーム画面を起動
                Intent intent = new Intent();
                intent.setClassName("jtp.c.dendai.ac.jp.action_sample", "jtp.c.dendai.ac.jp.action_sample.GameActivity");
                startActivity(intent);
            }
        });
    }

}