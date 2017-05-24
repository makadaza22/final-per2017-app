package com.per.maka.final_redes_app;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 * Created by Maka on 5/14/17.
 */

public class Communication extends Observable implements Runnable {

    //TAG para imprimir
    private static final String TAG ="Communication";

    //referencia para el singleton
    private static Communication ref;

    //socket
    private Socket socket;
    private boolean running;

    //--------------------------IMPORTANTE, DIRECCIÓN IP variable
    //private String ip="192.168.149.2";
    //private String ip=" 10.0.2.2";
    private String ip="10.0.2.2";
    private int port =1200;
    private boolean conected;
    private boolean reset;
    private boolean error;

    private Communication(){
        //Inicializamos todo
        socket= null;
        running =true;
        conected =true;
        reset=false;
        Log.d(TAG, "Instantiated");
        error=false;

    }

    public static Communication getInstance(){
        //Luego instanciamos con patron singleton
        if(ref==null){
            ref=new Communication();
            //Iniciamos el hilo
            Thread t=new Thread(ref);
            t.start();
        }
        return ref;

    }

    @Override
    public void run() {

        Log.d(TAG, "Thread Started");
        //Mientras este corriendo
        while(running){

            try{

                if(conected){ //Si no está conectado
                    if(reset){
                        //Mientras el socket sea diferente de null
                        if (socket!=null){
                            //intente resetearlo
                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.d(TAG, "Reseting");
                            }finally {
                                socket=null;
                            }

                        }
                        reset=false;
                    }
                    conected = !connect();
                }else{ //Si se conecta
                    if (socket!=null){
                        receive(); //Que reciba
                    }
                }

            }catch (SocketTimeoutException e){

            }catch (IOException e){
                Log.d(TAG, "[ CONNECTION LOST ]");

                retry();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        try {
            socket.close();
        } catch (IOException e) {
            Log.d(TAG, "[ ERROR CLOSING ]");
        }finally {
            socket=null;
        }

    } //Fin RUN

    //------------------------------------Establecer conexion

    private boolean connect(){

        try{
            InetAddress address= InetAddress.getByName(ip); //traducimos la ip

            //Creamos el nuevo socket y conectamos
            socket= new Socket(address, port);
            Log.d(TAG, "Connected width: "+socket.toString());
            setChanged();
            notifyObservers("Connected"); //notificar la conexión
            clearChanged();
        }catch (UnknownHostException e){
            Log.d(TAG, "UNKOWN SERVER");
            return false;
        } catch (IOException e) {

            Log.d(TAG, "ERROR CONNECTING");
            if(!error){
                setChanged();
                notifyObservers("no_connected");
                clearChanged();
                error=true;
            }
            return false;
        }

        return true;
    }

    //-----------------------------------Metodo de recibir

    private void receive() throws IOException, ClassNotFoundException {
        //Deserializamos
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object recibido= ois.readObject();
        //Notificamos el objeto a los observadores
        setChanged();
        notifyObservers(recibido);
        clearChanged();
    }

    //-------------------------------------Enviar

    public void send(final Object obj){
        //Si el socket no está vacio
        if(socket!=null){
            Thread t=new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ObjectOutputStream oos= new ObjectOutputStream(socket.getOutputStream());

                                oos.writeObject(obj);

                                oos.flush();

                            } catch (IOException e) {
                                //e.printStackTrace();
                                Log.d(TAG, "Send Failed");
                            }


                        }
                    });
            t.start();
            //Serializamos y enviamos
        }else{
            Log.d(TAG, "no connected");
        }
    }

    //---------------------------------------Intentar conectarse de nuevo
    public void retry() {
        conected = true;
        reset = true;
        error = false;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
