package org.team10424102.whisky.models.extensions.dota2;

import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.view.View;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ExtDota2MatchResultBinding;
import org.team10424102.whisky.models.extensions.PostExtensionHandler;
import org.team10424102.whisky.models.extensions.PostExtensionIdentifier;
import org.team10424102.whisky.models.extensions.RelatedGame;

@PostExtensionIdentifier("dota2_match_result")
@RelatedGame("DOTA2")
public class Dota2PostExtensionHandler implements PostExtensionHandler<Dota2MatchResult> {

    @Override
    public void render(Dota2MatchResult result, View view) {
        ExtDota2MatchResultBinding binding = DataBindingUtil.bind(view);
        binding.setResult(result);
    }

    @Override
    public int getLayout() {
        return R.layout.ext_dota2_match_result;
    }

    @Override
    public Parcelable parseJson(JsonNode dataNode, JsonParser jp) throws JsonProcessingException {
        return jp.getCodec().treeToValue(dataNode, Dota2MatchResult.class);
    }
}
