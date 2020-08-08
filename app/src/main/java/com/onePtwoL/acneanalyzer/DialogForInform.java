package com.onePtwoL.acneanalyzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogForInform extends Dialog {

    private Context context;
    private Button button0, button1, button2, button3, button4, button5;
    private TextView typeNameTextView, typeContextTextView, typeNumTextView;

    public DialogForInform(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_for_inform);

        button0 = findViewById(R.id.inform_btn0);
        button1 = findViewById(R.id.inform_btn1);
        button2 = findViewById(R.id.inform_btn2);
        button3 = findViewById(R.id.inform_btn3);
        button4 = findViewById(R.id.inform_btn4);
        button5 = findViewById(R.id.inform_btn5);

        typeNameTextView = findViewById(R.id.inform_acne_name_textView);
        typeContextTextView = findViewById(R.id.inform_acne_context_textView);
        typeNumTextView = findViewById(R.id.inform_acne_num_textView);

        button0.setClickable(false);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeNameTextView.setText("화이트헤드 : Whitehead");
                typeContextTextView.setText(
                        "모공이 열리지 않은 상태에서 피지가 축적된 것으로 하얗게 보이는 여드름입니다. " +
                        "흔히 ‘좁쌀 여드름’이라고 불리며 이마나 턱라인 쪽으로 쉽게 발생합니다."+
                        "모공이 열리지 않은 상태가 유지 되면 염증성으로 발전될 확률이 있습니다.");
                typeNumTextView.setText(" 1 ");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeNameTextView.setText("블랙헤드 : Blackhead");
                typeContextTextView.setText(
                        "표면이 열려 있는 상태로 피지가 축적되고, 산화되어 검게 보이는 여드름입니다. "
                        + "치료하기 매우 쉬운 상태이며, 피지 분비가 미간과 코 위주로 많이 발생하고 관리하지 않으면 모공이 확장되기 쉽습니다.");
                typeNumTextView.setText(" 2 ");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeNameTextView.setText("구진 : Papule");
                typeContextTextView.setText(
                        "모공이 닫혀있는 작고 딱딱하게 융기된 붉은 여드름입니다. " +
                        "크기가 큰 경우 발염감과 통증이 동반될 수 있습니다. 고름은 잡히지 않은 상태이며, 색소 침착 가능성이 높습니다.");
                typeNumTextView.setText(" 3 ");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeNameTextView.setText("농포 : Pustule");
                typeContextTextView.setText(
                        "구진에서 더 진행한 단계로, 고름이 발생한 상태의 여드름입니다. " +
                        "조직이 약해져 있어 피부층 손상을 받기 쉬운 상태로 흉터가 남을 가능성이 높습니다. ");
                typeNumTextView.setText(" 4 ");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeNameTextView.setText("결절, 낭종");
                typeContextTextView.setText(
                        "결절은 농포가 피부 속에서 터지면 염증이 더욱 심해지고 크기가 커진 단계입니다. " +
                        "낭종은 결절에서 발달된 형태로 모공 벽이 파열된 여러 개의 모공들이 합쳐져 거대한 염증 반응이 생긴 단계를 말합니다. " +
                        "따라서 모공 벽은 심각하게 파열된 상태이며, 압출 하지 않아도 흉터가 남을 확률이 매우 높습니다.");
                typeNumTextView.setText(" 5 ");
            }
        });
    }
}