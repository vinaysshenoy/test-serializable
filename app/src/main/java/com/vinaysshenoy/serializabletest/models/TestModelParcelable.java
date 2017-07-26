package com.vinaysshenoy.serializabletest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinaysshenoy on 26/07/17.
 */

public class TestModelParcelable extends TestModel implements Parcelable {

    public static final Creator<TestModelParcelable> CREATOR = new Creator<TestModelParcelable>() {
        @Override
        public TestModelParcelable createFromParcel(Parcel in) {
            return new TestModelParcelable(in);
        }

        @Override
        public TestModelParcelable[] newArray(int size) {
            return new TestModelParcelable[size];
        }
    };

    public TestModelParcelable(TestModel copyFrom) {
        id = copyFrom.id;
        intFields = copyFrom.intFields;
        stringFields = copyFrom.stringFields;
        floatFields = copyFrom.floatFields;
        if (copyFrom.children != null) {
            children = new TestModelParcelable[copyFrom.children.length];
            for (int i = 0; i < copyFrom.children.length; i++) {
                children[i] = new TestModelParcelable(copyFrom.children[i]);
            }
        }
    }

    private TestModelParcelable(Parcel parcel) {
        id = parcel.readLong();
        int numItems = parcel.readInt();
        intFields = new int[numItems];
        parcel.readIntArray(intFields);
        numItems = parcel.readInt();
        stringFields = new String[numItems];
        parcel.readStringArray(stringFields);
        numItems = parcel.readInt();
        floatFields = new float[numItems];
        parcel.readFloatArray(floatFields);
        numItems = parcel.readInt();
        children = new TestModelParcelable[numItems];
        if (numItems > 0) {
            parcel.readTypedArray((TestModelParcelable[]) children, CREATOR);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(intFields.length);
        parcel.writeIntArray(intFields);
        parcel.writeInt(stringFields.length);
        parcel.writeStringArray(stringFields);
        parcel.writeInt(floatFields.length);
        parcel.writeFloatArray(floatFields);
        parcel.writeInt(children == null ? 0 : children.length);
        if (children != null) {
            parcel.writeTypedArray((TestModelParcelable[]) children, 0);
        }
    }
}
