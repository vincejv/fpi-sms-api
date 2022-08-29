package com.abavilla.fpi.mapper.gl.load;

import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.impl.load.RewardsReq;

import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-29T21:22:36+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@ApplicationScoped
public class LoadReqMapperImpl implements LoadReqMapper {

    @Override
    public LoadReqDto mapToDto(RewardsReq rewardsReq) {
        if ( rewardsReq == null ) {
            return null;
        }

        LoadReqDto loadReqDto = new LoadReqDto();

        loadReqDto.setMobile( rewardsReq.getAddress() );
        loadReqDto.setSku( rewardsReq.getSku() );

        return loadReqDto;
    }

    @Override
    public RewardsReq mapToEntity(LoadReqDto loadReqDto) {
        if ( loadReqDto == null ) {
            return null;
        }

        RewardsReq rewardsReq = new RewardsReq();

        rewardsReq.setAddress( loadReqDto.getMobile() );
        rewardsReq.setSku( loadReqDto.getSku() );

        return rewardsReq;
    }
}
