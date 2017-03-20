package com.hanbit.testapp.member;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanbit.testapp.action.IDetail;
import com.hanbit.testapp.factory.Composite;
import com.hanbit.testapp.factory.DetailQuery;
import com.hanbit.testapp.message.MessageWrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context=MemberDetail.this;
        Intent intent=this.getIntent();
        final String id=intent.getExtras().getString("id").toString();
        HashMap<String,Object>components= (HashMap<String, Object>) init(context);

        final MemDetail memDetail=new MemDetail(context);
        IDetail service = new IDetail() {
            @Override
            public Map<String, String> detail() {
                return memDetail.detail("select _id AS id,name,phone,age,address,salary from member where _id='"+id+"';");
            }
        };
        final Map<String,String>rsMap= (Map<String,String>)service.detail();
        String temp="";
        Iterator<Map.Entry<String,String>> it=rsMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String>entry=it.next();
            temp+=entry.getKey()+","+entry.getValue()+",";
        }
        final String spec=temp;
        TextView tvIdContent= (TextView) components.get("tvDetailId");
        tvIdContent.setText(rsMap.get("id"));
        TextView tvNameContent= (TextView) components.get("tvDetailName");
        tvNameContent.setText(rsMap.get("name"));
        TextView tvPhoneContent= (TextView) components.get("tvDetailPhone");
        tvPhoneContent.setText(rsMap.get("phone"));
        TextView tvAgeContent= (TextView) components.get("tvDetailAge");
        tvAgeContent.setText(rsMap.get("age"));
        TextView tvAddressContent= (TextView) components.get("tvDetailAddress");
        tvAddressContent.setText(rsMap.get("address"));
        TextView tvSalaryContent= (TextView) components.get("tvDetailSalary");
        tvSalaryContent.setText(rsMap.get("salary"));
        setContentView((LinearLayout) components.get("llDetailFrame"));
        Button btLocation= (Button) components.get("btnDetailMyLocation");
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btGoogleMap= (Button) components.get("btnDetailGoogleMap");
        btGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btGallery= (Button) components.get("btnDetailAlbum");
        btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btMusic= (Button) components.get("btnDetailMusic");
        btMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btSMS = (Button) components.get("btnDetailSMS");
        btSMS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, rsMap.get("id")+","+rsMap.get("name")+","+rsMap.get("phone"), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,MessageWrite.class);
                intent.putExtra("idAndPhone",rsMap.get("id")+","+rsMap.get("name")+","+rsMap.get("phone"));
                startActivity(intent);

            }
        });
        Button btMail= (Button) components.get("btnDetailMail");
        btMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btCall= (Button) components.get("btnDetailCall");
        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btDial= (Button) components.get("btnDetailDial");
        btDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "));
                //intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + map.get("phoneNum")));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        Button btList= (Button) components.get("btnDetailList");
        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MemberList.class));

            }
        });
        Button btUpdate= (Button) components.get("btnDetailUpdate");
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MemberUpdate.class);
                intent.putExtra("spec",spec);
                startActivity(intent);
            }
        });
    }
    class MemDetail extends DetailQuery {
        public MemDetail(Context context) {
            super(context);
        }
        @Override
        public Map<String, String> detail(String sql) {
            Map<String,String> map = new HashMap<>();
            ArrayList<Map<String,String>> members=new ArrayList<>();
            SQLiteDatabase db=super.getDatabase();
            Cursor cursor=db.rawQuery(sql,null);
            if(cursor!=null) {
                if (cursor.moveToNext()){
                    map.put("id",cursor.getString(cursor.getColumnIndex("id")));
                    map.put("name",cursor.getString(cursor.getColumnIndex("name")));
                    map.put("age",cursor.getString(cursor.getColumnIndex("age")));
                    map.put("phone",cursor.getString(cursor.getColumnIndex("phone")));
                    map.put("address",cursor.getString(cursor.getColumnIndex("address")));
                    map.put("salary",cursor.getString(cursor.getColumnIndex("salary")));
                }
            }
            return map;
        }
}
    public HashMap<?,?>init(Context context){
        Composite compo =new Composite(context,"MemberDetail");
        compo.execute();
        setContentView(compo.getFrame());
        return compo.getComponents();
    }
}

