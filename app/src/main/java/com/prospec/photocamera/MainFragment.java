package com.prospec.photocamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ImageView photoImageView;
    private Uri uri;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Initila Image View
        photoImageView = getView().findViewById(R.id.imvPhoto);

//        Gallry Controller
        gallryController();

//        Camera Controller
        cameraController();

    }

    private void cameraController() {
        ImageView imageView = getView().findViewById(R.id.imvCamera);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pathFileString = Environment.getExternalStorageDirectory() + "/" + "MasterUNG";
                Log.d("6SepV1", "pathFileString ==> " + pathFileString);

                File file = new File(pathFileString);
                if (!file.exists()) {
                    file.mkdirs();
                }

                Random random = new Random();
                int i = random.nextInt(1000);
                File cameraFile1 = new File(file, "master_" + Integer.toString(i) + ".jpg");

                uri = Uri.fromFile(cameraFile1);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 2);

            }   // onClick
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case 1:
                    uri = data.getData();

                    showImage();

                    break;
                case 2:
                    showImage();
                    break;
            }
        }   // if

    }   // onActivityResult

    private void showImage() {
        try {

            Bitmap bitmap = BitmapFactory
                    .decodeStream(getActivity()
                            .getContentResolver().openInputStream(uri));
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,
                    800, 600, false);
            photoImageView.setImageBitmap(bitmap1);

        } catch (Exception e) {
            Log.d("6SepV1", "e showImage ==> " + e.toString());
        }
    }

    private void gallryController() {
        ImageView imageView = getView().findViewById(R.id.imvGallry);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
