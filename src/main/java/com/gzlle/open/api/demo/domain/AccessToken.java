package com.gzlle.open.api.demo.domain;

/**
 * @Creator: hadeslong
 * @Created: 2018/10/12 下午5:53
 * @Description:
 * @Modifier: hadeslong
 * @Modified By: 2018/10/12 下午5:53
 */
public class AccessToken {
    //token
    private String accessToken;
    //有效时间
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
