package fr.geoffreypruvost.projetpruvost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.Normalizer;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView tvNettoyage;
    private TextView tvReversed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        tvNettoyage = findViewById(R.id.clean_str);
        tvReversed = findViewById(R.id.swapped_str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onClickNettoyer(View v){
        String clean_str = Normalizer.normalize(editText.getText(), Normalizer.Form.NFD);
        clean_str = clean_str.replaceAll("[^\\p{ASCII}]", ""); // remove accents
        clean_str = clean_str.replaceAll("[^a-zA-Z]", ""); // remove punctuation
        tvNettoyage.setText(clean_str.toLowerCase());
    }

    public void onClickReverseStr(View v){
        tvReversed.setText(new StringBuilder(tvNettoyage.getText()).reverse().toString());
    }
}
