package com.emazon.shopping_service.infrastructure.output.feign.utils;

import com.emazon.shopping_service.infrastructure.output.feign.dto.ExceptionMessage;
import com.emazon.shopping_service.infrastructure.output.feign.exceptions.BadRequestException;
import com.emazon.shopping_service.infrastructure.output.feign.exceptions.InternalServerErrorException;
import com.emazon.shopping_service.infrastructure.output.feign.exceptions.NotFoundException;
import com.emazon.shopping_service.utils.FeignConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        switch (response.status()) {
            case FeignConstants.BAD_REQUEST_CODE:
                return new BadRequestException(message != null ? message.getMessage() : FeignConstants.BAD_REQUEST);
            case FeignConstants.NOT_FOUND_CODE:
                return new NotFoundException(message != null ? message.getMessage() : FeignConstants.NOT_FOUND);
            default:
                return new InternalServerErrorException(
                        message != null ? message.getMessage() : FeignConstants.INTERNAL_SERVER_ERROR
                );
        }

    }
}
