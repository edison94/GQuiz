package local.lrobalino.gquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pista extends AppCompatActivity {

    @BindView(R.id.textoDetalle)
    TextView textoDetalle;
    @BindView(R.id.imagenDetalle)
    ImageView imagenDetalle;

    int index;
    int puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista);
        ButterKnife.bind(this);

        Pregunta preg = (Pregunta) getIntent().getExtras().getSerializable("pregunta");
        index = getIntent().getExtras().getInt("indice");
        puntos = getIntent().getExtras().getInt("puntos");
        textoDetalle.setText(preg.getText());
        imagenDetalle.setImageResource(preg.getFoto());

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imagenDetalle.startAnimation(animation);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(Pista.this, PreguntaActivity.class);
            SharedPreferences prefs = getSharedPreferences("datosJugador", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("help", true);
            editor.commit();

            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }
}
