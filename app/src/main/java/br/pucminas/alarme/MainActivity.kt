package br.pucminas.alarme

import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("presenca-detectada")

        val self = this

        val labelAlerta = findViewById<TextView>(R.id.labelMsgSensorPIR)

        reference.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {

                val valorSensor = dataSnapshot.getValue(Boolean::class.java)

                if (valorSensor == true)
                {
                    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    val r = RingtoneManager.getRingtone(applicationContext, notification)
                    r.play()
                    labelAlerta.setText("Atenção! Presença detectada!!!")
                }
                else
                {
                    labelAlerta.setText("Nenhuma presença detectada!!!")
                }
            }

            override fun onCancelled(error: DatabaseError)
            {
                Toast.makeText(self,  "Erro: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }
}
