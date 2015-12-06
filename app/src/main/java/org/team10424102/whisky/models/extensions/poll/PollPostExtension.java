package org.team10424102.whisky.models.extensions.poll;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ExtPollBinding;
import org.team10424102.whisky.models.extensions.PostExtension;
import org.team10424102.whisky.models.extensions.PostExtensionIdentifier;
import org.team10424102.whisky.models.extensions.RelatedGame;

import java.util.Arrays;

@PostExtensionIdentifier("poll")
public class PollPostExtension implements PostExtension<Poll> {

    @Override
    public void render(Poll poll, View view) {
        ExtPollBinding binding = DataBindingUtil.bind(view);
        Context context = view.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RadioGroup group = binding.group;
        binding.setPoll(poll);
        for (String opt : poll.getOptions()) {
            RadioButton button = (RadioButton) inflater.inflate(R.layout.poll_option, null);
            button.setText(opt);
            group.addView(button);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.ext_poll;
    }

    @Override
    public Parcelable parseJson(JsonNode dataNode, JsonParser jp) throws JsonProcessingException {
        Poll poll = new Poll();
        String content = dataNode.get("content").asText();
        String[] parts = content.split(":");
        poll.setQuestion(parts[0]);
        poll.setOptions(Arrays.asList(parts[1].split(",")));
        return poll;
    }
}
