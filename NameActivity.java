package local.lrobalino.gquiz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    @BindView(R.id.img)
    ImageView img;
    private File file = new File(ruta_fotos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        ButterKnife.bind(this);
        checkCameraPermission();
        checkStoragePermission();
        checkReadStoragePermission();
    }


    @OnClick({R.id.continuar, R.id.tfoto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continuar:
                String nom = nombre.getText().toString();
                if (null == nom || "".equals(nom)) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    error.startAnimation(animation);
                    error.setText("Intoduce un nombre");

                } else {
                    SharedPreferences prefs = getSharedPreferences("datosJugador", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nombre", nom);
                    editor.commit();

                    Intent intent = new Intent(this, PreguntaActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.tfoto:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
                break;
        }
    }

    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    private void checkStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para guardar la foto!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar guardar la foto.");
        }
    }

    private void checkReadStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para guardar la foto!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar guardar la foto.");
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            
        }
    }

}
