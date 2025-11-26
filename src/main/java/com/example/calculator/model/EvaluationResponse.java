package com.example.calculator.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 式評価レスポンスモデル
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluationResponse {

    private BigDecimal result;
    private String error;

    public EvaluationResponse() {
    }

    public EvaluationResponse(BigDecimal result) {
        this.result = result;
    }

    public static EvaluationResponse success(BigDecimal result) {
        return new EvaluationResponse(result);
    }

    public static EvaluationResponse error(String errorMessage) {
        EvaluationResponse response = new EvaluationResponse();
        response.setError(errorMessage);
        return response;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
