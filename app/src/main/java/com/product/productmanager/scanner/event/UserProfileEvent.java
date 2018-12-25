package com.product.productmanager.scanner.event;

/**
 * @author zjx on 2018/7/9.
 */
public class UserProfileEvent {

    public static class UserProfileEvent1{
        public String typeValue;
        public UserProfileEvent1(String typeValue) {
            this.typeValue = typeValue;
        }
    }

    public static class UserProfileEvent2{
        public int position;
        public UserProfileEvent2(int position) {
            this.position = position;
        }
    }
}
