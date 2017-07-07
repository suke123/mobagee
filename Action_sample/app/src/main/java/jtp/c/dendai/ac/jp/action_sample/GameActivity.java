package jtp.c.dendai.ac.jp.action_sample;

/**
 * Created by taka on 2017/07/05.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import jtp.c.dendai.ac.jp.action_sample.Rakka;

public class GameActivity extends Activity implements GameView.Callback, UnderView.Callback {

    private GameView gameView;
    private UnderView underView;
    private String s = new String();
    Rakka ra = new Rakka();
    public boolean isDead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gameView = new GameView(this, this);
        gameView.setCallback(this);

        setContentView(gameView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.startDrawThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stopDrawThread();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameOver() {
        ra.rakka();
        if (ra.getrakka() < 0) {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
            isDead = true; //ゲームオーバーになったからisDeadをtrueにする
            return;
        }
        underView = new UnderView(this, this);

        underView.setCallback(this);
        setContentView(underView);

        s = Integer.toString(ra.getrakka());
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGameOver2() {
        ra.rakka();
        if (ra.getrakka() < 0) {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
            isDead = true;
            return;
        }
        gameView = new GameView(this, this);

        gameView.setCallback(this);
        setContentView(gameView);

        s = Integer.toString(ra.getrakka());
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void ToTitle() {
        // ゲーム画面を起動
        Intent intent = new Intent();
        intent.setClassName("jtp.c.dendai.ac.jp.action_sample", "jtp.c.dendai.ac.jp.action_sample.MainActivity");

        /*おまけ
        * タイトル画面にスコアを渡す
        */
        intent.putExtra("SCORE", underView.getScore());
        /*
        * おまけ終わり
        */

        startActivity(intent);

        /*おまけ
        * ゲーム画面に表示されているスコアを初期化するメソッドを呼ぶ
        */
        gameView.ResetScore();
        underView.ResetScore();
        /*
        * おまけ終わり
        */
    }

    //ゲームオーバーになったかどうかを返す
    public boolean getIsDead() {
        return isDead;
    }
}
