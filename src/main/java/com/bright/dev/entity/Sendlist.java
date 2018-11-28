package com.bright.dev.entity;

import java.io.Serializable;

public class Sendlist implements Serializable {
    private Integer listId;

    private Integer userId;

    private String sendTo;

    private String sendTime;

    private String sendStatus;

    private static final long serialVersionUID = 1L;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo == null ? null : sendTo.trim();
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime == null ? null : sendTime.trim();
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Sendlist other = (Sendlist) that;
        return (this.getListId() == null ? other.getListId() == null : this.getListId().equals(other.getListId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSendTo() == null ? other.getSendTo() == null : this.getSendTo().equals(other.getSendTo()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
            && (this.getSendStatus() == null ? other.getSendStatus() == null : this.getSendStatus().equals(other.getSendStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getListId() == null) ? 0 : getListId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSendTo() == null) ? 0 : getSendTo().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getSendStatus() == null) ? 0 : getSendStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", listId=").append(listId);
        sb.append(", userId=").append(userId);
        sb.append(", sendTo=").append(sendTo);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", sendStatus=").append(sendStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}