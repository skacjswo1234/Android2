package com.koreait.examplerecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
리스트 형태로 보일 때 각각의 아이템은 뷰로 만들어짐
각각의 아이템을 위한 뷰는 뷰홀더에 담아둠
뷰홀더의 역할을하는 클래스 : PersonAdapter 클래스의 내부클래스 인 ViewHolder
 */

public class PersonAdapter  extends  RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    ArrayList<Person> items = new ArrayList<Person>();

    /*
어댑터가 구현해야하는 중요한 메서드 3가지
onCreateViewHolder - 뷰홀더 객체가 만들어 질 때 생성되는 콜백 메서드
onBindViewHolder - 뷰홀더 객체가 재사용될때 호출되는 콜백 메서드
getItemCount - 어댑터가 관리하는 아이템의 개수
 */

    // 뷰를 화면에 보여줄 때 현재 화면에 인플레이션을 해야하므로 첫 번째 파라미터를 사용해서 현재 화면의 정보를 갖고있는
    // Context 객체를 가져와 사용함
    // 뷰의 타입을 위한 정수값이 파라미터로 전달 / 일반적으로는 아이템의 뷰를 한가지로 하기 때문에 거의 사용하는 경우는 없음
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        // XML을 인플레이션 시킬 대상 현재 액티비티
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.person_item, parent, false);
        return new ViewHolder(itemView);
    }

    // 재활용 할 수 잇도록 뷰홀더 객체를 첫 번째 파라미터로 전달
    // 첫 번째 파라미터로 전달된 뷰홀더 현재 아이템에 맞는 데이터만 설정하도록 코딩함.
    @Override
    public void onBindViewHolder( PersonAdapter.ViewHolder holder, int position) {
        Person item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Person item) {
        items.add(item);
    }

    public void setItems(ArrayList<Person> items) {
        this.items = items;
    }
    public void getItems(int position) {
         items.get(position);
    }
    public void setItem(int position, Person item){
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public ViewHolder(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.textView);
            tv2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Person item) {
            tv1.setText(item.getName());
            tv2.setText(item.getMobile());
        }
    }

}
