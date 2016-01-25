package org.team10424102.whisky.models;

import android.databinding.Bindable;
import android.os.Parcel;
import android.support.annotation.StringRes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.team10424102.whisky.App;
import org.team10424102.whisky.BR;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.GameManager;

import java.util.Date;
import java.util.List;

import rx.Observable;

public class Activity extends BaseModel {

    /**
     * 标题，20 字
     */
    private String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    /**
     * 内容，500 字
     */
    private String content;

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    /**
     * 地点，100 字
     */
    private String location;

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        notifyPropertyChanged(BR.location);
    }

    /**
     * 开始时间，手机当前的时区，JSON 数据是 UTC 时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @Bindable
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    /**
     * 结束时间，手机当前的时区，JSON 数据是 UTC 时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    @Bindable
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }

    /**
     * 注册截止时间，手机当前时区，JSON 数据是 UTC 时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date deadline;// registration

    @Bindable
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
        notifyPropertyChanged(BR.deadline);
    }

    /**
     * 活动创建时间，手机当前时区，JSON 数据是 UTC 时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationTime;

    @Bindable
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
        notifyPropertyChanged(BR.creationTime);
    }

    /**
     * 活动类型：内战(CIVIL_WAR)，比赛(MATCH)
     */
    private String type;

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    /**
     * 发起人
     */
    private User promoter;

    @Bindable
    public User getPromoter() {
        return promoter;
    }

    public void setPromoter(User promoter) {
        this.promoter = promoter;
        notifyPropertyChanged(BR.promoter);
    }

    /**
     * 相关游戏
     */
    private Game game;

    @Bindable
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        notifyPropertyChanged(BR.game);
    }

    /**
     * 相关用户组
     */
    private UserGroup group;

    @Bindable
    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
        notifyPropertyChanged(BR.group);
    }

    @JsonSetter
    public void setGroup(long id) {
        this.group = new UserGroup(id);
    }

    /**
     * 活动封面
     */
    private Image cover;

    @Bindable
    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
        notifyPropertyChanged(BR.cover);
    }

    /**
     * 活动相关图片
     */
    private List<Image> photos;

    public List<Image> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Image> photos) {
        this.photos = photos;
    }

    /**
     * 评论
     */
    private List<Post> comments;

    public List<Post> getComments() {
        return comments;
    }

    public void setComments(List<Post> comments) {
        this.comments = comments;
    }

    @JsonSetter
    public void setComments(int count) {
        this.comments = new LazyList<>(count);
    }

    /**
     * 点赞
     */
    private List<Like> likes;

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @JsonSetter
    public void setLikes(int count) {
        this.likes = new LazyList<>(count);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    public Activity() {
        super();
    }

    public Activity(long id) {
        super(id);
    }

    @Override
    protected Observable<Activity> init() {
        return api.getActivity(id);
    }

    @StringRes
    public int getStatus() {
        Date now = new Date();
        if (now.before(deadline)) return R.string.activity_status_ready;
        else if (now.before(startTime)) return R.string.activity_status_signup;
        else if (now.before(endTime)) return R.string.activity_status_running;
        return R.string.activity_status_ended;
    }

    public int getFocusedMemberCount() {
        return 0;
    }

    @JsonSetter("game")
    public void setGameByIdentifier(String identifier) {
        GameManager gameManager = App.getInstance().getObjectGraph().get(GameManager.class);
        if (identifier != null) {
            this.game = gameManager.getGame(identifier);
        }
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}
