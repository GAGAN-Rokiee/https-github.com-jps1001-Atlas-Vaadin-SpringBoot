package it.besolution.utils;

import it.besolution.rest.ApiRestResponse;
import org.apache.commons.lang3.StringUtils;

public class ResponseUtil {

    public synchronized static ApiRestResponse returnApiResponse(ApiRestResponse resp, String errMes) {

        if (resp == null || resp.getIsError()) {

            if (resp == null) resp = new ApiRestResponse();
            resp.setIsError(Boolean.TRUE);
            resp.setIsSuccess(Boolean.FALSE);
            resp.setErrorMessage(StringUtils.isBlank(resp.getErrorMessage()) ? errMes : resp.getErrorMessage());
            resp.setData(null);
        }

        return resp;
    }
}
