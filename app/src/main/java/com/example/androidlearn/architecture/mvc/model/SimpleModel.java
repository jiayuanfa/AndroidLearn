package com.example.androidlearn.architecture.mvc.model;

public class SimpleModel implements BaseModel{

    /**
     * 提供该外接获取Model的方法
     * @param uid
     * @param callback1
     */
    public void getUserInfo(String uid, Callback1<UserInfo> callback1) {
        UserInfo userInfo = new UserInfo();
        userInfo.age = 30;
        userInfo.name = "贾元发";
        callback1.onCallBack(userInfo);
    }

    @Override
    public void onDestroy() {

    }

    public class UserInfo
    {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
