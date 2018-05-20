package com.wipro.androidproficiencyexercise.pojo;

public class AppPojo {

    private String url;

    public String getUrl() {
        return url;
    }

    private AppPojo(AppPojoBuilder builder) {
        this.url = builder.url;
    }

    //Builder Class
    public static class AppPojoBuilder {

        private String url;

        public AppPojoBuilder setUrl (String url) {
            this.url = url;
            return this;
        }

        public AppPojo build() {
            return new AppPojo(this);
        }
    }
}
