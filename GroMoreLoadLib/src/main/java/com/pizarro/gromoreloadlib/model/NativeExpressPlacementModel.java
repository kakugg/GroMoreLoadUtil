package com.pizarro.gromoreloadlib.model;

/**
 * Created by Irving
 */
public class NativeExpressPlacementModel {

    private String placementId;
    private int width;

    private boolean cacheNext = true;

    public NativeExpressPlacementModel(String placementId, int width) {
        this.placementId = placementId;
        this.width = width;
    }

    public NativeExpressPlacementModel(String placementId, int width, boolean cacheNext) {
        this.placementId = placementId;
        this.width = width;
        this.cacheNext = cacheNext;
    }

    public boolean isCacheNext() {
        return cacheNext;
    }

    public void setCacheNext(boolean cacheNext) {
        this.cacheNext = cacheNext;
    }

    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
