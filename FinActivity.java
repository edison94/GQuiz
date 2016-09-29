package local.lrobalino.gquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinActivity extends AppCompatActivity {

    @BindView(R.id.Puntos)
    TextView Puntos;
    @BindView(R.id.textoFin)
    TextView textoFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);
        ButterKnife.bind(this);
        SharedPreferences prefs = getSharedPreferences("datosJugador", Context.MODE_PRIVATE);
        String nom = prefs.getString("nombre", "Hacker");
        String puntos = prefs.getInt("puntos", 0)+ "";
        Puntos.setText(puntos);
        textoFin.setText(nom+" tu puntuacion es:");

        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}