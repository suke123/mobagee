package jp.ac.dendai.c.jtp.myapplication1.mono;
import android.content.Context;

import jp.ac.dendai.c.jtp.myapplication1.R;
public class Haikei extends AbstractMono {
    private static final int[] ids = {R.drawable.haikei1};
    public Haikei(Context context){
        super(context,ids);
        p.set(0,50);
    }
    @Override
    public void move(int width, int height) {
    }
}