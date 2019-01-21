package com.xegami.pojo.bot;

public class Parameters {

    private String epicNicknameEncoded;
    private String platform;

    public Parameters(String epicNicknameEncoded, String platform) {
        this.epicNicknameEncoded = epicNicknameEncoded;
        this.platform = platform;
    }

    public String getEpicNicknameEncoded() {
        return epicNicknameEncoded;
    }

    public String getPlatform() {
        return platform;
    }
}
