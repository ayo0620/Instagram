package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Register your parse model
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(User.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("s3tE5OogD17XkYGR0W7XqnfofAZ32wVUs7BhBcDB")
                .clientKey("0kVB8kepc7vSV6nZZU9pSwXH7nDiOeDdgrqOdnUQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
