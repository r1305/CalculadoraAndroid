package pe.as.ulima.calculadora;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Bind(R.id.op1)
    EditText op1;
    @Bind(R.id.op2)
    EditText op2;
    @Bind(R.id.suma)
    Button suma;
    @Bind(R.id.resta)
    Button resta;
    @Bind(R.id.division)
    Button div;
    @Bind(R.id.multiplicacion)
    Button multi;
    @Bind(R.id.rpta)
    TextView rpta;


    private final String NAMESPACE = "http://cliente.edu.ulima/";
    private final String URL = "http://192.168.1.33:8080/WebService/OperacionesService?WSDL";
    private final String SOAP_ACTION = "http://cliente.edu.ulima/OperacionesService/";
    String METHOD_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        suma.setOnClickListener(this);
        resta.setOnClickListener(this);
        multi.setOnClickListener(this);
        div.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.suma:
                METHOD_NAME="getSuma";
                callServiceLogin();

                break;
            case R.id.resta:
                METHOD_NAME="getResta";
                callServiceLogin();

                break;
            case R.id.multiplicacion:
                METHOD_NAME="getMultiplicar";
                callServiceLogin();

                break;
            case R.id.division:
                METHOD_NAME="getDividir";
                callServiceLogin();
                break;
        }
    }

    public void callServiceLogin(){

        Thread networkThread = new Thread() {

            @Override public void run() {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("arg0",op1.getText().toString());
                request.addProperty("arg1",op2.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {

                    androidHttpTransport.call(SOAP_ACTION, envelope); SoapPrimitive response = (SoapPrimitive)envelope.getResponse();

                    final String str = response.toString();

                    runOnUiThread (new Runnable(){ public void run() {
                        rpta.setText(str);

                    }

                    });

                }

                catch (Exception e) { e.printStackTrace();

                }

            }

        };

        networkThread.start();

    }
}


