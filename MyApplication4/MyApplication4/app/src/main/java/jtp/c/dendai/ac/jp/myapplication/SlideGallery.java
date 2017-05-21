package jtp.c.dendai.ac.jp.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SlideGallery extends Activity implements DialogInterface.OnClickListener,OnItemClickListener,Runnable{
    /** Called when the activity is first created. */

    private ProgressDialog dialog;
    private ImageView imageView;
    private Gallery gallery;
    private Bitmap[] thumbnail;
    private List<String> dirList = new ArrayList<String>();
    private List<String> tmp = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new AlertDialog.Builder(this)//BUGCHECK
                .setMessage("ギャラリーのPath:　"+Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/")
                .setNeutralButton("OK", this)
                .show();

        if(!sdcardReadReady()){
            new AlertDialog.Builder(this)
                    .setMessage("SDカードにアクセスできません。")
                    .setNeutralButton("OK", this)
                    .show();
            return;
        }
       // dirList.add(Environment.getExternalStorageDirectory().getPath());
        dirList.add(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/");

        int i = 0;
        int j = 0;
        while(dirList.size() > i){
            File subDir = new File(dirList.get(i));
            String subFileName[] = subDir.list();
            j = 0;
            if(subFileName != null){
                while(subFileName.length > j){
                    File subFile = new File(subDir.getPath() + "/" + subFileName[j]);
                    if(subFile.isDirectory()){
                        String _st =subDir.getPath() + "/" + subFileName[j];
                        dirList.add(_st);
                    }else if(subFile.getName().endsWith("jpg") || subFile.getName().endsWith("JPG")){
                        tmp.add(subDir.getPath() + "/" + subFileName[j]);
                    }
                    j++;
                }
            }
            i++;
        }

        if(tmp.isEmpty()){
            new AlertDialog.Builder(this)
                    .setMessage("画像のデータがありません")
                    .setNeutralButton("OK", this)
                    .show();
            return;
        }

        //tmp.toArray();
        dialog = new ProgressDialog(this);
        dialog.setMessage("読み込みを行います。");
        dialog.show();
        thumbnail = new Bitmap[tmp.size()];
        new Thread(this).start();
        gallery = (Gallery)findViewById(R.id.gallery1);
        gallery.setSpacing(8);
        gallery.setAdapter(new GalleryAdapter());
        gallery.setOnItemClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView1);
        Bitmap picture = BitmapFactory.decodeFile(tmp.get(0));
        imageView.setImageBitmap(picture);
    }

    public void run(){
        for(int i = 0 ; i < tmp.size(); i++){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(tmp.get(i),options);
            int height = options.outHeight;
            int scale = height /50;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            thumbnail[i] = BitmapFactory.decodeFile(tmp.get(i),options);
        }
        dialog.dismiss();
    }

    private boolean sdcardReadReady(){
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state)||Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which){
            case DialogInterface.BUTTON_NEUTRAL:
                dialog.dismiss();
                finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if(arg0 == gallery){
            Bitmap picture = BitmapFactory.decodeFile(
                    tmp.get(arg2)
            );
            imageView.setImageBitmap(picture);
        }
    }
    public class GalleryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return tmp.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ImageView view;
            if(convertView == null){
                view = new ImageView(SlideGallery.this);
                view.setImageBitmap(thumbnail[position]);
            }else{
                view = (ImageView)convertView;
            }
            return view;
        }
    }
}
