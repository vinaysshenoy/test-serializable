package com.vinaysshenoy.serializabletest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.vinaysshenoy.serializabletest.models.TestModel;
import com.vinaysshenoy.serializabletest.models.TestModelParcelable;
import com.vinaysshenoy.serializabletest.models.TestModelSerializable;

/**
 * Created by vinaysshenoy on 26/07/17.
 */

public class DeserializeTestActivity extends AppCompatActivity {

    private static final String TAG = "DeserializeTestActivity";

    private TextView serializeDurationTextView;
    private TextView deserilizeDurationTextView;

    public static void launch(@NonNull Context context, @NonNull Type type, @NonNull TestModel testModel) {

        final Intent intent = new Intent(context, DeserializeTestActivity.class);
        intent.putExtra("type", type);

        final long startTime = System.nanoTime();

        if (Type.PARCELABLE == type) {
            intent.putExtra("model", (TestModelParcelable) testModel);
        } else if (Type.SERIALIZABLE == type) {
            intent.putExtra("model", (TestModelSerializable) testModel);
        }

        final long endTime = System.nanoTime();
        final long duration = endTime - startTime;
        intent.putExtra("serialize_duration", duration);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        serializeDurationTextView = (TextView) findViewById(R.id.tv_serialize);
        deserilizeDurationTextView = (TextView) findViewById(R.id.tv_deserialize);

        final Bundle extras = getIntent().getExtras();

        final long serializeDuration = extras.getLong("serialize_duration");
        serializeDurationTextView.setText(getString(R.string.serialize_time, serializeDuration));

        final Type type = (Type) extras.getSerializable("type");
        TestModel testModel = null;
        final long startTime = System.nanoTime();
        if (Type.PARCELABLE == type) {
            testModel = (TestModelParcelable) extras.getParcelable("model");
        } else if (Type.SERIALIZABLE == type) {
            testModel = (TestModelSerializable) extras.getSerializable("model");
        }
        final long endTime = System.nanoTime();
        final long duration = endTime - startTime;
        Log.d(TAG, "Test Model ID: " + testModel.id);
        deserilizeDurationTextView.setText(getString(R.string.deserialize_time, duration));
    }

    public enum Type {
        SERIALIZABLE,
        PARCELABLE
    }
}
