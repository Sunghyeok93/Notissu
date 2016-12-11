package com.notissu.WithUs;

/**
 * Created by forhack on 2016-12-11.
 */

public class We {
    String imgName;
    String name;
    String position;
    String email;

    public We(String imgName, String name, String position, String email) {
        this.imgName = imgName;
        this.name = name;
        this.position = position;
        this.email = email;
    }

    public String getImgName() {
        return imgName;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }
}
