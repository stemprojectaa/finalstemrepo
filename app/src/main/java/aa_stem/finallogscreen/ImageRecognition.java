package aa_stem.finallogscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class ImageRecognition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);

        Button btn = (Button) findViewById(R.id.useImage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView myImageView = (ImageView) findViewById(R.id.imgview);
                myImageView.setImageResource(R.drawable.womanwithdog);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable=true;
                Bitmap myBitmap = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(),
                        R.drawable.womanwithdog,
                        options);

                Paint myRectPaint = new Paint();
                myRectPaint.setStrokeWidth(5);
                myRectPaint.setColor(Color.RED);
                myRectPaint.setStyle(Paint.Style.STROKE);


                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                tempCanvas.drawBitmap(myBitmap, 0, 0, null);

                FaceDetector faceDetector = new
                        FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                        .build();

                /*
                if(!faceDetector.isOperational()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ImageRecognition.this);


                    alertDialog.setTitle("Image Alert");
                    alertDialog.setMessage("Could not set up the face detector!..");
                    //new AlertDialog.Builder(ImageRecognition.class.setMessage("Could not set up the face detector!").show();
                    alertDialog.show();
                    return;
                }*/


                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> faces = faceDetector.detect(frame);


                for(int i=0; i<faces.size(); i++) {
                    Face thisFace = faces.valueAt(i);
                    float x1 = thisFace.getPosition().x;
                    Log.v("X pos: ",Float.valueOf(thisFace.getPosition().x).toString());
                    float y1 = thisFace.getPosition().y;
                    Log.v("Y pos: ",Float.valueOf(thisFace.getPosition().y).toString());
                    float x2 = x1 + thisFace.getWidth();
                    Log.v("X2 pos: ",Float.valueOf(x2).toString());
                    float y2 = y1 + thisFace.getHeight();
                    Log.v("Y2 pos: ",Float.valueOf(y2).toString());
                    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                }
                myImageView.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));

            }
        });


    }
}
