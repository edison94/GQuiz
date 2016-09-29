package local.lrobalino.gquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NameActivity extends AppCompatActivity {

    @BindView(R.id.nombre)
    EditText nombre;
    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.continuar)
    Button continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.continuar)
    public void onClick() {
        String nom = nombre.getText().toString();
        if(null == nom || "".equals(nom)){
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
            error.startAnimation(animation);
            error.setText("Intoduce un nombre");

        }else{
            SharedPreferences prefs = getSharedPreferences("datosJugador",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", nom);
            editor.commit();

            Intent intent = new Intent(this, PreguntaActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
