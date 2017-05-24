package com.per.maka.final_redes_app;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import common.User;

public class Control extends AppCompatActivity implements Observer {

    //Comunicacion
    Communication com;

    //UI Elements
    Button left, down, right, up;
    TextView currentUser;

    //Usuario
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        user= (User) getIntent().getSerializableExtra("user"); //Obtenemos el extra del usuario
        currentUser = (TextView) findViewById(R.id.currentUser);
        if(user!=null) {
            currentUser.setText(user.getName()); //Ponemos el nombre del usuario en el Text View para tener el identificador
        }

        //Iniciamos la comunicacion
        com= Communication.getInstance();
        //Borramos observadores previos
        com.deleteObservers();
        //Añadimos esta clse como observer de la comunicacion
        com.getInstance().addObserver(this);


        //UI Elements y sus acciones --> inicializando controles

        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);

        //Comandos
        /* //--------------------- Metodo Up
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    com.getInstance().send("UP");
                    System.out.print("enviado UP");
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    System.out.print("released");
                }
                return true;
            }//Fin onTouch condiciones
        });*/


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
