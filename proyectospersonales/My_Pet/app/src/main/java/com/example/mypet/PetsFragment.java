package com.example.mypet;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PetsFragment extends Fragment {

    List<PetProfile> petList ;
    ImageButton btnAddPet;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_pets, container, false);
        petList = new ArrayList<>();
        btnAddPet = root.findViewById(R.id.btnAddPet);
        petList = new ArrayList<>();

        //Ejemplo de simulacion de mascotas- necesario base de datos

        String ejemploUri = "https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/322868_1100-800x825.jpg";
        String ejemploUri2 = "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2021/08/gato-perfil-2436385.jpg?itok=x06Dod9u";
        String ejemploUri3 ="https://imagenesbonitas.co/wp-content/uploads/2016/01/selfie5.png";
        String ejemploUri4 = "https://p0.pikist.com/photos/520/752/dog-pet-puppy-profile-dog-animals-animal-beach-holiday-cute.jpg";
        String ejemploUri5 = "";
        Uri path = Uri.parse(ejemploUri);
        Uri path2 = Uri.parse(ejemploUri2);
        Uri path3 = Uri.parse(ejemploUri3);
        Uri path4 = Uri.parse(ejemploUri4);
        Uri path5 = Uri.parse(ejemploUri5);
            // Usar por si no pone imagen de la mascota
            if (String.valueOf(path5).length() == 0) {

                path5 = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.drawable.signo);
            }
            petList.add(new PetProfile("Lulu", 1000, path));
            petList.add(new PetProfile("Zeus", 1001, path2));
            petList.add(new PetProfile("Lucas", 1002, path3));
        petList.add(new PetProfile("Simon", 1003, path4));
        petList.add(new PetProfile("Vanica", 2014, path5));

            ListAdapter listAdapter = new ListAdapter(petList, getContext(), new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PetProfile pet) {
                    Intent intent = new Intent(getContext(), SeePet.class);
                    intent.putExtra("petId", String.valueOf(pet.getIdPet()));
                    intent.putExtra("petUri", String.valueOf(pet.getUriPet()));
                    startActivity(intent);

                }
            });
            RecyclerView recyclerView = root.findViewById(R.id.recyclerId);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(listAdapter);
        //fin simulacion

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),CargarImagen.class);
                startActivity(i);
            }
        });


        return  root;
    }

}