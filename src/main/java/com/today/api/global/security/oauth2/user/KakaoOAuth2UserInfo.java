package com.today.api.global.security.oauth2.user;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickname() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties == null)
            return null;
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        return null; // Kakao email might need extra permission
    }
}
