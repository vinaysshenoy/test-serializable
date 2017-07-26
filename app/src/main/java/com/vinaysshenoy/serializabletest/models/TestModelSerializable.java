package com.vinaysshenoy.serializabletest.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by vinaysshenoy on 26/07/17.
 */

public class TestModelSerializable extends TestModel implements Serializable {

    public TestModelSerializable(TestModel copyFrom) {
        id = copyFrom.id;
        intFields = copyFrom.intFields;
        stringFields = copyFrom.stringFields;
        floatFields = copyFrom.floatFields;
        if (copyFrom.children != null) {
            children = new TestModelSerializable[copyFrom.children.length];
            for (int i = 0; i < copyFrom.children.length; i++) {
                children[i] = new TestModelSerializable(copyFrom.children[i]);
            }
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeLong(id);
        out.writeInt(intFields.length);
        for (int itemIndex = 0; itemIndex < intFields.length; itemIndex++) {
            out.writeInt(intFields[itemIndex]);
        }
        out.writeInt(stringFields.length);
        for (int itemIndex = 0; itemIndex < stringFields.length; itemIndex++) {
            out.writeUTF(stringFields[itemIndex]);
        }
        out.writeInt(floatFields.length);
        for (int itemIndex = 0; itemIndex < floatFields.length; itemIndex++) {
            out.writeFloat(floatFields[itemIndex]);
        }
        out.writeInt(children == null ? 0 : children.length);
        if(children != null) {
            for (int itemIndex = 0; itemIndex < children.length; itemIndex++) {
                out.writeObject(children[itemIndex]);
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        id = in.readLong();

        int numItems = in.readInt();
        intFields = new int[numItems];
        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {
            intFields[itemIndex] = in.readInt();
        }

        numItems = in.readInt();
        stringFields = new String[numItems];
        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {
            stringFields[itemIndex] = in.readUTF();
        }

        numItems = in.readInt();
        floatFields = new float[numItems];
        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {
            floatFields[itemIndex] = in.readFloat();
        }

        numItems = in.readInt();
        children = new TestModel[numItems];
        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {
            children[itemIndex] = (TestModel) in.readObject();
        }
    }
}
