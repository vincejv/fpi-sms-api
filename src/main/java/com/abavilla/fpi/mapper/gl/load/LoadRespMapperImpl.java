package com.abavilla.fpi.mapper.gl.load;

import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.entity.impl.load.RewardsResp;

import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-29T21:22:36+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@ApplicationScoped
public class LoadRespMapperImpl implements LoadRespMapper {

    @Override
    public LoadRespDto mapToDto(RewardsResp rewardsResp) {
        if ( rewardsResp == null ) {
            return null;
        }

        LoadRespDto loadRespDto = new LoadRespDto();

        loadRespDto.setStatus( rewardsResp.getStatus() );
        loadRespDto.setError( rewardsResp.getError() );
        loadRespDto.setTimestamp( ldtToStr( rewardsResp.getTimestamp() ) );

        return loadRespDto;
    }

    @Override
    public RewardsResp mapToEntity(LoadRespDto dto) {
        if ( dto == null ) {
            return null;
        }

        RewardsResp rewardsResp = new RewardsResp();

        rewardsResp.setStatus( dto.getStatus() );
        rewardsResp.setTimestamp( strToLdt( dto.getTimestamp() ) );
        rewardsResp.setError( dto.getError() );

        return rewardsResp;
    }
}
