package com.example.himani_k.greeting_card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class tab_One extends Fragment {
    private static final int IMAGE_GALLERY_REQUEST = 0;
    GridView gv; public static int flag_check;
    int images[] = {R.drawable.mahashivratri, R.drawable.holi_card,
            R.drawable.summer, R.drawable.diwali,
            R.drawable.thanks_giving, R.drawable.new_year};
    Uri imageUri;
    InputStream inputStream;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root_view = inflater.inflate(R.layout.fragment_tab__one, null);
        gv = root_view.findViewById(R.id.grid_view1);
        //setting Adapter
        greeting_card_adapter adapter = new greeting_card_adapter(getActivity(), images);
        gv.setAdapter(adapter);



        //setting the alpha
        FloatingActionsMenu fab_m = root_view.findViewById(R.id.fab_menu);
        final View v=root_view.findViewById(R.id.view);
        fab_m.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuCollapsed() {
                v.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onMenuExpanded() {
                v.setVisibility(View.VISIBLE);
            }
        });

        //adding a fab
        FloatingActionButton fab1 = root_view.findViewById(R.id.second_fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_check=1;
                on_Image_from_Gallery();
            }
        });

        FloatingActionButton fab2 = root_view.findViewById(R.id.third_fab);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_check=2;
                Intent i = new Intent(getActivity(), create_custom_card.class);
                startActivity(i);
            }
        });
        return root_view;
    }

    // fetching image from the gallery
    public void on_Image_from_Gallery() {
        //prompting gallery
        Intent photopicker = new Intent(Intent.ACTION_PICK);
        //Where to find the data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String PictureDirectoryPath = pictureDirectory.getPath();
        //getting a URI representation
        Uri data = Uri.parse(PictureDirectoryPath);
        photopicker.setDataAndType(data, "image/*");
        startActivityForResult(photopicker, IMAGE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                imageUri = data.getData();
                try {
                    {
                        inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        String stringUri;
                        stringUri = imageUri.toString();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setClass(getActivity(), image_from_gallery.class);
                        intent.putExtra("KEY", stringUri);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("error ::", "" + e);
                }
            }
        }
    }
    public static int getVariable()
    {
        return flag_check;
    }

    public interface OnFragmentInteractionListener {
    }
}
