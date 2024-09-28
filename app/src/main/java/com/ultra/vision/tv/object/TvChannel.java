package com.ultra.vision.tv.object;

public class TvChannel {

    private int num;
    private String name;
    private String stream_type;
    private int stream_id;
    private String stream_icon;
    private String epg_channel_id;
    private String added;
    private String is_adult;
    private String category_id;
    private String custom_sid;
    private int tv_archive;
    private String direct_source;
    private int tv_archive_duration;

    public TvChannel(
            int num,
            String name,
            String stream_type,
            int stream_id,
            String stream_icon,
            String epg_channel_id,
            String added,
            String is_adult,
            String category_id,
            String custom_sid,
            int tv_archive,
            String direct_source,
            int tv_archive_duration) {
        this.num = num;
        this.name = name;
        this.stream_type = stream_type;
        this.stream_id = stream_id;
        this.stream_icon = stream_icon;
        this.epg_channel_id = epg_channel_id;
        this.added = added;
        this.is_adult = is_adult;
        this.category_id = category_id;
        this.custom_sid = custom_sid;
        this.tv_archive = tv_archive;
        this.direct_source = direct_source;
        this.tv_archive_duration = tv_archive_duration;
    }

    // Adicione construtores, getters e setters aqui conforme necessário

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStream_type() {
        return this.stream_type;
    }

    public void setStream_type(String stream_type) {
        this.stream_type = stream_type;
    }

    public int getStream_id() {
        return this.stream_id;
    }

    public void setStream_id(int stream_id) {
        this.stream_id = stream_id;
    }

    public String getStream_icon() {
        return this.stream_icon;
    }

    public void setStream_icon(String stream_icon) {
        this.stream_icon = stream_icon;
    }

    public String getEpg_channel_id() {
        return this.epg_channel_id;
    }

    public void setEpg_channel_id(String epg_channel_id) {
        this.epg_channel_id = epg_channel_id;
    }

    public String getAdded() {
        return this.added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getIs_adult() {
        return this.is_adult;
    }

    public void setIs_adult(String is_adult) {
        this.is_adult = is_adult;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCustom_sid() {
        return this.custom_sid;
    }

    public void setCustom_sid(String custom_sid) {
        this.custom_sid = custom_sid;
    }

    public int getTv_archive() {
        return this.tv_archive;
    }

    public void setTv_archive(int tv_archive) {
        this.tv_archive = tv_archive;
    }

    public String getDirect_source() {
        return this.direct_source;
    }

    public void setDirect_source(String direct_source) {
        this.direct_source = direct_source;
    }

    public int getTv_archive_duration() {
        return this.tv_archive_duration;
    }

    public void setTv_archive_duration(int tv_archive_duration) {
        this.tv_archive_duration = tv_archive_duration;
    }
}
