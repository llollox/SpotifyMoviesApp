package com.lorenzorigato.base.network.service.dto;

public class MetadataDTO {

    // Class attributes ****************************************************************************
    int offset;
    int limit;
    int total;


    // Constructor *********************************************************************************
    public MetadataDTO(int offset, int limit, int total) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }


    // Class methods *******************************************************************************
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

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
                "offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                '}';
    }
}
