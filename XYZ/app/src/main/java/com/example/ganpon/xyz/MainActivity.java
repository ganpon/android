package com.example.ganpon.xyz;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class MainActivity extends Activity implements Runnable, SensorEventListener {
    SensorManager sm;
    TextView tv;
    Handler h;
    float gx, gy, gz, a, b, c, d, e;
    int mm=1,qw=0,qe=0,qr=0,qt=0,mk=0,co=0,con=0;

    private String fileName = "xyz.csv";
    private String fileName1 = "stand.csv";
    private String fileName2 = "walk.csv";
    private String fileName3 = "run.csv";
    private String fileName4 = "mode.arff";
    Classifier classifierJ48 = new J48();
    Instances instancesj48;

    boolean in=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        setContentView(ll);

        tv = new TextView(this);
        ll.addView(tv);
        tv.setTextSize(35.0f);

        LinearLayout ll2 = new LinearLayout(this);
        ll2.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(ll2);

        LinearLayout ll3 = new LinearLayout(this);
        ll3.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(ll3);

        LinearLayout ll4 = new LinearLayout(this);
        ll4.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(ll4);

        h = new Handler();
        h.postDelayed(this, 500);

        super.onCreate(savedInstanceState);

        Button button1 = new Button(this);
        button1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll2.addView(button1);
        button1.setText("Stand");

        Button button2 = new Button(this);
        button2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll2.addView(button2);
        button2.setText("Walk");

        Button button3 = new Button(this);
        button3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll2.addView(button3);
        button3.setText("Run");

        Button button4 = new Button(this);
        button4.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll2.addView(button4);
        button4.setText("stop stand");

        Button button5 = new Button(this);
        button5.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll3.addView(button5);
        button5.setText("stop walk");

        Button button6 = new Button(this);
        button6.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll3.addView(button6);
        button6.setText("stop Run");

        Button button7 = new Button(this);
        button7.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll3.addView(button7);
        button7.setText("make arff");

        Button button8 = new Button(this);
        button8.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll3.addView(button8);
        button8.setText("reset arff");

        Button button9 = new Button(this);
        button9.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll4.addView(button9);
        button9.setText("weka");

        Button button10 = new Button(this);
        button10.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll4.addView(button10);
        button10.setText("start");

        Button button11 = new Button(this);
        button11.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll4.addView(button11);
        button11.setText("stop");

        Button button12 = new Button(this);
        button12.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll4.addView(button12);
        button12.setText("Clear");

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                qw=1;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qe=1;
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qr=1;
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qw=0;
            }

        });

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qe=0;
            }

        });

        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qr=0;
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qt=1;
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mm=1;
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainn(e);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mk=1;
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mk=0;
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clear();
            }
        });
    }

    @Override
    public void run() {
            FastVector out = new FastVector(3);
            out.addElement("stand");
            out.addElement("walk");
            out.addElement("run");
            Attribute mode = new Attribute("mode", out, 0);
            Attribute acceleration = new Attribute("acceleration", 1);

            Instance instance = new DenseInstance(2);
            instance.setDataset(instancesj48);
            instance.setValue(acceleration, e);
            double result = 0;
            try {
                result = classifierJ48.classifyInstance(instance);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (mk == 0) {
                tv.setText("X-軸 : " + gx + "\n"
                        + "Y-軸 : " + gy + "\n"
                        + "Z-軸 : " + gz + "\n"
                        + "合成加速度 : " + e + "\n"
                );
            } else if (mk == 1) {
                if (result == 2.0) {
                    tv.setText("run");
                    con++;
                    if(con>10){
                        tv.setText("歩きスマホ止めろ");
                    }
                }
                if (result == 1.0) {
                    tv.setText("walk");
                    con++;
                    if(con>10){
                        tv.setText("歩きスマホ止めろ");
                    }
                }
                if (result == 0.0) {
                    tv.setText("stand");
                    con=0;
                }
            }
            h.postDelayed(this, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors =
                sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            sm.registerListener(this, sensors.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gx = event.values[0];
        gy = event.values[1];
        gz = event.values[2];

        a = (float) Math.pow(gx,2);
        b = (float) Math.pow(gy,2);
        c = (float) Math.pow(gz,2);
        d = a + b + c;
        e = (float) Math.sqrt(d);

        if(co==10) {
            if (mm == 1) {
                save2(fileName4);
            }

            if (qw > 0) {
                save6(fileName1, fileName4);
            }
            if (qe > 0) {
                save7(fileName2, fileName4);
            }
            if (qr > 0) {
                save8(fileName3, fileName4);
            }
            if (qt != 0) {
                make(fileName4);
            }
            co=0;
        }
        co++;
    }

    public void save2(String file){
            try {
                FileOutputStream fileOutputStream = openFileOutput(fileName4, Context.MODE_PRIVATE);
                String writeString = ("@relation mode\n" +
                        "\n" +
                        "@attribute mode{stand,walk,run}\n" +
                        "@attribute acceleration real\n" +
                        "\n" +
                        "@data\n"
                );
                fileOutputStream.write(writeString.getBytes());
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
            mm=20;
    }

    public void save6 (String file,String file1){
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName1, MODE_APPEND);
            String writeString = (
                    "stand," + e + "\n"
            );

            fileOutputStream.write(writeString.getBytes());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void save7 (String file,String file1){
            try {
                FileOutputStream fileOutputStream = openFileOutput(fileName2, MODE_APPEND);
                String writeString = (
                        "walk," + e + "\n"
                );
                fileOutputStream.write(writeString.getBytes());
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
    }

    public void save8 (String file,String file1){
            try {
                FileOutputStream fileOutputStream = openFileOutput(fileName3, MODE_APPEND);
                String writeString = (
                        "run," + e + "\n"
                );
                fileOutputStream.write(writeString.getBytes());
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
    }

    public String read(String file) {//読み出し
        FileInputStream fileInputStream;
        String text = null;
        try {
            fileInputStream = openFileInput(file);
            byte[] readBytes = new byte[fileInputStream.available()];
            fileInputStream.read(readBytes);
            text = new String(readBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void make(String file) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName4, MODE_APPEND);
            String writeString = read(fileName1);
            fileOutputStream.write(writeString.getBytes());
            String writeString1 = read(fileName2);
            fileOutputStream.write(writeString1.getBytes());
            String writeString2 = read(fileName3);
            fileOutputStream.write(writeString2.getBytes());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        qt=0;
    }

    public void mainn(float x) {
        try {
            ArffLoader rawatf = new ArffLoader();
            File dataLoader = new File("/data/data/com.example.ganpon.xyz/files/mode.arff");
            rawatf.setFile(dataLoader);
            instancesj48 = new Instances(rawatf.getDataSet());
            instancesj48.setClassIndex(instancesj48.numAttributes()-1);

            instancesj48.setClassIndex(0);

            classifierJ48.buildClassifier(instancesj48);

            Evaluation evaluation = new Evaluation(instancesj48);
            evaluation.evaluateModel(classifierJ48, instancesj48);
            System.out.println(evaluation.toMatrixString());

            FastVector out = new FastVector(3);
            out.addElement("stand");
            out.addElement("walk");
            out.addElement("run");
            Attribute mode = new Attribute("mode", out, 0);
            Attribute acceleration = new Attribute("acceleration", 1);

            Instance instance = new DenseInstance(2);
            instance.setDataset(instancesj48);
            instance.setValue(acceleration, x);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public void www(){
        FastVector out = new FastVector(3);
        out.addElement("stand");
        out.addElement("walk");
        out.addElement("run");
        Attribute mode = new Attribute("mode", out, 0);
        Attribute acceleration = new Attribute("acceleration", 1);

        Instance instance = new DenseInstance(2);
        instance.setDataset(instancesj48);
        instance.setValue(acceleration, e);

    }

    public void clear(){
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName1, MODE_PRIVATE);
            String writeString = (
                    "\n"
            );

            fileOutputStream.write(writeString.getBytes());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName2, MODE_PRIVATE);
            String writeString = (
                    "\n"
            );

            fileOutputStream.write(writeString.getBytes());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName3, MODE_PRIVATE);
            String writeString = (
                    "\n"
            );

            fileOutputStream.write(writeString.getBytes());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        tv.setText("CLEAR CSV");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}