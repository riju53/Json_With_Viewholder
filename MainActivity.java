package com.example.rijunath.json_sample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String url="http://220.225.80.177/onlineShoppingapp/Show.asmx/GetProduct?catid=4";
    ArrayList<Product_setget>arrayList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lv);
        Dataexecute da=new Dataexecute();
        da.execute("Dataexecute");
    }
    public class Dataexecute extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            JSONParser jp=new JSONParser();
            JSONObject jobj=jp.getJsonFromURL(url);
            try {
                JSONArray jarr=jobj.getJSONArray("Products");
                arrayList=new ArrayList<Product_setget>();
                for (int i=0;i<jarr.length();i++){
                    JSONObject obj=jarr.getJSONObject(i);
                    String Product_Id=obj.getString("Product_Id");
                    String Category_Id=obj.getString("Category_Id");
                    String Product_Image=obj.getString("Product_Image");

                    Product_setget ps=new Product_setget();
                    ps.setCategory_Id(Product_Id);
                    ps.setProduct_Id(Category_Id);
                    ps.setProduct_Image(Product_Image);
                    arrayList.add(ps);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lv.setAdapter(new Myadp());
        }
    }
    public class Myadp extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder h= new Holder();
            if (convertView==null){
                LayoutInflater inf=getLayoutInflater();
                convertView=inf.inflate(R.layout.next,parent,false);
                h.pid=(TextView)convertView.findViewById(R.id.pid);
                h.cid=(TextView)convertView.findViewById(R.id.cid);
                h.imid=(WebView)convertView.findViewById(R.id.imid);
                convertView.setTag(h);
            }
            else
            h=(Holder)convertView.getTag();
            Product_setget psg=new Product_setget();
            psg=arrayList.get(position);
            h.pid.setText(psg.getProduct_Id());
            h.cid.setText(psg.getCategory_Id());
            h.imid.loadUrl(psg.getProduct_Image());
            return convertView;
        }
    }
    public class Holder{
        TextView pid;
        TextView cid;
        WebView imid;
    }
}
