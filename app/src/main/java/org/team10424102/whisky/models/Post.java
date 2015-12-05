package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.team10424102.whisky.models.extensions.PostExtensionData;

import java.util.Date;

public class Post extends BaseObservable implements Parcelable {

    private Long id;
    private String content;
    private String device;
    private Date creationTime;
    private User sender;
    private PostExtensionData extension;
    private int gameLogo;
    private int likes;
    private int comments;





    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        GET & SET                            //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getGameLogo() {
        return gameLogo;
    }

    public void setGameLogo(int gameLogo) {
        this.gameLogo = gameLogo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }


    public void setComments(int comments) {
        this.comments = comments;
    }

    public PostExtensionData getExtension() {
        return extension;
    }

    public void setExtension(PostExtensionData extension) {
        this.extension = extension;
    }




    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                     Object Override                         //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////



    @Override
    public String toString() {
        return String.format("推文 (编号 = %d, 内容 = %s)", getId(), getContent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return !(id != null ? !id.equals(post.id) : post.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }



    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////


    public Post() {
    }

    protected Post(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.content = in.readString();
        this.device = in.readString();
        long tmpCreationTime = in.readLong();
        this.creationTime = tmpCreationTime == -1 ? null : new Date(tmpCreationTime);
        this.sender = in.readParcelable(User.class.getClassLoader());
        this.extension = in.readParcelable(PostExtensionData.class.getClassLoader());
        this.gameLogo = in.readInt();
        this.likes = in.readInt();
        this.comments = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.content);
        dest.writeString(this.device);
        dest.writeLong(creationTime != null ? creationTime.getTime() : -1);
        dest.writeParcelable(this.sender, 0);
        dest.writeParcelable(this.extension, flags);
        dest.writeInt(this.gameLogo);
        dest.writeInt(this.likes);
        dest.writeInt(this.comments);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
