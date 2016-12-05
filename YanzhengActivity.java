package com.hhzmy.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class YanzhengActivity extends AppCompatActivity {

    private Button btny;
    private EditText yanphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanzheng);
        initView();


    }

    private void initView() {
        btny=(Button)findViewById(R.id.yanzhengtiao);
        yanphone=(EditText)findViewById(R.id.yanphone);
        yanphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0)
                    return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9)
                                && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    yanphone.setText(sb.toString());
                    yanphone.setSelection(index);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=isMobileNO(yanphone.getText().toString());
                boolean isTel=true;//标记位：true-是手机号码；false-不是手机号码
                //判断输入的用户名是否是电话号码
                if(flag){
                    for(int i=0;i<yanphone.getText().toString().length();i++){
                        char c=yanphone.getText().toString().charAt(i);
                        if(!Character.isDigit(c)){
                            isTel=false;
                            break;//只要有一位不符合要求退出循环
                        }else{
                            isTel=false;
                        }
                    }
                }
               /*只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆*/
                if(TextUtils.isEmpty(yanphone.getText())){
                    Toast.makeText(YanzhengActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();;
                }else if(isTel){
                    Toast.makeText(YanzhengActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();;

                }
            }
        });

    }

    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        else{
            return mobiles.matches(telRegex);
        }
    }

}
