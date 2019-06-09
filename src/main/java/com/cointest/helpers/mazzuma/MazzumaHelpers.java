package com.cointest.helpers.mazzuma;

public class MazzumaHelpers {

    private String sender;
    private String network;
    private String price;
    private String recipient_number;
    private String option;
    private String apikey = "your key goes here";


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRecipient_number() {
        return recipient_number;
    }

    public void setRecipient_number(String recipient_number) {
        this.recipient_number = recipient_number;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public String toString() {
        return "MazzumaHelpers{" +
                "sender='" + sender + '\'' +
                ", network='" + network + '\'' +
                ", price='" + price + '\'' +
                ", recipient_number='" + recipient_number + '\'' +
                ", option='" + option + '\'' +
                ", apikey='" + apikey + '\'' +
                '}';
    }
}
