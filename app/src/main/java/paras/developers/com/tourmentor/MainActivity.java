package paras.developers.com.tourmentor;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
GoogleSignInClient mGoogleSignInClient;
SignInButton btn;
private static int RC_SIGN_IN = 1889;
FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private DatabaseReference userEnd ;
String TAG ="MainActivity";
    private ProgressDialog dialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)&&ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.CAMERA)&&ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)&&ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) ) {

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1899);
                }
            } else {
                //permission has already been granted.
            }
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
btn = findViewById(R.id.googleSignIn);
btn.setSize(SignInButton.SIZE_STANDARD);
auth = FirebaseAuth.getInstance();
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
});

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode==1899&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
        Log.d("Tag","Permission Granted");
    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this,"WelCome"+ account.getDisplayName(), Toast.LENGTH_SHORT).show();

            BackgroundTask task2 = new BackgroundTask(MainActivity.this,account);
            task2.execute();


        } catch (ApiException e) {
            Log.e("Tag","SignIn");
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                          //  updateUI(user);
                         registerUsertoFirebase(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.googleSignIn), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }
    public static List<TouristDetails> getTouristInfo(String name, String email, String pUrl){

        List<TouristDetails> touristDetailsArrayList = new ArrayList<>();
        TouristDetails touristDetails = new TouristDetails(name,pUrl,email);
        touristDetailsArrayList.add(touristDetails);
        return touristDetailsArrayList;
    }
    public void registerUsertoFirebase(final FirebaseUser firebaseUser){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userEnd = mDatabase.child("TouristInfo").child(firebaseUser.getUid());

        List<TouristDetails> touristDetailsList = getTouristInfo(firebaseUser.getDisplayName().toString(),firebaseUser.getEmail(),firebaseUser.getPhotoUrl().toString());
        for(TouristDetails touristDetails1 : touristDetailsList){
            userEnd.setValue(touristDetails1).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(MainActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
               }
           }).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   Toast.makeText(MainActivity.this, "User added successfully.", Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(MainActivity.this,NavigationActivity.class);
                   i.putExtra("uid",firebaseUser.getUid());
                   if(dialog.isShowing()){
                       dialog.dismiss();
                   }
                   Toast.makeText(MainActivity.this, firebaseUser.getDisplayName().toString()+" "+firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
                   startActivity(i);
               }
           });
        }


    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {
        GoogleSignInAccount user;
        public BackgroundTask(MainActivity activity, GoogleSignInAccount user) {
            this.user = user;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Checking your details with server...");
            dialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
                firebaseAuthWithGoogle(user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

    }


}
