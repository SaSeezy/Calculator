package com.example.salih.calculatrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText ecran;
    String valeur="";
    String currentOperator="";
    String result="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ecran=(EditText)findViewById(R.id.editText);

        ecran.setText(valeur);

    }

    public boolean isOperator(char op){
        switch (op){
            case '+'://return true;
            case'-': //return true;
            case '*': //return true;
            case '/': //return true;
             default: return false;
        }
    }

    public void onClickNumber(View v) {
        if(result!="") {
            clear();
            updateScreen();
        }
          Button b = (Button)v;
          valeur+=b.getText();
          updateScreen();
    }

    public void updateScreen(){
            ecran.setText(valeur);
    }

    public void onClickOperator( View v){
        if (valeur=="") return;
        Button b = (Button)v;
           if(result!=""){
               String dis=result;
               clear();
               valeur=dis;

           }
           if(currentOperator!=""){
               Log.d("CalcX",""+valeur.charAt(valeur.length()-1));
               if (isOperator(valeur.charAt(valeur.length()-1))){
                   valeur=valeur.replace(valeur.charAt(valeur.length()-1),b.getText().charAt(0));
                   updateScreen();
                   return;
               }
               else {
                   getResult();
                   valeur=result;
                   result="";
               }
               currentOperator=b.getText().toString();
           }

        valeur+=b.getText();
           currentOperator=b.getText().toString();
           updateScreen();
    }

    public void clear(){
        valeur="";
           currentOperator="";
           result="";
    }

    public void onClickClear(View v){
          clear();
          updateScreen();

    }

    public void onClickEgal(View v){
          if (valeur=="") return;
          if (!getResult()) return;
              ecran.setText(valeur+"\n"+String.valueOf(result));
    }

    private double operate(String a,String b,String op){
          switch (op){
              case "+": return Double.valueOf(a) + Double.valueOf(b);
              case "-": return Double.valueOf(a) - Double.valueOf(b);
              case "*": return Double.valueOf(a) * Double.valueOf(b);
              case "^": return Math.pow(Double.valueOf(a),Double.valueOf(b));
              case "/": try {
                              return Double.valueOf(a)/Double.valueOf(b);
                         }catch (Exception e){
                               Log.d("Calc",e.getMessage());
                          }
                  default: return -1;
          }
    }

    public boolean getResult(){
        if (currentOperator=="") return false;
        String[] operation = valeur.split(Pattern.quote(currentOperator));
        if(operation.length <2) return false;

        result=String.valueOf(operate(operation[0],operation[1],currentOperator));
        return true;
    }
}
