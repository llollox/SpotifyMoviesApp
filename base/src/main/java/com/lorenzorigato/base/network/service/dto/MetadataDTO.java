package com.lorenzorigato.base.network.service.dto;

public class MetadataDTO {

    // Class attributes ****************************************************************************
    boolean isLastPage;
    int limit;
    int total;


    // Constructor *********************************************************************************
    public MetadataDTO(boolean isLastPage, int limit, int total) {
        this.isLastPage = isLastPage;
        this.limit = limit;
        this.total = total;
    }


    // Class methods *******************************************************************************
    public boolean isLastPage() { return isLastPage; }

    public void setLastPage(boolean lastPage) { isLastPage = lastPage; }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MetadataDTO{" +
                "isLastPage=" + isLastPage +
                ", limit=" + limit +
                ", total=" + total +
                '}';
    }
}
