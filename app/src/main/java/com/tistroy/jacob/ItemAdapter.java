package com.tistroy.jacob;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

public class ItemAdapter extends BaseAdapter {

    // 셀을 만들기 위해서 필요한 정보들
    Context context; //layout inflater를 만들기 위한 문맥 정보
    LayoutInflater inflater; // xml로 만든 layout을 view로 전개하기 위한 객체
    int layout; // layout 파일의 이름


    List<Item> data;
    //생성자

    public ItemAdapter(Context context, List<Item> data, int layout) {
        super();
        this.context = context;
        this.data = data;
        this.layout = layout;

        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).getItemname();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //실제 셀의 모양을 결정하는 메서드
    //i는 각 셀의 인덱스
    //View는 화면에 보여지는 뷰로 처음에는 null이 넘어오고 두번쨰 부터는 이전에 출력한 뷰가 넘어옴.
    //ViewGroup은 이 항목을 사용하는 AdapterView
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int pos = i;
        //이전에 출력된 뷰가 없다면 = 처음 화면이라면
        if (view == null) {
            //이떄 inflate(레이아웃 이름 ,부모이름(parent class) , 본인이 부모가 될지 에 대한 여부 (true,false)
            view = inflater.inflate(layout, viewGroup, false);
        }
        // i번쨰 데이터 찾아오기
        Item item = data.get(i);
        //데이터 출력
        TextView txtName = (TextView) view.findViewById(R.id.itemname);
        txtName.setText(item.getItemname());

        TextView txtprice = (TextView) view.findViewById(R.id.price);
        txtName.setText("$"+item.getPrice());

        ImageView imageView = (ImageView) view.findViewById(R.id.itemimage);

        // 아이폰은 interface를 이용하고
        //자바는 상속을 이용함

        Handler handler = new Handler(Looper.getMainLooper()) {
            public void handleMessgae(Message message) {
                Bitmap bitmap = (Bitmap)message.obj;
                //이미지 뷰에 이미지 출력
                imageView.setImageBitmap(bitmap);

            }
        };
        class TreadEx extends Thread {
            public void run() {
                try {
                    InputStream inputStream = new URL("192.168.10.34:9000/member/download?path=" + item.getPictureurl()).openStream();

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Message message = new Message();

                    handler.sendMessage(message);
                } catch (Exception e) {
                    Log.e("다운로드 예외", e.getLocalizedMessage());
                }
            }
        }

        new ThreadEx().start();
        return view;

    }
}
