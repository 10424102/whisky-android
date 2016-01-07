package org.team10424102.whisky.components;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.enums.StringResourceProvided;
import org.team10424102.whisky.utils.EnumUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import retrofit.Retrofit;

public class DataBindingAdapters {

    @BindingAdapter({"bind:lazyImage"})
    public static void loadLazyImage(ImageView view, LazyImage image) {
        if (image == null) return;
        image.loadInto(view);
    }

    @BindingAdapter({"bind:enum"})
    public static void loadEnum(TextView view, StringResourceProvided e) {
        if (e == null) return;
        view.setText(e.getStringResId());
    }

    @BindingAdapter({"bind:enum"})
    public static void loadEnumToSpinner(Spinner spinner, StringResourceProvided e) {
        List<String> data = EnumUtils.getStringList(spinner.getContext(), ((Enum) e).getClass());
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter({"bind:date", "bind:dateFormat"})
    public static void dateToString(TextView view, Date date, String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        view.setText(dt.format(date));
    }
}
