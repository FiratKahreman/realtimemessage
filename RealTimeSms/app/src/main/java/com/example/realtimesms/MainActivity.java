/*package com.example.realtimesms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

package com.example.realtimesms;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.security.cert.X509CRLEntry;
        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    Button kayitOlButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    EditText emailText, passwordText;
    private FirebaseAuth firebaseAuth;
    String userName="";



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null ){
            String currentEmail = firebaseUser.getEmail();
            String currentKullanici = currentEmail.substring(0, currentEmail.lastIndexOf("@"));
            ekle(currentKullanici);
        }

    }

    public void girisYap(View View) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {


                userName = email.substring(0,email.lastIndexOf("@"));
                emailText.setText("");
                passwordText.setText("");
                ekle(userName);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void kayitOl(View view){

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if(email.contains("@") ){
            userName = email.substring(0,email.lastIndexOf("@"));}
        else
            {
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        }
        if (userName.contains(".") || userName.contains("#") || userName.contains("$") || userName.contains("[") || userName.contains("]")){
           Toast.makeText(MainActivity.this,"Mail Hesabınızdan @'den önce '.' , '#' , '$' , '[' , ']' olmamalıdır.", Toast.LENGTH_LONG).show();
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {


                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, "Kullanıcı Olusturuldu", Toast.LENGTH_LONG).show();

                    userName = email.substring(0, email.lastIndexOf("@"));
                    emailText.setText("");
                    passwordText.setText("");
                    ekle(userName);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public void tanimla(){

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        kayitOlButton= findViewById(R.id.kayitOlButton);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();

    }

    public void ekle(String kadi){

        reference.child("Kullanicilar").child(kadi).child("kullaniciadi").setValue(kadi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    Intent intent= new Intent(MainActivity.this,ListActivity.class);
                    intent.putExtra("kadi",kadi);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}