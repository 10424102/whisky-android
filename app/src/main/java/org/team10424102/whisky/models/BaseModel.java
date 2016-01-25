package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.text.WordUtils;
import org.team10424102.whisky.App;
import org.team10424102.whisky.components.BlackServerApi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class BaseModel extends BaseObservable implements Parcelable {

    protected volatile boolean initialized = false;
    protected long id;

    @Inject protected BlackServerApi api;

    public BaseModel() {
        App.getInstance().getObjectGraph().inject(this);
    }

    protected BaseModel(Parcel in) {
        App.getInstance().getObjectGraph().inject(this);
        id = in.readLong();
    }

    public BaseModel(long id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
    }

    private synchronized void _init() {
        if (initialized) return;

        init().subscribeOn(Schedulers.newThread()).subscribe(new Action1<BaseModel>() {
            @Override
            public void call(BaseModel model) {
                copy(model);
            }
        });
    }

    protected abstract Observable<? extends BaseModel> init();

    private void copy(BaseModel model) {
        Class cls = model.getClass();

        if (getClass().equals(cls)) return;

        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            String name = WordUtils.capitalize(field.getName());
            String gettterName = field.getType().equals(Boolean.class) ? "is" + name : "get" + name;
            String setterName = "set" + name;

            try {
                Method getter = cls.getDeclaredMethod(gettterName);
                Method setter = cls.getDeclaredMethod(setterName, field.getType());

                setter.invoke(this, getter.invoke(model));

            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
