package com.lorenzorigato.base.network.service.envelope;

import com.lorenzorigato.base.network.service.dto.MetadataDTO;
import com.lorenzorigato.base.network.service.dto.MovieDTO;

import java.util.List;

public class MovieEnvelope {


    // Class attributes ****************************************************************************
    MetadataDTO metadata;
    List<MovieDTO> data;


    // Constructor *********************************************************************************
    public MovieEnvelope(MetadataDTO metadata, List<MovieDTO> data) {
        this.metadata = metadata;
        this.data = data;
    }


    // Class methods *******************************************************************************
    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    public List<MovieDTO> getData() {
        return data;
    }

    public void setData(List<MovieDTO> data) {
        this.data = data;
    }
}
