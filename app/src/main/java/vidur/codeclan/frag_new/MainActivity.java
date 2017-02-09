package vidur.codeclan.frag_new;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    Button bt1;
    private static final int l = 1;
    StorageReference my_Storageref;
    public ProgressDialog my_Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.button1);
        my_Dialog = new ProgressDialog(this);
        my_Dialog.setProgressStyle(3);

        my_Storageref = FirebaseStorage.getInstance().getReference();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_Dialog.setMessage("Uploading the Image");
                my_Dialog.show();
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, l);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == l && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            Log.i("TAG", "uri found");
            StorageReference file_Path = my_Storageref.child("Photos").child(uri.getLastPathSegment());
            file_Path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Added to firebase storage!!", Toast.LENGTH_LONG).show();
                    my_Dialog.dismiss();
                }
            });
        }
    }
}
