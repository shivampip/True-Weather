package com.shivam.tree.mousam.security_tips;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shivam.tree.mousam.R;

public class SecurityTipArea extends AppCompatActivity {

    TextView headTv, areaTv, linkTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_tip_area);

        headTv= (TextView) findViewById(R.id.securityAreaHead);
        areaTv= (TextView) findViewById(R.id.securityAreaTv);
        linkTv= (TextView) findViewById(R.id.securityAreaRefTv);

        Intent intent= getIntent();
        String name= intent.getStringExtra("name");
        String data= intent.getStringExtra("data");
        final String ref= intent.getStringExtra("ref");

        headTv.setText(name);
        areaTv.setText(data);
        linkTv.setText(ref);

        linkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ref.equals("None")){
                    return;
                }
                Uri uri = Uri.parse(ref);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                SecurityTipArea.this.startActivity(browserIntent);
            }
        });

    }//onCreateEND




}//classEND
