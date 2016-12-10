package com.notissu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.notissu.R;

/**
 * Created by Sunghyeok on 2016-12-09.
 */

public class EditKeywordFragment extends Fragment{
    View rootView;
    TextView textView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_update_list, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView_current_updatetime);

        //1시간
        View.OnClickListener oneHourListener = new View.OnClickListener (){
            public void onClick(View v) {
                textView.setText("현재 업데이트 주기 : 1시간");
            }
        };
        RadioButton radioboxOneHour = (RadioButton) rootView.findViewById(R.id.radiobox_one_hour);
        radioboxOneHour.setOnClickListener(oneHourListener);

        //3시간
        View.OnClickListener threeHourListener = new View.OnClickListener (){
            public void onClick(View v) {
                textView.setText("현재 업데이트 주기 : 3시간");
            }
        };
        RadioButton radioboxThreeHour = (RadioButton) rootView.findViewById(R.id.radiobox_three_hour);
        radioboxThreeHour.setOnClickListener(threeHourListener);

        //6시간
        View.OnClickListener sixHourListener = new View.OnClickListener (){
            public void onClick(View v) {
                textView.setText("현재 업데이트 주기 : 6시간");
            }
        };
        RadioButton radioboxSixHour = (RadioButton) rootView.findViewById(R.id.radiobox_six_hour);
        radioboxSixHour.setOnClickListener(sixHourListener);

        //12시간
        View.OnClickListener halfDayListener = new View.OnClickListener (){
            public void onClick(View v) {
                textView.setText("현재 업데이트 주기 : 12시간");
            }
        };
        RadioButton radioboxHalfDay = (RadioButton) rootView.findViewById(R.id.radiobox_half_day);
        radioboxHalfDay.setOnClickListener(halfDayListener);

        //1일
        View.OnClickListener oneDayListener = new View.OnClickListener (){
            public void onClick(View v) {
                textView.setText("현재 업데이트 주기 : 1일");
            }
        };
        RadioButton radioboxOneDay = (RadioButton) rootView.findViewById(R.id.radiobox_one_day);
        radioboxOneDay.setOnClickListener(oneDayListener);

        return rootView;
    }
    public void clickbox(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case R.id.radiobox_one_hour:
                if (checked)

                    break;
            case R.id.radiobox_three_hour:
                if (checked)

                    break;
            case R.id.radiobox_six_hour:
                if (checked)

                    break;
            case R.id.radiobox_half_day:
                if (checked)

                    break;
            case R.id.radiobox_one_day:
                if (checked)

                    break;
        }
    }
}
