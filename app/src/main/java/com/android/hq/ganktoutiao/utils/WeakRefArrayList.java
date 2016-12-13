package com.android.hq.ganktoutiao.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by heqiang on 16-12-13.
 */
public class WeakRefArrayList<E> {

    private final ArrayList<WeakReference<E>> mArrayList;

    public WeakRefArrayList(int size){
        mArrayList = new ArrayList<>(size);
    }

    public E get(int index) {
        WeakReference<E> o = mArrayList.get(index);
        return o != null ? o.get() : null;
    }

    public boolean add(E object) {
        if (object == null) {
            return false;
        }
        return mArrayList.add(new WeakReference<E>(object));
    }

    public E remove(int index) {
        WeakReference<E> o = mArrayList.remove(index);
        return o != null ? o.get() : null;
    }

    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index >= 0 && index < mArrayList.size()) {
            remove(index);
            return true;
        }
        return false;
    }

    public int indexOf(Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = 0; i < mArrayList.size(); i++) {
            WeakReference<E> o = mArrayList.get(i);
            if (o != null && object.equals(o.get())) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return mArrayList.size();
    }
}
