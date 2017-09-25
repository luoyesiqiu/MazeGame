package com.woc.dfsmaze;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    static  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(new MainView(this));
        showToast(this,"点击屏幕让小球走迷宫");
        context=this;
    }

    static Toast toast;
    public static void showToast(Context context,String text)
    {
        if(toast==null)
        {
            toast=Toast.makeText(context,text,Toast.LENGTH_LONG);
        }else
        {
            toast.setText(text);
        }
        toast.show();
    }



}
