package com.abavilla.fpi.mapper.gl.load;

import com.abavilla.fpi.dto.impl.api.gl.load.RewardsCallbackDto;
import com.abavilla.fpi.dto.impl.api.gl.load.RewardsReqDto;
import com.abavilla.fpi.dto.impl.api.gl.load.RewardsRespDto;
import com.abavilla.fpi.entity.impl.load.RewardsCallback;
import com.abavilla.fpi.entity.impl.load.RewardsReq;
import com.abavilla.fpi.entity.impl.load.RewardsResp;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;

import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-29T21:22:36+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@ApplicationScoped
public class RewardsReqMapperImpl implements RewardsReqMapper {

    @Override
    public void mapRequestDtoToEntity(RewardsReqDto dto, RewardsTransStatus rewardsTransStatus) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBody() != null ) {
            if ( rewardsTransStatus.getRequest() == null ) {
                rewardsTransStatus.setRequest( new RewardsReq() );
            }
            bodyToRewardsReq( dto.getBody(), rewardsTransStatus.getRequest() );
        }
        else {
            rewardsTransStatus.setRequest( null );
        }
        if ( rewardsTransStatus.getExceptions() != null ) {
            List<Exception> list = dto.getExceptions();
            if ( list != null ) {
                rewardsTransStatus.getExceptions().clear();
                rewardsTransStatus.getExceptions().addAll( list );
            }
            else {
                rewardsTransStatus.setExceptions( null );
            }
        }
        else {
            List<Exception> list = dto.getExceptions();
            if ( list != null ) {
                rewardsTransStatus.setExceptions( new ArrayList<Exception>( list ) );
            }
        }
    }

    @Override
    public void mapRespDtoToEntity(RewardsRespDto dto, RewardsTransStatus rewardsTransStatus) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBody() != null ) {
            if ( rewardsTransStatus.getResponse() == null ) {
                rewardsTransStatus.setResponse( new RewardsResp() );
            }
            bodyToRewardsResp( dto.getBody(), rewardsTransStatus.getResponse() );
        }
        else {
            rewardsTransStatus.setResponse( null );
        }
        if ( rewardsTransStatus.getResponse() == null ) {
            rewardsTransStatus.setResponse( new RewardsResp() );
        }
        rewardsRespDtoToRewardsResp( dto, rewardsTransStatus.getResponse() );
        if ( rewardsTransStatus.getExceptions() != null ) {
            List<Exception> list = dto.getExceptions();
            if ( list != null ) {
                rewardsTransStatus.getExceptions().clear();
                rewardsTransStatus.getExceptions().addAll( list );
            }
            else {
                rewardsTransStatus.setExceptions( null );
            }
        }
        else {
            List<Exception> list = dto.getExceptions();
            if ( list != null ) {
                rewardsTransStatus.setExceptions( new ArrayList<Exception>( list ) );
            }
        }
    }

    @Override
    public void mapCallbackDtoToEntity(RewardsCallbackDto dto, RewardsTransStatus rewardsTransStatus) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBody() != null ) {
            if ( rewardsTransStatus.getCallback() == null ) {
                rewardsTransStatus.setCallback( new RewardsCallback() );
            }
            bodyToRewardsCallback( dto.getBody(), rewardsTransStatus.getCallback() );
        }
        else {
            rewardsTransStatus.setCallback( null );
        }
        if ( rewardsTransStatus.getExceptions() != null ) {
            List<Exception> list = dto.getExceptions();
            if ( list != null ) {
                rewardsTransStatus.getExceptions().clear();
                rewardsTransStatus.getExceptions().addAll( list );
            }
            else {
                rewardsTransStatus.setExceptions( null );
            }
        }
        else {
            List<Exception> list = dto.getExceptions();
            if ( list != null ) {
                rewardsTransStatus.setExceptions( new ArrayList<Exception>( list ) );
            }
        }
    }

    @Override
    public RewardsCallback mapCallbackDtoToCallbackEntity(RewardsCallbackDto dto) {
        if ( dto == null ) {
            return null;
        }

        RewardsCallback rewardsCallback = new RewardsCallback();

        rewardsCallback.setStatus( dtoBodyStatus( dto ) );
        rewardsCallback.setSku( dtoBodySku( dto ) );
        rewardsCallback.setTimestamp( strToLdt( dtoBodyTimestamp( dto ) ) );
        rewardsCallback.setTransactionId( dtoBodyTransactionId( dto ) );
        rewardsCallback.setMobileNumber( dtoBodyMobileNumber( dto ) );
        List<Exception> list = dto.getExceptions();
        if ( list != null ) {
            rewardsCallback.setExceptions( new ArrayList<Exception>( list ) );
        }

        return rewardsCallback;
    }

    protected void bodyToRewardsReq(RewardsReqDto.Body body, RewardsReq mappingTarget) {
        if ( body == null ) {
            return;
        }

        mappingTarget.setAddress( body.getAddress() );
        mappingTarget.setSku( body.getSku() );
    }

    protected void bodyToRewardsResp(RewardsRespDto.Body body, RewardsResp mappingTarget) {
        if ( body == null ) {
            return;
        }

        mappingTarget.setTransactionId( body.getTransactionId() );
        mappingTarget.setStatus( body.getStatus() );
        mappingTarget.setAddress( body.getAddress() );
        mappingTarget.setPromo( body.getPromo() );
        mappingTarget.setTimestamp( strToLdt( body.getTimestamp() ) );
    }

    protected void rewardsRespDtoToRewardsResp(RewardsRespDto rewardsRespDto, RewardsResp mappingTarget) {
        if ( rewardsRespDto == null ) {
            return;
        }

        mappingTarget.setError( rewardsRespDto.getError() );
    }

    protected void bodyToRewardsCallback(RewardsCallbackDto.Body body, RewardsCallback mappingTarget) {
        if ( body == null ) {
            return;
        }

        mappingTarget.setStatus( body.getStatus() );
        mappingTarget.setSku( body.getSku() );
        mappingTarget.setTimestamp( strToLdt( body.getTimestamp() ) );
        mappingTarget.setTransactionId( body.getTransactionId() );
        mappingTarget.setMobileNumber( body.getMobileNumber() );
    }

    private String dtoBodyStatus(RewardsCallbackDto rewardsCallbackDto) {
        if ( rewardsCallbackDto == null ) {
            return null;
        }
        RewardsCallbackDto.Body body = rewardsCallbackDto.getBody();
        if ( body == null ) {
            return null;
        }
        String status = body.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    private String dtoBodySku(RewardsCallbackDto rewardsCallbackDto) {
        if ( rewardsCallbackDto == null ) {
            return null;
        }
        RewardsCallbackDto.Body body = rewardsCallbackDto.getBody();
        if ( body == null ) {
            return null;
        }
        String sku = body.getSku();
        if ( sku == null ) {
            return null;
        }
        return sku;
    }

    private String dtoBodyTimestamp(RewardsCallbackDto rewardsCallbackDto) {
        if ( rewardsCallbackDto == null ) {
            return null;
        }
        RewardsCallbackDto.Body body = rewardsCallbackDto.getBody();
        if ( body == null ) {
            return null;
        }
        String timestamp = body.getTimestamp();
        if ( timestamp == null ) {
            return null;
        }
        return timestamp;
    }

    private Integer dtoBodyTransactionId(RewardsCallbackDto rewardsCallbackDto) {
        if ( rewardsCallbackDto == null ) {
            return null;
        }
        RewardsCallbackDto.Body body = rewardsCallbackDto.getBody();
        if ( body == null ) {
            return null;
        }
        Integer transactionId = body.getTransactionId();
        if ( transactionId == null ) {
            return null;
        }
        return transactionId;
    }

    private String dtoBodyMobileNumber(RewardsCallbackDto rewardsCallbackDto) {
        if ( rewardsCallbackDto == null ) {
            return null;
        }
        RewardsCallbackDto.Body body = rewardsCallbackDto.getBody();
        if ( body == null ) {
            return null;
        }
        String mobileNumber = body.getMobileNumber();
        if ( mobileNumber == null ) {
            return null;
        }
        return mobileNumber;
    }
}
