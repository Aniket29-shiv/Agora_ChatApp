package io.agora.model;

/**
 * Created by animsh on 9/3/2021.
 */
public class ItemChatProfileModel {

    private String profileName;
    private String profileMessage;
    private boolean isLastMessageSeen;
    private String unseenMessageCount;
    private String lastMessageTime;
    private String profileImage;

    public ItemChatProfileModel(String profileName, String profileMessage, boolean isLastMessageSeen, String unseenMessageCount, String lastMessageTime, String profileImage) {
        this.profileName = profileName;
        this.profileMessage = profileMessage;
        this.isLastMessageSeen = isLastMessageSeen;
        this.unseenMessageCount = unseenMessageCount;
        this.lastMessageTime = lastMessageTime;
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileMessage() {
        return profileMessage;
    }

    public void setProfileMessage(String profileMessage) {
        this.profileMessage = profileMessage;
    }

    public boolean isLastMessageSeen() {
        return isLastMessageSeen;
    }

    public void setLastMessageSeen(boolean lastMessageSeen) {
        isLastMessageSeen = lastMessageSeen;
    }

    public String getUnseenMessageCount() {
        return unseenMessageCount;
    }

    public void setUnseenMessageCount(String unseenMessageCount) {
        this.unseenMessageCount = unseenMessageCount;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
