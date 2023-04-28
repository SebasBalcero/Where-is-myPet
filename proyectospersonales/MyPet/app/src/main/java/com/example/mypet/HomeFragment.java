package com.example.mypet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    ImageView ivProfile,ivBackground;
    Button btnSignOut;
    EditText edtPhone1, edtPhone2,edtEmail,edtName;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth firebaseAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        ivProfile = root.findViewById(R.id.ivProfile);
        ivBackground = root.findViewById(R.id.ivBackground);
        edtName = root.findViewById(R.id.edtName);
        btnSignOut = root.findViewById(R.id.btnSignOut);
        edtEmail = root.findViewById(R.id.edtEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        edtPhone1 = root.findViewById(R.id.edtPhone1);
        edtPhone2 = root.findViewById(R.id.edtPhone2);
        FirebaseUser userF = firebaseAuth.getCurrentUser();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(),gso);
        ivBackground.setImageResource(R.drawable.fondoc7);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if(acct != null){
            edtName.setText(acct.getDisplayName());
            edtName.setEnabled(false);
            edtEmail.setText(acct.getEmail());
            edtEmail.setEnabled(false);
            Picasso.get().load(acct.getPhotoUrl()).into(ivProfile);

        }
        else if(userF != null){
            Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(ivProfile);
            edtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
            edtEmail.setEnabled(false);

        }
        else{
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code

                            try {
                                edtName.setText(object.getString("name"));
                                edtName.setEnabled(false);
                                Picasso.get().load(object.getJSONObject("picture").getJSONObject("data").getString("url")).into(ivProfile);
                                // USAR COMO ID SI VIENE DE FACEBOOK--->  object.getString("id")
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }
        if  (ivProfile.getDrawable() == null)
        {
            ivProfile.setImageResource(R.drawable.profile);


        }



        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fire;
                fire = FirebaseAuth.getInstance();
                FirebaseUser userR = fire.getCurrentUser();
                if(acct != null){
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent i = new Intent(getContext(), MainActivity.class);
                            startActivity(i);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    });
                }
                else if(userR != null){
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                else{
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                }

            }
        });
        return root;
    }
}