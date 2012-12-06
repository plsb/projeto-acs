/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.control;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class DateEditText extends EditText {
    private boolean isUpdating;
     
     private int positioning[] = { 0, 1, 3, 4, 6, 7, 8, 9, 10 };
 
    public DateEditText(Context context, AttributeSet attrs, int defStyle) {
     super(context, attrs, defStyle);
     initialize();
 
    }
 
    public DateEditText(Context context, AttributeSet attrs) {
     super(context, attrs);
     initialize();
 
    }
 
    public DateEditText(Context context) {
     super(context);
     initialize();
 
    }
 
    public String getCleanText() {
     String text = DateEditText.this.getText().toString();
 
    text.replaceAll("[^0-9]*", "");
     return text;
 
    }
 
    private void initialize() {
 
            final int maxNumberLength = 8;
             this.setKeyListener(keylistenerNumber);
         
            this.setText("  /  /     ");
             this.setSelection(1);
         
            this.addTextChangedListener(new TextWatcher() {
                 public void afterTextChanged(Editable s) {
                 String current = s.toString();
             
               
                 if (isUpdating) {
                 isUpdating = false;
                 return;
             
                }
             

                 String number = current.replaceAll("[^0-9]*", "");
                 if (number.length() > 8)
                 number = number.substring(0, 8);
                 int length = number.length();
             
                /* *** */
                 String  paddedNumber = padNumber(number, maxNumberLength);
             
                /* *** */
                 String dia = paddedNumber.substring(0, 2);
                 String mes = paddedNumber.substring(2, 4);
                 String ano = paddedNumber.substring(4, 8);
                 
               
                 
                 
                 /* *** */
                    String date =  dia + "/" + mes + "/" +ano;                  
                     
                /* ***  */
                 isUpdating = true;
                 DateEditText.this.setText(date);
             
                 DateEditText.this.setSelection(positioning[length]);
                 
                 
                 
                 }
                public void beforeTextChanged(CharSequence s, int start, int count,
                 int after) {
             
                }
             
                public void onTextChanged(CharSequence s, int start, int before,
                 int count) {
             
                  }
                 });    
           
     }
 
    protected String padNumber(String number, int maxLength) {
     String padded = new String(number);
     for (int i = 0; i < maxLength - number.length(); i++)
     padded += " ";
     return padded;
 
    }
 
    private final KeylistenerNumber keylistenerNumber = new KeylistenerNumber();
 
    private class KeylistenerNumber extends NumberKeyListener {
 
    public int getInputType() {
     return InputType.TYPE_CLASS_NUMBER
     | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
 
    }
 
    @Override
     protected char[] getAcceptedChars() {
         return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
     
           }
     }
}
