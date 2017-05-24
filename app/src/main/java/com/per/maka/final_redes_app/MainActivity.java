package com.per.maka.final_redes_app;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import common.User;

public class MainActivity extends AppCompatActivity implements Observer { //Esta es la clase del login y aqui pasa practicamente todoo!

    //---------------- UI

    Typeface title_font;
    Typeface body_font;
    EditText inputPlayer;
    TextView title;
    Button submitPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------- UI ELEMENTS

        title_font = Typeface.createFromAsset(getAssets(),  "fonts/BebasNeue-Regular.ttf");
        body_font = Typeface.createFromAsset(getAssets(),  "fonts/bariol-regular.ttf");

        inputPlayer = (EditText) findViewById(R.id.editPlayer);
        inputPlayer.setTypeface(body_font);

        title = (TextView) findViewById(R.id.editText);
        inputPlayer.setTypeface(body_font);

        submitPlayer = (Button) findViewById(R.id.submitPlayer);
        submitPlayer.setTypeface(title_font);

        //Añadimos esta clse como observer de la comunicacion


        //------------ ACCIONES PARA EL REGISTRO

        submitPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrar();
            }
        });

    }//FIN ONCREATE


    //-------------------- Método para registrar nuevos usuarios


    public void registrar(){

        // Registrar usuarios

        //si no hay ningún campo vacio enviamos la info con los datos, si hay alguno vacio generamos un feedback
        if (inputPlayer.getText().length() < 1 ) {
            Communication.getInstance().addObserver(this);
            Toast.makeText(getApplicationContext(), "You will need an username, pleasee fill", Toast.LENGTH_SHORT).show();
        } else {
            String name=inputPlayer.getText().toString();
            Communication.getInstance().send(new User(name, 0, 0));
        }

    }

    @Override
    public void update(Observable observable, Object data) {

        //Si llega un numero 3 es que fue registrado exitosamente, si no pues es que el usuario ya existe
        if(data instanceof Integer) {
            if ((Integer) data == 3) {

                //Si llega el 3 se hace el intent
                Intent a = new Intent(MainActivity.this, Control.class);
                User u=new User(inputPlayer.getText().toString(), 0, 0);

                //---El cero en el equipo significa que aun no ha elegido un equipo, esto cambia solo cuando elige uno. cada equipo tiene un ID y una camiseta
                //---El cero en el ID es para que el servidor lo identifique como usuario nuevo para registrar.

                a.putExtra("user",u); //pasamos el usuario como extra
                startActivity(a);
            } else {
                runOnUiThread(msg);
            }
        }
    }

    //Feedback si no fue registrado porque ya existe
    private Runnable msg = new Runnable()
    {
        public void run()
        {
            Toast.makeText(MainActivity.this,"Username already exist",Toast.LENGTH_LONG).show();
        }
    };

    //------Toast para dar feedback

    private Runnable message = new Runnable()
    {
        public void run()
        {
            Toast.makeText(MainActivity.this,"Iniciando...",Toast.LENGTH_LONG).show();
        }
    };


    //Cerrar la aplicación al dar click atrás
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

}//Fin clase
