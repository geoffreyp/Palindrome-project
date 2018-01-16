package fr.geoffreypruvost.projetpruvost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.List;

import fr.geoffreypruvost.projetpruvost.helper.Helper;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView tvNettoyage;
    private TextView tvReversed;
    private SpannableString ssClean;
    private SpannableString ssReversed;

    /* Override methods */
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_palin_alea:
                try {
                    List<String> palindromes = Helper.getAllPalindromes(new InputStreamReader(getAssets().open("palindromes.txt")));
                    String palin = palindromes.get((int)(Math.random() * (palindromes.size()-1)));

                    editText.setText(palin);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.menu_est_ce_palin:
                try {
                    List<String> palindromes = Helper.getAllPalindromes(new InputStreamReader(getAssets().open("estcepalindromes.txt")));
                    String palin = palindromes.get((int)(Math.random() * (palindromes.size()-1)));

                    editText.setText(palin);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu_a_propos:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("A propos de ...");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder1.setView(R.layout.activity_apropos);
                }
                builder1.setCancelable(true);
                builder1.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    /* on click methods */
    public void onClickNettoyer(View v){
        String clean_str = Normalizer.normalize(editText.getText(), Normalizer.Form.NFD);
        clean_str = clean_str.replaceAll("[^\\p{ASCII}]", ""); // remove accents
        clean_str = clean_str.replaceAll("[^a-zA-Z]", ""); // remove punctuation
        tvNettoyage.setText(clean_str.toLowerCase());
    }

    public void onClickReverseStr(View v){
        tvReversed.setText(new StringBuilder(tvNettoyage.getText()).reverse().toString());
    }

    public void onClickCompare(View v){
        ssClean = new SpannableString(tvNettoyage.getText());
        ssReversed = new SpannableString(tvReversed.getText());

        if(tvNettoyage.getText().length() > 0 && tvReversed.getText().length() > 0) {
            new Thread(new ComparePalin()).start();
        }else {
            Toast.makeText(this,"Veuillez nettoyer et retourner le texte",Toast.LENGTH_SHORT).show();
        }
    }


    class ComparePalin implements Runnable{

        @Override
        public void run() {

            for (int i = 0; i < tvNettoyage.getText().length() && i < tvReversed.getText().length(); i++){

                if(tvReversed.getText().charAt(i) == tvNettoyage.getText().charAt(i)) {
                    ssClean.setSpan(new BackgroundColorSpan(Color.GREEN), 0,i+1,0);
                    ssReversed.setSpan(new BackgroundColorSpan(Color.GREEN), 0,i+1,0);

                }else{
                    ssClean.setSpan(new BackgroundColorSpan(Color.RED), i,i+1,0);
                    ssReversed.setSpan(new BackgroundColorSpan(Color.RED), i,i+1,0);
                    setText(tvNettoyage, ssClean);
                    setText(tvReversed, ssReversed);
                    break;
                }

                setText(tvNettoyage, ssClean);
                setText(tvReversed, ssReversed);
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    System.err.println(e);
                }
            }
        }

        private void setText(final TextView text,final CharSequence value){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(value);
                }
            });
        }
    }
}
