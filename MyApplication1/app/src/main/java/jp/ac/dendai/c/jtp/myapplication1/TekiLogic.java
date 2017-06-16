package jp.ac.dendai.c.jtp.myapplication1;
import android.content.Context;
import jp.ac.dendai.c.jtp.myapplication1.mono.Mono;
import jp.ac.dendai.c.jtp.myapplication1.mono.Saka;
import jp.ac.dendai.c.jtp.myapplication1.mono.Teki;
import jp.ac.dendai.c.jtp.myapplication1.mono.Zako;

public class TekiLogic {
    private static double period = 500;
    private final Context context;
    private final HanteiList<Mono> list;
    private double tic;

    static boolean u = true;

    public TekiLogic(Context context, HanteiList<Mono> list) {
        this.context = context;
        this.list = list;
        tic = 0;
        list.add(createTeki());
    }

    private Mono createTeki() {
        return new Teki(context, 400, 50);
    }

    private Mono createZako() {
        return new Zako(context, 0, 50);
    }

    private Mono createSaka() {
        return new Saka(context, 200, 50);
    }


    public void step(double tstep, int width, int height) {
        tic += tstep;
        while (tic > period) {
            list.add(createTeki());
            tic -= period;
        }
    }

    public void step2(double tstep, int width, int height) {
        tic += tstep;
        while (tic > period) {
            list.add(createZako());
            tic -= period;
        }
    }


    public void stepSaka(double tstep, int width, int height) {

        tic += tstep;
        while (tic > period && u) {
            list.add(createSaka());
            tic -= period;
            u = false;
        }
    }
}