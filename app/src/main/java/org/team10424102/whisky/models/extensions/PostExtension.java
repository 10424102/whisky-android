package org.team10424102.whisky.models.extensions;

import android.os.Parcelable;
import android.view.View;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface PostExtension<T extends Parcelable> {
    void render(T data, View view);

    int getLayout();

    Parcelable parseJson(JsonNode dataNode, JsonParser jp) throws JsonProcessingException;
}
