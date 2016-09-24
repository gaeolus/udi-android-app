package com.smithsocial.udisampleapp.views;

class DetailsView {
    interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setDeviceName(String name);
        void setDeviceId(String deviceId);
        void setDeviceExpirationBool(Boolean hasExpiration);
        void setSterilizePriorToUse(Boolean hasSterilize);
        void setDeviceDescription(String deviceDescription);
        void setDeviceIsSaved(Boolean deviceIsSaved);
    }
}
