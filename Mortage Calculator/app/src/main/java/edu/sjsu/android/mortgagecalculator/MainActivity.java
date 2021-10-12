package edu.sjsu.android.mortgagecalculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Initialize components of App
    Button btnCalc; // Calculate button
    TextView txtMP, txtIR; // text views for Monthly Payment(MP) and Interest Rate (IR)
    EditText edtTxtAB; // edit text or the Amount to be Borrowed (AB)
    SeekBar seekBarIR; // seek bar for IR
    RadioGroup rdGroup; // radio group
    RadioButton rbFifteen, rbTwenty, rbThirty; // radio buttons for different year options
    CheckBox cbTI; // check box for tax/insurance inclusion

    int months = 0;
    boolean tiFlag; //the flag to see if insurance and tax are included in calc
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set views
        setViews();
        // set listeners
        setListeners();
    }
    private void setListeners() {
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthlyPaymentCalc();
            }
        });
        cbTI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // check TI box is checked, if so use value
                tiFlag = isChecked;
            }
        });
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbFifteen:
                        // convert the years in months
                        months = 15 * 12;
                        break;
                    case R.id.rbThirty:
                        months = 30 * 12;
                        break;
                    case R.id.rbTwenty:
                        months = 20 * 12;
                        break;
                }
            }
        });
        seekBarIR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                // display the seek bar value
                txtIR.setText("Interest Rate: " + progress + "%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    private void setViews() {
        txtMP = findViewById(R.id.txtMonthlyPayment);
        edtTxtAB = findViewById(R.id.edtAmountBorrowed);
        seekBarIR = findViewById(R.id.seekBarInterestRate);
        txtIR = findViewById(R.id.txtInterestRate);
        cbTI = findViewById(R.id.cbTI);
        rdGroup = findViewById(R.id.rdGroup);
        rbFifteen = findViewById(R.id.rbFifteen);
        rbTwenty = findViewById(R.id.rbTwenty);
        rbThirty = findViewById(R.id.rbThirty);
        btnCalc = findViewById(R.id.btnCalculate);
        txtIR.setText("Interest Rate: " + seekBarIR.getProgress() + "%");
    }
    void monthlyPaymentCalc() {
        float j = 0.0f; // monthly interest rate
        String borrowed = edtTxtAB.getText().toString();
        if (TextUtils.isEmpty(borrowed)) {
            Toast.makeText(this, "Please enter valid amount value", Toast.LENGTH_SHORT).show();
            return;
        }
        float p = Float.parseFloat(borrowed); // principal
        float iR = seekBarIR.getProgress(); // interest rate
        if (iR != 0) {
            j = iR / 1200;
        }
        int t = months; // loan duration
        if (t == 0) {
            Toast.makeText(this, "Please select duration of loan", Toast.LENGTH_SHORT).show();
            return;
        }
        double insurance = 0.0; // insurance
        if (tiFlag) {
            insurance = (p * 0.1) / 100;
        }
        double payment = (p * (j / (1 - (1 / Math.pow((1 + j), t))))) + insurance;
        // set the calculated value for the monthly payment
        txtMP.setText("Calculated Monthly Payment:$ " + String.format("%.2f",payment));
    }
}
