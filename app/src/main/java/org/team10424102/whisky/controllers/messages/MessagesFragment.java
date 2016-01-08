package org.team10424102.whisky.controllers.messages;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Message;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class MessagesFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.messages_fragment, container, false);

        // 初始化 Toolbar
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        assert activity.getSupportActionBar() != null;
//        final ActionBar ab = activity.getSupportActionBar();
//        ab.setDisplayShowTitleEnabled(false);
//        ab.setDisplayHomeAsUpEnabled(false);

        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setPadding(0, DimensionUtils.getStatusBarHeight(), 0, 0);
        }

        RecyclerView list = (RecyclerView) root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(R.drawable.star_boy, "德国 Boy", "我练功发自真心", new Date()));
        messages.add(new Message(R.drawable.star_chenglong, "成龙", "duang duang duang", new Date()));
        messages.add(new Message(R.drawable.star_dali, "大力哥", "一天不喝，浑身难受！", new Date()));
        messages.add(new Message(R.drawable.star_erkang, "尔康", "紫薇，等一下！", new Date()));
        messages.add(new Message(R.drawable.star_gepao, "葛炮", "看！人群中钻出一个光头", new Date()));
        messages.add(new Message(R.drawable.star_hitler, "元首", "渣渣！", new Date()));
        messages.add(new Message(R.drawable.star_jinguanzhang, "金馆长", "哈 哈 哈", new Date()));
        messages.add(new Message(R.drawable.star_kela_af, "东仙队长", "我要金坷垃，非洲农业不发达，必须要有金坷垃！", new Date()));
        messages.add(new Message(R.drawable.star_kela_jp, "小鬼子", "我要金坷垃，日本资源太缺乏，必须要有金坷垃！", new Date()));
        messages.add(new Message(R.drawable.star_kela_jp, "小鬼子", "金坷垃！金坷垃！我的！我的！", new Date()));
        messages.add(new Message(R.drawable.star_kela_af, "东仙队长", "妈妈的！金坷垃是我的！", new Date()));
        messages.add(new Message(R.drawable.star_kela_us, "美国佬", "不能打架不能打架！金坷垃好处都有啥？谁说对了就给他！", new Date()));
        messages.add(new Message(R.drawable.star_xiangcai, "香菜", "。。。", new Date()));
        messages.add(new Message(R.drawable.star_lyf, "梁逸峰", "我有特殊的朗诵技巧", new Date()));
        messages.add(new Message(R.drawable.star_zhanghan, "张翰", "。。。", new Date()));
        messages.add(new Message(R.drawable.star_situ, "司徒", "来者可是诸葛孔明", new Date()));
        messages.add(new Message(R.drawable.star_chengxiang, "丞相", "我从未见过有如此厚颜无耻之人！", new Date()));
        messages.add(new Message(R.drawable.star_yaoming, "姚明", "。。。", new Date()));
        messages.add(new Message(R.drawable.star_leijun, "雷总", "Are you OK?", new Date()));
        list.setAdapter(new MessageAdapter(messages));


        return root;
    }
}
