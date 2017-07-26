package com.vinaysshenoy.serializabletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vinaysshenoy.serializabletest.models.TestModel;
import com.vinaysshenoy.serializabletest.models.TestModelParcelable;
import com.vinaysshenoy.serializabletest.models.TestModelSerializable;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TestModel testModel = generateTestModel(4, new Random());
        findViewById(R.id.button_serializable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeserializeTestActivity.launch(MainActivity.this, DeserializeTestActivity.Type.SERIALIZABLE, new TestModelSerializable(testModel));
            }
        });
        findViewById(R.id.button_parcelable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeserializeTestActivity.launch(MainActivity.this, DeserializeTestActivity.Type.PARCELABLE, new TestModelParcelable(testModel));
            }
        });
    }

    private TestModel generateTestModel(int counter, Random random) {

        final TestModel testModel = new TestModel();
        testModel.id = random.nextLong();
        testModel.stringFields = new String[30];
        for (int i = 0; i < 30; i++) {
            testModel.stringFields[i] = Long.toHexString(random.nextLong());
        }
        testModel.intFields = new int[150];
        for (int i = 0; i < 150; i++) {
            testModel.intFields[i] = random.nextInt();
        }
        testModel.floatFields = new float[100];
        for (int i = 0; i < 100; i++) {
            testModel.floatFields[i] = random.nextFloat();
        }

        if (counter > 0) {
            testModel.children = new TestModel[3];
            final int newCounterValue = counter - 1;
            for (int i = 0; i < 3; i++) {
                testModel.children[i] = generateTestModel(newCounterValue, random);
            }
        }
        return testModel;
    }
}
