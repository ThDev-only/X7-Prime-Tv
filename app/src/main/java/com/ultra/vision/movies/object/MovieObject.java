package com.ultra.vision.movies.object;

public class MovieObject {
    public int id;
    public String name;
    public String title;
    public String overview;
    public String posterPath;
    public String backdropPath;
    public String voteAverage;
    public String popularity;
    public String voteCount;
    public boolean video;
    public String filePath;

    public MovieObject(
            int id,
            String title,
            String overview,
            String posterPath,
            String backdropPath,
            String voteAverage,
            String popularity,
            String voteCount,
            boolean video) {

        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
    }

    // serie
    public MovieObject(
            int id,
            String name,
            String title,
            String overview,
            String posterPath,
            String backdropPath,
            String voteAverage,
            String popularity,
            String voteCount,
            boolean video,
            String filePath) {

        this.id = id;
        this.name = name;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        if (name == null) return this.title;

        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public String getFilePath() {
        return this.filePath;
    }

    }
