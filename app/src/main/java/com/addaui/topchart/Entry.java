package com.addaui.topchart;

/**
 * Created by ADDAUI on 5/8/2016.
 */
public class Entry {

    private String name ;
    private String category;
    private String preview;
    private String artist;
    private String imgUrl;
    private String releaseDate;
    //TODO try : http://stackoverflow.com/questions/16623963/how-to-format-2013-05-15t100000-0700-to-date-android


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String toString(){
        return String.format("\nname : %s \nartist : %s \nreleaseDate : %s \nimgUrl : %s \nCategory : %s\n",getName(),getArtist(),getReleaseDate(),getImgUrl(),getCategory());
    }
}
