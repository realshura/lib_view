package com.feeling.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.feeling.view.popupmenu.PopupMenu;

public class MainActivity extends AppCompatActivity {

    private PopupMenu mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String[] abs = new String[]{"关于我们", "检查更新", "意见反馈","关于我们", "检查更新", "意见反馈"};
                mPopupMenu = new PopupMenu(MainActivity.this, abs);
                mPopupMenu.setOnMenuClickListener(new PopupMenu.OnMenuClickListener() {
                    @Override
                    public void onItemClick(int index) {
                        Toast.makeText(MainActivity.this, abs[index], Toast.LENGTH_SHORT).show();
                    }
                });
                mPopupMenu.showLocation(R.id.start_btn);// 设置弹出菜单弹出的位置
            }
        });
    }
}
