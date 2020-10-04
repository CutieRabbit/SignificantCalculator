package com.example.significantcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTextviewMovement();
        initilizeButton();
        initButtomEvent();
    }

    Map<String, Button> clickable = new HashMap<>();
    Boolean resultPrinted = true;
    Boolean stopAnimation = false;
    public void setTextviewMovement(){
        TextView textView = this.findViewById(R.id.textView3);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void addSpecialRow(String[] array){
        TableLayout layout = (TableLayout) findViewById(R.id.tableLayout);
        TableRow row = new TableRow(this);
        for(String str : array) {
            String text = str;
            Button button = new Button(this);
            button.setText(text);
            row.addView(button);
            clickable.put(text, button);
        }
        layout.addView(row);
    }

    public void initilizeButton(){
        TableLayout layout = (TableLayout) findViewById(R.id.tableLayout);
        addSpecialRow(new String[]{"(",")","C","AC"});
        String[] array = {"+", "-", "*", "/"};
        for(int i = 0; i < 3; i++){
            TableRow row = new TableRow(this);
            for(int j = 0; j <= 2; j++){
                int number = i*3+j+1;
                String text = String.valueOf(number);
                Button button = new Button(this);
                button.setText(text);
                row.addView(button);
                clickable.put(text, button);
            }
            String text = String.valueOf(array[i]);
            Button button = new Button(this);
            button.setText(text);
            clickable.put(text, button);
            row.addView(button);
            layout.addView(row);
        }
        addSpecialRow(new String[]{"","0",".","/"});
        addSpecialRow(new String[]{"","","","="});
    }
    public String fixString(String str){
        str = str.replaceAll("\\(", " ( ");
        str = str.replaceAll("\\)", " ) ");
        str = str.replaceAll("\\+", " + ");
        str = str.replaceAll("-", " - ");
        str = str.replaceAll("\\*", " * ");
        str = str.replaceAll("/", " / ");
        return str.trim();
    }
    public void initButtomEvent(){
        for(Map.Entry<String, Button> entry : clickable.entrySet()) {
            String str = entry.getKey();
            Button button = entry.getValue();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = findViewById(R.id.textView3);
                    SpannableString ss = (SpannableString) textView.getText();
                    String text = ss.toString();
                    if(resultPrinted){
                        text = "";
                        resultPrinted = false;
                    }
                    text += ((Button) v).getText();
                    textView.setText(text);
                }
            });
        }
        Button ac = clickable.get("AC");
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.textView3);
                SpannableString ss = (SpannableString) textView.getText();
                String text = ss.toString();
                textView.setText("");
            }
        });
        Button c = clickable.get("C");
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.textView3);
                SpannableString ss = (SpannableString) textView.getText();
                String text = ss.toString();
                if(text.length() == 0){
                    return;
                }
                textView.setText(text.subSequence(0, text.length()-1));
            }
        });
        Button calc = clickable.get("=");
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.textView3);
                SpannableString ss = (SpannableString) textView.getText();
                String text = ss.toString();
                String fixStr = fixString(text).replaceAll("  ", " ");
                try {
                    textView.setText(ReversePolish.result(ShutingYard.postfix(fixStr)));
                }catch (Exception e){
                    textView.setText("Error X_X");
                }
                resultPrinted = true;
            }
        });
    }
}
