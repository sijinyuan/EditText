package com.hhzmy.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MyBuyActivity extends AppCompatActivity {

    private Button zhuce;
    private EditText phonenum;
    private EditText pass;
    private Button mylogin;
    private boolean isHidden=true;
    private ImageView rbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buy);

        initView();

    }



    private void initView() {
        zhuce=(Button)findViewById(R.id.zhuce);
        phonenum=(EditText)findViewById(R.id.phonenum);
        pass=(EditText)findViewById(R.id.passworld);
        mylogin=(Button)findViewById(R.id.my_bt_login);
        rbtn=(ImageView)findViewById(R.id.iv);
        phonenum.addTextChangedListener(new TextWatcher() {
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
                    phonenum.setText(sb.toString());
                    phonenum.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    //设置EditText文本为可见的
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    rbtn.setImageResource(R.mipmap.icon_display);
                } else {
                    //设置EditText文本为隐藏的
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    rbtn.setImageResource(R.mipmap.icon_hidden);
                }
                isHidden = !isHidden;
                pass.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = pass.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
        mylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean flag=isMobileNO(phonenum.getText().toString());
                boolean isTel=true;//标记位：true-是手机号码；false-不是手机号码
                //判断输入的用户名是否是电话号码
                if(flag){
//                    String text=phonenum.getText().toString().trim();
//                    String phonenum=text.replace("","");
                    for(int i=0;i<phonenum.getText().toString().length();i++){
                        char c=phonenum.getText().toString().charAt(i);
                        if(!Character.isDigit(c)){
                            isTel=false;
                            break;//只要有一位不符合要求退出循环
                        }else{
                            isTel=false;
                        }
                    }
                }
               /*只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆*/
                if(TextUtils.isEmpty(phonenum.getText())){
                    Toast.makeText(MyBuyActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();;
                }else if(isTel){
                    Toast.makeText(MyBuyActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();;

                }else if(TextUtils.isEmpty(pass.getText())){
                    Toast.makeText(MyBuyActivity.this,"密码名不为空！",Toast.LENGTH_SHORT).show();;

                }else if(pass.getText().length()>=6){
                    Toast.makeText(MyBuyActivity.this,"可以使用！",Toast.LENGTH_SHORT).show();;
                }else if(pass.getText().length()<6){
                    Toast.makeText(MyBuyActivity.this,"密码不能小于六位！",Toast.LENGTH_SHORT).show();;

                }

            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyBuyActivity.this,YanzhengActivity.class);
                startActivity(intent);
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
