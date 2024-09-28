package com.ultra.vision.series.object;

public class EpisodeObject {

    private int id;
    private int number;
    private EpisodeObject.InfoEpisode episodeInfo;

    public EpisodeObject(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public InfoEpisode getEpisodeInfo() {
        return this.episodeInfo;
    }

    public void setEpisodeInfo(InfoEpisode episodeInfo) {
        this.episodeInfo = episodeInfo;
    }
    
    public int getId() {
        return this.id;
    }

    public int getNumber() {
        return this.number;
    }

    public static class InfoEpisode {
        private String title;
        private String sinopse;
        private String banner;
        private int duration;
        private int progress;
        private int number;
        private Double vote;

        public InfoEpisode(
                String title,
                String sinopse,
                String banner,
                int duration,
                int progress,
                int number,
                Double vote) {
            this.title = title;
            this.sinopse = sinopse;
            this.banner = banner;
            this.duration = duration;
            this.progress = progress;
            this.number = number;
            this.vote = vote;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSinopse() {
            return this.sinopse;
        }

        public void setSinopse(String sinopse) {
            this.sinopse = sinopse;
        }

        public String getBanner() {
            return this.banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getProgress() {
            return this.progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Double getVote() {
            return this.vote;
        }

        public void setVote(Double vote) {
            this.vote = vote;
        }
    }

}
