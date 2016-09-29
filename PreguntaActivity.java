package local.lrobalino.gquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreguntaActivity extends AppCompatActivity {

    @BindView(R.id.truebtn)
    Button truebtn;
    @BindView(R.id.falsebtn)
    Button falsebtn;
    @BindView(R.id.pregunta)
    TextView pregunta;

    private int index;
    private ArrayList<Pregunta> preguntas;
    private boolean help;
    private int puntos;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);
        ButterKnife.bind(this);
        prefs = getSharedPreferences("datosJugador", Context.MODE_PRIVATE);
        index = prefs.getInt("index",0);
        help = prefs.getBoolean("help",false);
        //puntos = prefs.getInt("puntos",0);
        puntos = 0;
        preguntas = getPreguntas();
        setPregunta();
    }

    @OnClick({R.id.truebtn, R.id.falsebtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.falsebtn:
                evaluarRespuesta(false, view);
                break;

            case R.id.truebtn:
                evaluarRespuesta(true, view);
                break;
        }
        setPregunta();
    }

    public void evaluarRespuesta(boolean resp, View view) {
        int res;
        if (preguntas.get(index).isResp() == resp){
            res = R.string.correcto;
            puntos ++;
            if (!help){
                puntos+=2;
                help = false;
            }
        }else{
            res = R.string.error;
        }

        if (index < preguntas.size() - 1) {
            index++;
        }else{
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("puntos", puntos);
            editor.commit();
            Intent intent = new Intent(this, FinActivity.class);
            startActivity(intent);
            finish();
        }
        String r2 = getString(res) + " tienes "+puntos+ " puntos ";
        Snackbar.make(view, r2 , Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void setPregunta() {
        pregunta.setText(preguntas.get(index).getText());
    }

    private ArrayList<Pregunta> getPreguntas() {
        ArrayList<Pregunta> lista = new ArrayList<>();
        lista.add(new Pregunta(false, "¿España esta Asia?.",R.drawable.badera));
        lista.add(new Pregunta(false, "¿Portugal hace frontera con Francia?.",R.drawable.port));
        lista.add(new Pregunta(true, "¿Julio Cesar era Romano?.",R.drawable.ita));
        lista.add(new Pregunta(false, "¿Abraham Lincoln fue presidente de Rusia?.",R.drawable.rusia));
        lista.add(new Pregunta(false, "¿La Estatua de la Libertad esta en Chicago?.",R.drawable.eeuu));
        return lista;
    }

    @OnClick(R.id.pistabtn)
    public void onClick() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("puntos", puntos);
        editor.putInt("index", index);
        editor.commit();

        Intent intent = new Intent(this, Pista.class);
        intent.putExtra("pregunta",preguntas.get(index));
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }
}
