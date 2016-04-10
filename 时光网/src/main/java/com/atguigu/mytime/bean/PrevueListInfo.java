package com.atguigu.mytime.bean;

/**
 *
 * Created by Administrator on 16-4-8.
 */
public class PrevueListInfo {


    /**
     * id : 59748
     * movieName : 《吹梦巨人》预告片
     * coverImg : http://img31.mtime.cn/mg/2016/04/06/095028.19148163.jpg
     * movieId : 215177
     * url : http://vfx.mtime.cn/Video/2016/04/06/mp4/160406075539899122.mp4
     * hightUrl : http://vfx.mtime.cn/Video/2016/04/06/mp4/160406075539899122.mp4
     * videoTitle : 吹梦巨人 预告片
     * videoLength : 144
     * rating : -1
     * type : ["家庭","奇幻"]
     * summary : 好心眼儿巨人的冒险旅程
     */

    private TrailersEntity[] trailers;

    public void setTrailers(TrailersEntity[] trailers) {
        this.trailers = trailers;
    }

    public TrailersEntity[] getTrailers() {
        return trailers;
    }

    public static class TrailersEntity {
        private int id;
        private String movieName;
        private String coverImg;
        private int movieId;
        private String url;
        private String hightUrl;
        private String videoTitle;
        private int videoLength;
        private int rating;
        private String summary;
        private String[] type;

        public void setId(int id) {
            this.id = id;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setType(String[] type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public String getMovieName() {
            return movieName;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public int getMovieId() {
            return movieId;
        }

        public String getUrl() {
            return url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public int getRating() {
            return rating;
        }

        public String getSummary() {
            return summary;
        }

        public String[] getType() {
            return type;
        }
    }
}
